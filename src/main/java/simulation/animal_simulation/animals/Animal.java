package simulation.animal_simulation.animals;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.map.Ground;
import simulation.animal_simulation.map.Shelter;
import simulation.animal_simulation.map.navigation.Coordinates;
import simulation.animal_simulation.map.navigation.CoordinatesDifference;
import simulation.animal_simulation.map.navigation.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The Animal class represents an animal in the simulation.
 * It is an abstract class that is extended by specific animal classes.
 * Each Animal object has a sex, maximum satiety, age, vision range, and a boolean indicating if it is alive.
 * It also keeps track of the nearest shelter, the ground it is currently on, and if it is running to a shelter.
 * The Animal class provides methods for setting and getting its nearest shelter and current ground.
 * It also provides methods for moving the animal, decreasing its satiety, and checking if it is alive.
 * The Animal class is responsible for the animal's movement and actions during the simulation.
 */
public abstract class Animal {

    @NotNull protected final AnimalSpecies species;
    @NotNull protected final Sex sex;
    protected boolean isAlive = true;
    protected int maxSatiety;
    protected int visionRange;
    @NotNull protected Age age;
    /**
     * The number of steps before the animal grows.
     */
    protected int stepsBeforeGrow;
    /**
     * The number of steps the animal made after it grew for the last time.
     */
    protected int stepsAfterGrow = 0;
    protected int satiety;
    /**
     * The number of steps before the animal's satiety decreases.
     */
    protected int stepsBeforeSatietyDecrease;
    /**
     * The number of steps the animal made after its satiety decreased for the last time.
     */
    protected int stepsAfterSatietyDecrease = 0;
    protected Shelter nearestShelter;
    protected Ground currentGround;
    protected boolean runsToShelter = false;
    protected Direction movingDirection = null;
    /**
     * The number of steps the animal made after its death.
     * Used for animal decomposition, so it can be removed from the map.
     */
    private int stepsAfterDeath = 0;

    /**
     * Constructor for the Animal class.
     *
     * @param species The species of the animal.
     * @param sex Animal sex.
     * @param maxSatiety The maximum satiety of the animal.
     * @param visionRange The vision range of the animal.
     * @param age The age of the animal.
     * @param stepsBeforeGrow The number of steps before the animal grows.
     * @param satiety Initial satiety of the animal.
     * @param stepsBeforeSatietyDecrease The number of steps before the animal's satiety decreases.
     */
    public Animal(
        @NotNull AnimalSpecies species,
        @NotNull Sex sex,
        int maxSatiety,
        int visionRange,
        @NotNull Age age,
        int stepsBeforeGrow,
        int satiety,
        int stepsBeforeSatietyDecrease
    ) {
        this.species = species;
        this.sex = sex;
        this.maxSatiety = maxSatiety;
        this.visionRange = visionRange;
        this.age = age;
        this.stepsBeforeGrow = stepsBeforeGrow;
        this.satiety = satiety;
        this.stepsBeforeSatietyDecrease = stepsBeforeSatietyDecrease;
    }

    /**
     * Constructor for the Animal class primarily used for testing purposes.
     *
     * @param species The species of the animal.
     * @param isAlive The boolean indicating if the animal is alive.
     */
    public Animal(@NotNull AnimalSpecies species, boolean isAlive) {
        this.species = species;
        this.sex = Sex.Male;
        this.maxSatiety = 10;
        this.visionRange = 2;
        this.age = Age.Adult;
        this.stepsBeforeGrow = 10;
        this.satiety = 10;
        this.stepsBeforeSatietyDecrease = 10;
        this.isAlive = isAlive;
    }


    public void setNearestShelter(@NotNull Shelter shelter) {
        nearestShelter = shelter;
    }

    public void setGround(@NotNull Ground ground) {
        currentGround = ground;
    }
    public Ground getGround() {
        return currentGround;
    }

