package simulation.animal_simulation.map;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.LoggerController;
import simulation.animal_simulation.animals.Animal;
import simulation.animal_simulation.animals.Fox;
import simulation.animal_simulation.animals.Rabbit;
import simulation.animal_simulation.map.navigation.Coordinates;
import simulation.animal_simulation.map.plants.Grass;
import simulation.animal_simulation.map.plants.ThickVegetation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Map class represents the simulation environment where the animals live and interact.
 * It contains all the animals and shelters in the simulation.
 * The map is a grid of Ground objects, each of which can contain grass, thick vegetation, or a shelter.
 * The map also keeps track of the animals in the simulation, including rabbits and foxes.
 * The Map class is responsible for initializing the map and running the simulation steps.
 * During each simulation step, the grass on the map regrows and each animal takes its turn.
 */
public class Map {

    private final LoggerController loggerController;

    final private int size;
    /**
     * The root ground of the map. It is the first ground in the first row of the map. It has id 1.
     */
    @NotNull public final Ground rootGround;

    /**
     * List of rabbits on the map.
     */
    @NotNull public final ArrayList<Rabbit> rabbits = new ArrayList<>();
    /**
     * List of foxes on the map.
     */
    @NotNull public final ArrayList<Fox> foxes = new ArrayList<>();
    /**
     * List of grass and thick vegetation on the map.
     */
    private int grassQuantity = 0;

    /**
     * Creates a map with the given parameters.
     *
     * @param size Size of the map.
     * @param grassAmount Amount of grass on the map.
     * @param thickVegetationAmount Amount of thick vegetation on the map.
     * @param rabbitShelters List of rabbit shelters.
     * @param foxShelters List of fox shelters.
     * @param loggerController The logger controller.
     *
     * @throws IllegalAccessException If there is illegal access to a field.
     */
    public Map(
        int size, int grassAmount, int thickVegetationAmount,
        List<Shelter> rabbitShelters, List<Shelter> foxShelters,
        LoggerController loggerController
    ) throws IllegalAccessException {
        if (!isMapInputValid(
            size, grassAmount, thickVegetationAmount,
            rabbitShelters.size(), foxShelters.size()
        )) {
            throw new IllegalArgumentException();
        }

        ArrayList<Shelter> rabbitSheltersCopy = new ArrayList<>(rabbitShelters);
        ArrayList<Shelter> foxSheltersCopy = new ArrayList<>(foxShelters);

        this.size = size;
        rootGround = generateMap(
            grassAmount,
            thickVegetationAmount,
            rabbitSheltersCopy,
            foxSheltersCopy
        );
        this.loggerController = loggerController;

        addRabbitsFromShelters(rabbitShelters);
        addFoxesFromShelters(foxShelters);
    }


    /**
     * Checks if the input for the map is valid.
     *
     * @param size Size of the map.
     * @param grassAmount Amount of grass on the map.
     * @param thickVegetationAmount Amount of thick vegetation on the map.
     * @param rabbitSheltersAmount Amount of rabbit shelters on the map.
     * @param foxSheltersAmount Amount of fox shelters on the map.
     *
     * @return True if amount of all elements is less or equal to the size of the map.
     */
    private boolean isMapInputValid(
        int size, int grassAmount, int thickVegetationAmount,
        int rabbitSheltersAmount, int foxSheltersAmount
    ) {
        return grassAmount +
            thickVegetationAmount +
            rabbitSheltersAmount +
            foxSheltersAmount <= size * size;
    }

    /**
     * Generates the map with the given parameters.
     *
     * @param grassAmount Amount of grass on the map.
     * @param thickVegetationAmount Amount of thick vegetation on the map.
     * @param rabbitShelters List of rabbit shelters.
     * @param foxShelters List of fox shelters.
     *
     * @return The root ground of the map.
     * @throws IllegalAccessException If there is illegal access to a field.
     */
    private Ground generateMap(
        int grassAmount, int thickVegetationAmount,
        List<Shelter> rabbitShelters, List<Shelter> foxShelters
    ) throws IllegalAccessException {
        if (size < 5) throw new IllegalArgumentException();

        ArrayList<Integer> excludedGroundsIds = new ArrayList<>();
        addGroundIdsOfSheltersToList(excludedGroundsIds, rabbitShelters);
        addGroundIdsOfSheltersToList(excludedGroundsIds, foxShelters);

        List<Integer> grassIds = getRandomGroundIds(size * size, grassAmount, excludedGroundsIds);
        excludedGroundsIds.addAll(grassIds);
        List<Integer> thickVegetationIds =
            getRandomGroundIds(size * size, thickVegetationAmount, excludedGroundsIds);

        ArrayList<Ground> firstGroundVerticalLine = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            firstGroundVerticalLine.add(
                getGroundHorizontalLine(
                    i * size + 1, size, size, null,
                    grassIds, thickVegetationIds, rabbitShelters, foxShelters
                )
            );
        }

        for (int i = 0; i < size - 1; i++) {
            setGroundsTopBottomNeighbours(firstGroundVerticalLine.get(i), firstGroundVerticalLine.get(i + 1));
        }

