package simulation.animal_simulation.animals;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.map.Ground;
import simulation.animal_simulation.map.plants.Grass;
import simulation.animal_simulation.map.plants.ThickVegetation;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * The Rabbit class represents a rabbit in the animal simulation.
 * Each rabbit has a species, sex, maximum satiety, vision range, age, and steps before satiety decrease.
 * Rabbits can perform actions such as reproducing, eating grass or thick vegetation, and moving to the next ground.
 * The behavior of a rabbit is determined by its current state, such as its satiety level and whether it's in danger.
 *
 * <p>This class extends the Animal class, inheriting its common properties and behaviors.</p>
 */
public class Rabbit extends Animal {

    /**
     * Constructor for the Rabbit class.
     * Initial values: maxSatiety: 10, visionRange: 2, satiety: 5, stepsBeforeSatietyDecrease: 8.
     *
     * @param sex The sex of the rabbit.
     */
    public Rabbit(@NotNull Sex sex) {
        super(
            AnimalSpecies.Rabbit,
            sex,
            10,
            3,
            Age.Adult,
            30,
            5,
            8
        );
    }

    /**
     * Constructor for the baby Rabbit class.
     * Initial values: maxSatiety: 10, visionRange: 2, satiety: 5, stepsBeforeSatietyDecrease: 8.
     *
     * @param sex The sex of the rabbit.
     * @param age The age of the rabbit.
     */
    public Rabbit(@NotNull Sex sex, @NotNull Age age) {
        super(
            AnimalSpecies.Rabbit,
            sex,
            10,
            3,
            age,
            30,
            5,
            8
        );
    }

    /**
     * Constructor for the Rabbit class primarily used for testing.
     *
     * @param isAlive Whether the rabbit is alive.
     */
    public Rabbit(boolean isAlive) {
        super(AnimalSpecies.Rabbit, isAlive);
    }

    /**
     * Returns a baby rabbit.
     *
     * @return A baby rabbit.
     */
    private Rabbit getBabyRabbit() {
        Rabbit babyRabbit = new Rabbit(
            Math.random() < 0.5 ? Sex.Female : Sex.Male,
            Age.Child
        );
        babyRabbit.setNearestShelter(nearestShelter);
        return babyRabbit;
    }


    /**
     * Performs a step in the simulation for the rabbit.
     * The rabbit will try to run to a shelter if it's in danger or already running to a shelter.
     * If not, it will try to eat and if it can't, it will move to the next ground.
     * Finally, it will try to decrease its satiety.
     */
    public void doStep(Function<Rabbit, Boolean> onNewRabbitHasBorn) {
        runsToShelter = isInDanger();

        if (runsToShelter) {
            runToShelter();
        } else if (isInShelter()) {
            tryToLeaveCell();
        } else {
            Rabbit babyRabbit = age != Age.Child ? tryToReproduce() : null;
            if (babyRabbit != null) {
                onNewRabbitHasBorn.apply(babyRabbit);
            } else if (satiety == maxSatiety) {
                searchForOtherRabbits();
            } else if (!tryToEat()) {
                this.moveToNextGround();
            }
        }
        tryToDecreaseSatiety();
        tryToGrow();
    }

    /**
     * Checks if the rabbit is in danger.
     * A rabbit is in danger if there's a fox in its vision range.
     *
     * @return True if the rabbit is in danger, false otherwise.
     */
    private boolean isInDanger() {
        return getGroundsInVisionRange(currentGround).stream().anyMatch(
            ground -> ground.getAnimal() != null &&
                    ground.getAnimal().species == AnimalSpecies.Fox &&
                    ground.getAnimal().isAlive()
        );
    }

    /**
     * Tries to reproduce with another rabbit.
     * If the rabbit can reproduce, it will create a baby rabbit and return it.
     *
     * @return The baby rabbit if the rabbit could reproduce, null otherwise.
     */
    private Rabbit tryToReproduce() {
        if (currentGround == null || ((double) satiety / maxSatiety) < 0.5) return null;

        List<Ground> grounds = getNearestGrounds(Objects::nonNull);
        for (Ground ground : grounds) {
            Animal animal = ground.getAnimal();

            if (
                animal != null &&
                animal.species == AnimalSpecies.Rabbit &&
                animal.isAlive &&
                sex != animal.sex &&
                animal.age != Age.Child
            ) {

                Rabbit femaleRabbit = sex == Sex.Female ? this : (Rabbit) animal;
                Ground femaleRabbitGround = femaleRabbit.currentGround;
                if (femaleRabbitGround == null || ((double) femaleRabbit.satiety / femaleRabbit.maxSatiety) < 0.5) {
                    continue;
                }

                List<Ground> femaleRabbitGrounds = femaleRabbit.getNearestGrounds(Ground::isAvailable);
                if (!femaleRabbitGrounds.isEmpty()) {
                    Rabbit babyRabbit = getBabyRabbit();
                    femaleRabbitGrounds.getFirst().setAnimal(babyRabbit);
                    femaleRabbit.satiety -= (maxSatiety / 3);

                    return babyRabbit;
                }

            }
        }

        return null;
    }

    /**
     * Tries to eat the grass or the thick vegetation on the current ground.
     * If the rabbit can eat, it will increase its satiety.
     *
     * @return True if the rabbit could eat, false otherwise.
     */
    private boolean tryToEat() {
        if (satiety == maxSatiety || currentGround == null) return false;

        Grass grass = currentGround.getGrass();
        if (grass != null && grass.eat()) {
            satiety++;
            return true;
        }

        ThickVegetation thickVegetation = currentGround.getThickVegetation();
        if (thickVegetation != null) {
            thickVegetation.eat();
            satiety++;
            return true;
        }

        return false;
    }

    /**
     * Moves the rabbit to the next ground.
     * The next ground is chosen as the ground in the rabbit's vision range that doesn't have an animal or a shelter
     * and has grass or thick vegetation.
     * If there's no such ground, the next ground is chosen as the ground in the rabbit's vision range that has a
     * rabbit.
     */
    private void moveToNextGround() {

        Ground nextGround = getGroundInVisionRangeByConditionSortedByDistance( ground ->
            ground.isAvailable() &&
            (
                (ground.getGrass() != null && ground.getGrass().quantity > 0) ||
                (ground.getThickVegetation() != null && ground.getThickVegetation().quantity > 0)
            )
        );

        if (nextGround == null && age != Age.Child && ((double) satiety / maxSatiety) > 0.6) {
            searchForOtherRabbits();
        } else {
            super.moveToNextGround(nextGround);
        }

    }

    /**
     * Moves the rabbit towards next ground where there's another rabbit.
     */
    private void searchForOtherRabbits() {
        Ground nextGround = getGroundInVisionRangeByConditionSortedByDistance( ground ->
                ground.getAnimal() != null &&
                        ground.getAnimal().species == AnimalSpecies.Rabbit &&
                        ground.getAnimal().age != Age.Child &&
                        ground.getAnimal().isAlive() &&
                        ground.getShelter() == null
        );

        super.moveToNextGround(nextGround);
    }

}