    public Coordinates getCoordinates() {
        return currentGround.coordinates;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public @NotNull AnimalSpecies getSpecies() {
        return species;
    }

    public @NotNull Age getAge() {
        return age;
    }

    public boolean isInShelter() {
        return currentGround.coordinates == nearestShelter.getCoordinates();
    }

    protected void resetMovingDirection() {
        movingDirection = null;
    }


    /**
     * Get nearest ground (top, right, bottom, left) that meets the passed condition.
     *
     * @param predicate The condition that the ground must meet.
     *
     * @return The list of grounds that meets the condition. If no ground meets the condition, an empty list is
     * returned.
     */
    @NotNull public List<Ground> getNearestGrounds(@NotNull Predicate<Ground> predicate) {
        List<Ground> grounds = currentGround.getNearestGrounds();
        return grounds.stream().filter(predicate).toList();
    }

    /**
     * Returns a list of Ground objects that are within the vision range of the given source ground.
     *
     * @param sourceGround The ground from which the vision range is calculated.
     *
     * @return A list of Ground objects within the vision range.
     */
    protected List<Ground> getGroundsInVisionRange(@NotNull Ground sourceGround) {
        ArrayList<Ground> horizontalGrounds = getGroundsHorizontalLineInVisionRange(sourceGround);
        ArrayList<Ground> grounds = new ArrayList<>(horizontalGrounds);

        grounds.addAll(getGroundsVerticalLineInVisionRange(sourceGround, sourceGround.coordinates));
        for (Ground ground : horizontalGrounds) {
            grounds.addAll(getGroundsVerticalLineInVisionRange(ground, sourceGround.coordinates));
        }

        return grounds;
    }

    /**
     * Returns a list of Ground objects that are horizontally in the vision range of the given source ground.
     *
     * @param sourceGround The ground from which the vision range is calculated.
     *
     * @return A list of Ground objects horizontally within the vision range.
     */
    private ArrayList<Ground> getGroundsHorizontalLineInVisionRange(@NotNull Ground sourceGround) {
        ArrayList<Ground> grounds = new ArrayList<>();

        Ground nextGround = sourceGround.getNextLeft();
        while (
            nextGround != null &&
            nextGround.coordinates.areInRageWithOtherCoordinates(sourceGround.coordinates, visionRange)
        ) {
            grounds.add(nextGround);
            nextGround = nextGround.getNextLeft();
        }
        nextGround = sourceGround.getNextRight();
        while (
            nextGround != null &&
            nextGround.coordinates.areInRageWithOtherCoordinates(sourceGround.coordinates, visionRange)
        ) {
            grounds.add(nextGround);
            nextGround = nextGround.getNextRight();
        }

        return grounds;
    }

    /**
     * Returns a list of Ground objects that are vertically in the vision range of the given ground.
     *
     * @param ground The ground from which the vision range is calculated.
     * @param sourceCoordinates The coordinates of the source ground.
     *
     * @return A list of Ground objects vertically within the vision range.
     */
    private ArrayList<Ground> getGroundsVerticalLineInVisionRange(
        @NotNull Ground ground,
        @NotNull Coordinates sourceCoordinates
    ) {
        ArrayList<Ground> grounds = new ArrayList<>();

        Ground nextGround = ground.getNextTop();
        while (
            nextGround != null &&
            nextGround.coordinates.areInRageWithOtherCoordinates(sourceCoordinates, visionRange)
        ) {
            grounds.add(nextGround);
            nextGround = nextGround.getNextTop();
        }
        nextGround = ground.getNextBottom();
        while (
            nextGround != null &&
            nextGround.coordinates.areInRageWithOtherCoordinates(sourceCoordinates, visionRange)
        ) {
            grounds.add(nextGround);
            nextGround = nextGround.getNextBottom();
        }

        return grounds;
    }

    /**
     * Returns a ground in the vision range of the animal that meets the condition and is closest to the animal.
     *
     * @param condition The condition that the ground must meet.
     *
     * @return The ground that meets the condition and is closest to the animal.
     */
    protected Ground getGroundInVisionRangeByConditionSortedByDistance(@NotNull Predicate<Ground> condition) {
        List<Ground> grounds = getGroundsInVisionRange(currentGround);
        return grounds.stream()
                .filter(condition).min((ground1, ground2) -> Double.compare(
                        currentGround.coordinates.getDistanceToOtherCoordinates(ground1.coordinates),
                        currentGround.coordinates.getDistanceToOtherCoordinates(ground2.coordinates)
                ))
                .orElse(null);
    }


    /**
     * Move the animal to the next ground.
     */
    protected void moveToNextGround(Ground nextGround) {
        if (moveTowardsGround(nextGround)) {
            resetMovingDirection();
            return;
        }

        if (movingDirection != null) {
            if (!moveInDirection(movingDirection)) {
                resetMovingDirection();
            } else {
                return;
            }
        }

        if (moveAwayFromShelter()) return;
        if (moveTowardsShelter()) return;
        tryToLeaveCell();
    }

    /**
     * Moves the animal to another ground.
     * The animal will try to move to the next top ground, then to the next right one, then to the next bottom one,
     * and finally to the next left one.
     */
    protected void tryToLeaveCell() {
        Coordinates c = currentGround.coordinates;

        if(moveToNextGroundByCoordinatesDifference(c, new Coordinates(c.x, c.y - 1, true))) return;
        if(moveToNextGroundByCoordinatesDifference(c, new Coordinates(c.x + 1, c.y, true))) return;
        if(moveToNextGroundByCoordinatesDifference(c, new Coordinates(c.x, c.y + 1, true))) return;
        moveToNextGroundByCoordinatesDifference(c, new Coordinates(c.x - 1, c.y, true));
    }

    /**
     * Moves the animal towards the nearest shelter. If the animal entered the ground where the shelter is located,
     * it will try to enter the shelter.
     */
    protected void runToShelter() {
        if (isInShelter() || nearestShelter == null || currentGround == null) return;

        moveToNextGroundByCoordinatesDifference(nearestShelter.getCoordinates(), currentGround.coordinates);

        if (runsToShelter && isInShelter()) {
            if (nearestShelter.animals.size() < nearestShelter.capacity) {
                getGround().removeAnimal();
                nearestShelter.animals.add(this);
                runsToShelter = false;
            } else {
                Shelter anotherNearestShelter = nearestShelter.getNearestShelter();
                if (anotherNearestShelter != null) {
                    nearestShelter = anotherNearestShelter;
                }
            }
        }

    }

    /**
     * Try to move the animal to the ground.
     *
     * @param ground The ground where the animal is moved.
     *
     * @return True if the animal was moved to the ground, false otherwise.
     */
    protected boolean moveToGround(Ground ground) {
        if (currentGround == null || ground == null || !ground.isAvailable()) return false;

        currentGround.removeAnimal();

        if (currentGround.getShelter() != null) {
            currentGround.getShelter().animals.remove(this);
        }

        ground.setAnimal(this);
        currentGround = ground;

        return true;
    }

    /**
     * Moves the animal towards the ground.
     *
     * @param ground The ground animal is moving towards.
     */
    protected boolean moveTowardsGround(Ground ground) {
        if (currentGround == null || ground == null) return false;

        return moveToNextGroundByCoordinatesDifference(currentGround.coordinates, ground.coordinates);
    }

    /**
     * Moves the animal to the next ground based on the difference between the coordinates of the first and second
     * coordinates.
     */
    protected boolean moveToNextGroundByCoordinatesDifference(
        @NotNull Coordinates sourceCoordinates,
        @NotNull Coordinates targetCoordinates
    ) {
        if (
            currentGround == null ||
            sourceCoordinates.x <= 0 || sourceCoordinates.y <= 0 ||
            targetCoordinates.x <= 0 || targetCoordinates.y <= 0
        ) return false;

        CoordinatesDifference coordDifference = new CoordinatesDifference(sourceCoordinates, targetCoordinates);

        if (Math.abs(coordDifference.x) > Math.abs(coordDifference.y)) {
            if (coordDifference.x > 0) {
                if (moveToGround(currentGround.getNextRight())) return true;
            } else {
                if (moveToGround(currentGround.getNextLeft())) return true;
            }
            if (moveToGround(currentGround.getNextTop())) return true;
            return moveToGround(currentGround.getNextBottom());
        } else {
            if (coordDifference.y > 0) {
                if (moveToGround(currentGround.getNextBottom())) return true;
            } else {
                if (moveToGround(currentGround.getNextTop())) return true;
            }
            if (moveToGround(currentGround.getNextLeft())) return true;
            return moveToGround(currentGround.getNextRight());
        }
    }

    /**
     * Moves the animal in the given direction.
     *
     * @param direction The direction in which the animal is moving.
     *
     * @return True if the animal was moved in the direction, false otherwise.
     */
    protected boolean moveInDirection(@NotNull Direction direction) {
        if (currentGround == null) return false;

        return switch (direction) {
            case Top, TopRight, TopLeft -> moveTowardsGround(currentGround.getNextTop());
            case Right -> moveTowardsGround(currentGround.getNextRight());
            case Bottom, BottomRight, BottomLeft -> moveTowardsGround(currentGround.getNextBottom());
            case Left -> moveTowardsGround(currentGround.getNextLeft());
        };
    }

    /**
     * Moves the animal towards the shelter.
     *
     * @return True if the animal was moved towards the shelter, false otherwise.
     */
    protected boolean moveTowardsShelter() {
        if (currentGround == null || nearestShelter == null) return false;

        Coordinates currentCoordinates = currentGround.coordinates;
        Coordinates shelterCoordinates = nearestShelter.getCoordinates();

        if (moveToNextGroundByCoordinatesDifference(currentCoordinates, shelterCoordinates)) {
            movingDirection = currentCoordinates.getDirectionToOtherCoordinates(shelterCoordinates);
            return true;
        }
        return false;
    }

    /**
     * Moves the animal away from the shelter.
     *
     * @return True if the animal was moved away from the shelter, false otherwise.
     */
    protected boolean moveAwayFromShelter() {
        if (currentGround == null || nearestShelter == null) return false;

        Coordinates currentCoordinates = currentGround.coordinates;
        Coordinates shelterCoordinates = nearestShelter.getCoordinates();
        Coordinates oppositeToShelterCoordinates =
                currentCoordinates.getOppositeCoordinatesToCoordinates(shelterCoordinates);

        if (moveToNextGroundByCoordinatesDifference(currentCoordinates, oppositeToShelterCoordinates)) {
            movingDirection = currentCoordinates.getDirectionToOtherCoordinates(currentGround.coordinates);
            return true;
        }
        return false;
    }


    /**
     * The method that should be called when the animal is doing a step.
     * It checks if the animal made enough steps to decrease its satiety. If it did, the satiety is decreased.
     */
    protected void tryToDecreaseSatiety() {
        if (stepsAfterSatietyDecrease == stepsBeforeSatietyDecrease) {
            if (tryToDie()) return;
            satiety--;
            stepsAfterSatietyDecrease = 0;
        } else {
            stepsAfterSatietyDecrease++;
        }
    }

    /**
     * The method that should be called when the animal is doing a step.
     * It checks if the animal is hungry and its satiety needs to be decreased. If the satiety needs to be decreased
     * and the satiety is 0, the animal dies.
     *
     * @return True if the animal died, false otherwise.
     */
    private boolean tryToDie() {
        if (satiety == 0 && stepsAfterSatietyDecrease == stepsBeforeSatietyDecrease) {
            isAlive = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * The method that should be called when the animal is doing a step.
     * It checks if the animal made enough steps to grow. If it did, the animal grows.
     */
    protected void tryToGrow() {
        if (stepsAfterGrow == stepsBeforeGrow) {
            if (!grow()) {
                isAlive = false;
                return;
            }
            stepsAfterGrow = 0;
        } else {
            stepsAfterGrow++;
        }
    }

    /**
     * Grow the animal.
     * If the animal is a child, it grows into an adult. If the animal is an adult, it grows into a senior.
     * If the animal is a senior, it dies.
     */
    private boolean grow() {
        if (age == Age.Child) {
            age = Age.Adult;
            return true;
        } else if (age == Age.Adult) {
            age = Age.Senior;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Try to remove the dead animal from the ground if it has been dead for 20 steps after its death.
     *
     * @param onRemoveAnimalFromMap The function that removes the animal from the map's list of animals.
     */
    public void tryToDecompose(Function<Animal, Boolean> onRemoveAnimalFromMap) {
        if (stepsAfterDeath == 20) {
            onRemoveAnimalFromMap.apply(this);
            currentGround.removeAnimal();
        } else {
            stepsAfterDeath++;
        }
    }

}
