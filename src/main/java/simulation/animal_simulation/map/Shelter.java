package simulation.animal_simulation.map;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.animals.*;
import simulation.animal_simulation.map.navigation.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The Shelter class represents a shelter in the simulation map.
 * Each Shelter object has a unique identifier and coordinates on the map.
 * A Shelter is intended for a specific species of animal and has a maximum capacity.
 * It also keeps track of its nearest shelters and the animals currently in the shelter.
 * The Shelter class provides methods for setting and getting its ground, coordinates, nearest shelters, and animals.
 * It also provides methods for adding and removing animals from the shelter.
 */
public class Shelter {

    public final int groundId;
    private Ground ground;
    @NotNull public final ShelterType type;
    @NotNull public AnimalSpecies forAnimal;

    public int capacity;
    public int range;
    @NotNull public final ArrayList<Shelter> nearestShelters = new ArrayList<>();

    @NotNull public final List<Animal> animals;

    /**
     * Constructor for the Shelter class.
     *
     * @param groundId The unique identifier for the ground where this shelter is located.
     * @param type The type of this shelter.
     * @param forAnimal The species of animal for which this shelter is intended.
     * @param capacity The maximum number of animals this shelter can accommodate.
     */
    public Shelter(
        int groundId,
        @NotNull ShelterType type,
        @NotNull AnimalSpecies forAnimal,
        int capacity
    ) {
        this.groundId = groundId;
        this.type = type;
        this.forAnimal = forAnimal;
        this.capacity = capacity;
        this.range = getRangeByAnimalSpecies(forAnimal);
        animals = List.of();
    }

    /**
     * Constructor for the Shelter class.
     *
     * @param groundId The unique identifier for the ground where this shelter is located.
     * @param type The type of this shelter.
     * @param forAnimal The species of animal for which this shelter is intended.
     * @param capacity The maximum number of animals this shelter can accommodate.
     * @param animals The list of animals currently in this shelter.
     */
    public Shelter(
        int groundId,
        @NotNull ShelterType type,
        @NotNull AnimalSpecies forAnimal,
        int capacity,
        @NotNull List<Animal> animals
    ) {
        this.groundId = groundId;
        this.type = type;
        this.forAnimal = forAnimal;
        this.capacity = capacity;
        this.range = getRangeByAnimalSpecies(forAnimal);
        this.animals = animals;
    }


    /**
     * Sets the ground this shelter is located on.
     *
     * @param ground The ground where this shelter is located.
     *
     * @throws IllegalAccessException If the ground is already set or the ground ID does not match.
     */
    public void setGround(@NotNull Ground ground) throws IllegalAccessException {
        if (this.ground != null || groundId != ground.id) throw new IllegalAccessException();
        this.ground = ground;
    }

    /**
     * @return The ground where this shelter is located.
     *
     * @throws IllegalAccessException If the ground is not set.
     */
    public @NotNull Ground getGround() throws IllegalAccessException {
        if (ground == null) throw new IllegalAccessException();
        return ground;
    }

    /**
     * @return The coordinates of this shelter.
     */
    public Coordinates getCoordinates() {
        return ground.coordinates;
    }

    /**
     * Sets the nearest shelters for this shelter that are in range.
     *
     * @param shelters The list of shelters to find the nearest ones from.
     */
    public void setNearestSheltersFromSheltersList(List<Shelter> shelters) {
        for (Shelter shelter : shelters) {
            if (shelter == this) continue;
            if (getCoordinates().areInRageWithOtherCoordinates(shelter.getCoordinates(), range)) {
                nearestShelters.add(shelter);
            }
        }
        nearestShelters.sort((shelter1, shelter2) -> Double.compare(
            getCoordinates().getDistanceToOtherCoordinates(shelter1.getCoordinates()),
            getCoordinates().getDistanceToOtherCoordinates(shelter2.getCoordinates())
        ));
    }

    /**
     * @return The nearest shelter to this shelter.
     */
    public Shelter getNearestShelter() {
        if (nearestShelters.isEmpty()) return null;
        return nearestShelters.getFirst();
    }

    /**
     * @return Range of this shelter based on the animal species.
     */
    private int getRangeByAnimalSpecies(AnimalSpecies species) {
        return switch (species) {
            case Rabbit -> 3;
            case Fox -> 5;
        };
    }

    /**
     * @return True if this shelter is intended for rabbits.
     */
    public boolean isForRabbits() {
        return forAnimal == AnimalSpecies.Rabbit;
    }

    /**
     * @return True if this shelter is intended for foxes.
     */
    public boolean isForFoxes() {
        return forAnimal == AnimalSpecies.Fox;
    }

    /**
     * Adds a rabbit to this shelter.
     *
     * @param rabbit The rabbit to add.
     *
     * @return True if the rabbit was added successfully.
     */
    public boolean addRabbit(Rabbit rabbit) {
        if (!isForRabbits() || animals.size() >= capacity) return false;

        return animals.add(rabbit);
    }

    /**
     * Adds a fox to this shelter.
     *
     * @param fox The fox to add.
     *
     * @return True if the fox was added successfully.
     */
    public boolean addFox(Fox fox) {
        if (!isForFoxes() || animals.size() >= capacity) return false;

        return animals.add(fox);
    }

    /**
     * Removes an animal from this shelter.
     *
     * @param animal The animal to remove.
     *
     * @return True if the animal was removed successfully.
     */
    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }

}
