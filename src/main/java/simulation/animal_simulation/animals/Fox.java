package simulation.animal_simulation.animals;


import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.map.Ground;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * The Fox class represents a fox in the animal simulation.
 * Each fox has a species, sex, maximum satiety, vision range, age, and steps before satiety decrease.
 * Foxes can perform actions such as reproducing, eating rabbits, and moving to the next ground.
 * The behavior of a fox is determined by its current state, such as its satiety level and whether it's in a shelter.
 *
 * <p>This class extends the Animal class, inheriting its common properties and behaviors.</p>
 */
public class Fox extends Animal {

    private final double SatietyRatioToSearchForOtherFoxes = 0.7;

    /**
     * Constructor for the Fox class.
     * Initial values: maxSatiety: 16, visionRange: 3, satiety: 8, stepsBeforeSatietyDecrease: 14.
     *
     * @param sex Animal sex.
     */
    public Fox(@NotNull Sex sex) {
        super(
            AnimalSpecies.Fox,
            sex,
            16,
            3,
            Age.Adult,
            50,
            8,
            14
        );
    }

    /**
     * Constructor for the baby Fox class.
     * Initial values: maxSatiety: 16, visionRange: 3, satiety: 8, stepsBeforeSatietyDecrease: 14.
     *
     * @param sex Animal sex.
     * @param age Animal age.
     */
    public Fox(@NotNull Sex sex, @NotNull Age age) {
        super(
            AnimalSpecies.Fox,
            sex,
            16,
            3,
            age,
            50,
            8,
            14
        );
    }

    /**
     * Returns a baby fox.
     *
     * @return A baby fox.
     */
    private Fox getBabyFox() {
        Fox babyRabbit = new Fox(
            Math.random() < 0.5 ? Sex.Female : Sex.Male,
            Age.Child
        );
        babyRabbit.setNearestShelter(nearestShelter);
        return babyRabbit;
    }


    public void doStep(Function<Fox, Boolean> onFoxWasBorn, Function<Rabbit, Boolean> onRabbitHasEaten) {
        if (isInShelter()) {
            tryToLeaveCell();
        } else {
            Fox babyFox = age != Age.Child ? tryToReproduce() : null;
            if (babyFox != null) {
                onFoxWasBorn.apply(babyFox);
            } else if (((double) satiety / maxSatiety) > SatietyRatioToSearchForOtherFoxes) {
                searchForOtherFoxes();
            } else if (!tryToEat(onRabbitHasEaten)) {
                this.moveToNextGround(onRabbitHasEaten);
            }
        }
        tryToDecreaseSatiety();
        tryToGrow();
    }

    /**
     * Tries to reproduce with another fox.
     * If the fox could reproduce, it will create a baby fox and return it.
     *
     * @return The baby fox if the fox could reproduce, null otherwise.
     */
    private Fox tryToReproduce() {
        if (currentGround == null || ((double) satiety / maxSatiety) < 0.8) return null;

        List<Ground> grounds = getNearestGrounds(Objects::nonNull);
        for (Ground ground : grounds) {
            Animal animal = ground.getAnimal();

            if (
                animal != null &&
                    animal.species == AnimalSpecies.Fox &&
                    animal.isAlive &&
                    sex != animal.sex &&
                    animal.age != Age.Child
            ) {

                Fox femaleFox = sex == Sex.Female ? this : (Fox) animal;
                Ground femaleFoxGround = femaleFox.currentGround;
                if (
                    femaleFoxGround == null ||
                    ((double) femaleFox.satiety / femaleFox.maxSatiety) < 0.5
                ) {
                    continue;
                }

                List<Ground> femaleFoxGrounds = femaleFox.getNearestGrounds(Ground::isAvailable);
                if (!femaleFoxGrounds.isEmpty()) {
                    Fox babyFox = getBabyFox();
                    femaleFoxGrounds.getFirst().setAnimal(babyFox);
                    femaleFox.satiety -= (int) (maxSatiety / 1.5);

                    return babyFox;
                }

            }
        }

        return null;
    }

    /**
     * Tries to eat a rabbit on the nearest ground.
     * If the fox could eat, it will increase its satiety, remove the rabbit from the ground.
     *
     * @return True if the fox could eat, false otherwise.
     */
    private boolean tryToEat(Function<Rabbit, Boolean> removeRabbit) {
        if (satiety == maxSatiety || currentGround == null) return false;

        List<Ground> nearestGrounds = getNearestGrounds(Objects::nonNull);
        for (Ground ground : nearestGrounds) {
            Animal animal = ground.getAnimal();
            if (animal != null && animal.species == AnimalSpecies.Rabbit) {
                eatRabbit(ground, removeRabbit);
                return true;
            }
        }

        return false;
    }

    /**
     * Eats the rabbit on the ground.
     * The satiety of the fox will be increased based on the age of the rabbit.
     *
     * @param ground The ground with the rabbit.
     */
    private void eatRabbit(Ground ground, Function<Rabbit, Boolean> removeRabbit) {
        Rabbit rabbit = (Rabbit) ground.getAnimal();
        if (rabbit == null) return;

        ground.removeAnimal();
        removeRabbit.apply(rabbit);

        switch (rabbit.age) {
            case Child -> satiety += 4;
            case Adult -> satiety += 8;
            case Senior -> satiety += 12;
        }

        if (satiety > maxSatiety) {
            satiety = maxSatiety;
        }

    }

    /**
     * Moves the fox to the next ground.
     * The next ground is chosen as the ground in the fox's vision range that has a rabbit on it.
     * If there's no such ground, the next ground is chosen as the ground in the fox's vision range that has a fox.
     */
    private void moveToNextGround(Function<Rabbit, Boolean> removeRabbit) {

        Ground groundWithRabbitInVisionRange = getGroundInVisionRangeByConditionSortedByDistance( ground ->
                ground.getAnimal() != null &&
                        ground.getAnimal().species == AnimalSpecies.Rabbit &&
                        ground.getShelter() == null
        );

        if (
            groundWithRabbitInVisionRange == null &&
            age != Age.Child &&
            ((double) satiety / maxSatiety) > SatietyRatioToSearchForOtherFoxes
        ) {
            searchForOtherFoxes();
        } else {
            super.moveToNextGround(groundWithRabbitInVisionRange);
            tryToEat(removeRabbit);
        }

    }

    /**
     * Moves the fox towards next ground where there's another fox.
     */
    private void searchForOtherFoxes() {
        Ground nextGround = getGroundInVisionRangeByConditionSortedByDistance( ground ->
            ground.getAnimal() != null &&
            ground.getAnimal().species == AnimalSpecies.Fox &&
            ground.getAnimal().age != Age.Child &&
            ground.getAnimal().isAlive() &&
            ground.getShelter() == null
        );

        super.moveToNextGround(nextGround);
    }

}