        return firstGroundVerticalLine.getFirst();
    }

    /**
     * Adds the ground ids of the shelters to the list.
     *
     * @param groundIds List of ground ids.
     * @param shelterList List of shelters.
     */
    private void addGroundIdsOfSheltersToList(List<Integer> groundIds, List<Shelter> shelterList) {
        for (Shelter shelter : shelterList) {
            groundIds.add(shelter.groundId);
        }
    }

    /**
     * Returns a list of random ground ids.
     *
     * @param maxId Maximum id of a ground.
     * @param idsCount Amount of ids to generate.
     * @param excludeIds List of ids to exclude.
     *
     * @return List of random ground ids.
     */
    @NotNull
    private List<Integer> getRandomGroundIds(int maxId, int idsCount, @NotNull List<Integer> excludeIds) {
        List<Integer> randomIds = new ArrayList<>();

        for (int i = 1; i <= idsCount; i++) {
            int randomId = (int) (Math.random() * maxId);
            while (excludeIds.contains(randomId) || randomIds.contains(randomId)) {
                randomId = (int) (Math.random() * maxId);
            }
            randomIds.add(randomId);
        }

        return randomIds.stream().sorted().collect(Collectors.toList());
    }

    /**
     * Returns a horizontal line of grounds.
     *
     * @param id ID of the first ground.
     * @param size Size of the line.
     * @param previousGround Previous ground.
     * @param grassIds List of grass ids.
     * @param thickVegetationIds List of thick vegetation ids.
     * @param rabbitShelters List of rabbit shelters.
     * @param foxShelters List of fox shelters.
     *
     * @return Horizontal line of grounds.
     * @throws IllegalAccessException If there is illegal access to a field.
     */
    private Ground getGroundHorizontalLine(
        int id,
        int size,
        int leftSize,
        Ground previousGround,
        @NotNull List<Integer> grassIds,
        @NotNull List<Integer> thickVegetationIds,
        @NotNull List<Shelter> rabbitShelters,
        @NotNull List<Shelter> foxShelters
    ) throws IllegalAccessException {

        Ground ground = getNewGround(
            id, new Coordinates(size, id),
            grassIds, thickVegetationIds, rabbitShelters, foxShelters
        );
        ground.setNextLeft(previousGround);

        if (leftSize > 1) {
            Ground nextGround = getGroundHorizontalLine(
                id + 1, size, leftSize - 1, ground, grassIds, thickVegetationIds,
                rabbitShelters, foxShelters
            );
            ground.setNextRight(nextGround);
        }

        return ground;
    }

    /**
     * Returns a new ground with the given parameters.
     *
     * @param id ID of the ground.
     * @param coordinates Coordinates of the ground.
     * @param grassIds List of grass ids.
     * @param thickVegetationIds List of thick vegetation ids.
     * @param rabbitShelters List of rabbit shelters.
     * @param foxShelters List of fox shelters.
     *
     * @return New ground.
     * @throws IllegalAccessException If there is illegal access to a field.
     */
    private Ground getNewGround(
        int id,
        Coordinates coordinates,
        @NotNull List<Integer> grassIds,
        @NotNull List<Integer> thickVegetationIds,
        @NotNull List<Shelter> rabbitShelters,
        @NotNull List<Shelter> foxShelters
    ) throws IllegalAccessException {
        if (!grassIds.isEmpty() && id == grassIds.getFirst()) {

            grassIds.removeFirst();
            Ground ground = new Ground(id, coordinates);
            ground.setGrass(new Grass(ground));
            return ground;

        } else if (!thickVegetationIds.isEmpty() && id == thickVegetationIds.getFirst()) {

            thickVegetationIds.removeFirst();
            Ground ground = new Ground(id, coordinates);
            ground.setThickVegetation(new ThickVegetation(ground));
            return ground;

        } else if (!rabbitShelters.isEmpty() && rabbitShelters.getFirst().groundId == id) {

            Ground ground = new Ground(id, coordinates, rabbitShelters.getFirst());
            rabbitShelters.removeFirst().setGround(ground);
            return ground;

        } else if (!foxShelters.isEmpty() && foxShelters.getFirst().groundId == id) {

            Ground ground = new Ground(id, coordinates, foxShelters.getFirst());
            foxShelters.removeFirst().setGround(ground);
            return ground;

        } else {

            return new Ground(id, coordinates);

        }
    }

    /**
     * Sets the top and bottom neighbours for the given grounds.
     *
     * @param topGround Top ground.
     * @param bottomGround Bottom ground.
     */
    private void setGroundsTopBottomNeighbours(Ground topGround, Ground bottomGround) {
        if (topGround == null || bottomGround == null) return;

        topGround.setNextBottom(bottomGround);
        bottomGround.setNextTop(topGround);

        setGroundsTopBottomNeighbours(topGround.getNextRight(), bottomGround.getNextRight());
    }

    /**
     * Adds rabbits from the given shelters to the map.
     *
     * @param shelters List of shelters.
     *
     * @throws IllegalAccessException If there is illegal access to a field.
     */
    private void addRabbitsFromShelters(List<Shelter> shelters) throws IllegalAccessException {
        for (Shelter shelter : shelters) {
            for (Animal rabbit : shelter.animals) {
                rabbit.setGround(shelter.getGround());
                rabbits.add((Rabbit) rabbit);
            }
        }
    }

    /**
     * Adds foxes from the given shelters to the map.
     *
     * @param shelters List of shelters.
     *
     * @throws IllegalAccessException If there is illegal access to a field.
     */
    private void addFoxesFromShelters(List<Shelter> shelters) throws IllegalAccessException {
        for (Shelter shelter : shelters) {
            for (Animal fox : shelter.animals) {
                fox.setGround(shelter.getGround());
                foxes.add((Fox) fox);
            }
        }
    }


    /**
     * Returns true if there are alive animals on the map.
     *
     * @return True if there are alive animals on the map, false otherwise.
     */
    public boolean hasAliveAnimals() {
        return rabbits.stream().anyMatch(Animal::isAlive) || foxes.stream().anyMatch(Animal::isAlive);
    }

    /**
     * @return Returns the count of alive rabbits on the map.
     */
    public int getRabbitCount() {
        return rabbits.stream().filter(Animal::isAlive).toList().size();
    }
    /**
     * @return Returns the count of alive foxes on the map.
     */
    public int getFoxCount() {
        return foxes.stream().filter(Animal::isAlive).toList().size();
    }
    /**
     * @return Returns the quantity of grass.
     */
    public int getGrassQuantity() {
        return grassQuantity;
    }
    /**
     * Increases the quantity of grass.
     *
     * @param quantity Quantity to increase.
     */
    public void increaseGrassQuantity(int quantity) {
        grassQuantity += quantity;
    }
    /**
     * Resets the quantity of grass.
     */
    public void resetGrassQuantity() {
        grassQuantity = 0;
    }

    /**
     * Does the next step of the simulation.
     * It regrows the grass and then does the next step for each animal.
     */
    public void doNextStep() {
        regrowGrass();
        doNextStepForRabbits();
        doNextStepForFoxes();
    }

    /**
     * Does the next step for the rabbits.
     * It makes the rabbits do their steps and checks if they should decompose.
     */
    private void doNextStepForRabbits() {
        ArrayList<Rabbit> newRabbits = new ArrayList<>();
        ArrayList<Rabbit> oldRabbits = new ArrayList<>();

        for (Rabbit rabbit : rabbits) {
            if (rabbit.isAlive()) {
                Coordinates startCoordinates = rabbit.getCoordinates();
                rabbit.doStep(
                    babyRabbit -> {
                        loggerController.logRabbitWasBorn(babyRabbit);
                        return newRabbits.add(babyRabbit);
                    }
                );
                loggerController.logAnimalMovement(rabbit, startCoordinates);
            } else {
                rabbit.tryToDecompose(
                    animal -> {
                        loggerController.logAnimalDecomposition(animal);
                        return oldRabbits.remove((Rabbit) animal);
                    }
                );
            }
        }

        if (!newRabbits.isEmpty()) rabbits.addAll(newRabbits);
        if (!oldRabbits.isEmpty()) rabbits.removeAll(oldRabbits);
    }

    /**
     * Does the next step for the foxes.
     * It makes the foxes do their steps and checks if they should decompose.
     */
    private void doNextStepForFoxes() {
        ArrayList<Fox> newFoxes = new ArrayList<>();
        ArrayList<Fox> oldFoxes = new ArrayList<>();

        for (Fox fox : foxes) {
            if (fox.isAlive()) {
                Coordinates startCoordinates = fox.getCoordinates();
                fox.doStep(
                    babyFox -> {
                        loggerController.logFoxWasBorn(babyFox);
                        return newFoxes.add(babyFox);
                    },
                    eatenRabbit -> {
                        loggerController.logRabbitWasEaten(eatenRabbit);
                        return rabbits.remove(eatenRabbit);
                    }
                );
                loggerController.logAnimalMovement(fox, startCoordinates);
            } else {
                fox.tryToDecompose(animal -> oldFoxes.remove((Fox) animal));
            }
        }

        if (!newFoxes.isEmpty()) foxes.addAll(newFoxes);
        if (!oldFoxes.isEmpty()) foxes.removeAll(oldFoxes);
    }

    /**
     * Regrows the grass on the map.
     */
    private void regrowGrass() {
        Ground currentGroundInColumn = rootGround;
        Ground currentGround = rootGround;

        while (currentGround != null) {

            if (currentGround.getGrass() != null) {
                currentGround.getGrass().tryToRegrowth();
            } else if (currentGround.getThickVegetation() != null) {
                currentGround.getThickVegetation().tryToRegrowth(loggerController::logGrassWasSpread);
            }

            if (currentGround.getNextRight() != null) {
                currentGround = currentGround.getNextRight();
            } else {
                currentGroundInColumn = currentGroundInColumn.getNextBottom();
                currentGround = currentGroundInColumn;
            }

        }
    }

}
