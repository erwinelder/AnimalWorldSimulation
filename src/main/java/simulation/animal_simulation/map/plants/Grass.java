package simulation.animal_simulation.map.plants;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.map.Ground;

/**
 * The Grass class represents grass in the simulation.
 * Each Grass object is located on a specific ground and has a quantity.
 * The Grass class provides methods for eating the grass and upgrading it to thick vegetation.
 * If the quantity of the grass exceeds 4, the grass is upgraded to thick vegetation.
 * The Grass class is responsible for the grass's consumption and transformation during the simulation.
 *
 * <p>This class extends the Plant class, inheriting its common properties and behaviors.</p>
 */
public class Grass extends Plant {

    /**
     * Constructor for the Grass class.
     * The initial quantity of the grass is set to 3.
     *
     * @param ground The ground where this grass is located.
     */
    public Grass(@NotNull Ground ground) {
        super(ground, 3);
    }

    /**
     * Constructor for the Grass class.
     *
     * @param ground The ground where this grass is located.
     * @param quantity The initial quantity of the grass.
     *
     * @throws IllegalArgumentException If the initial quantity is more than 4.
     */
    public Grass(@NotNull Ground ground, int quantity) {
        super(ground, quantity);
        if (quantity > 4) throw new IllegalArgumentException();
    }

    /**
     * Tries to regrow the grass.
     * The grass will regrow after 5 steps have passed since the last regrowth.
     * If the number of steps after the last regrowth is less than 5, it increments the steps after regrowth.
     * Otherwise, it increases the quantity of the grass and resets the steps after regrowth.
     */
    public void tryToRegrowth() {
        if (stepsAfterRegrowth < 10) {
            stepsAfterRegrowth++;
        } else {
            increaseQuantity();
            stepsAfterRegrowth = 0;
        }
    }

    /**
     * Decreases the quantity of the grass by 1 if there is any left.
     *
     * @return True if the grass could be eaten (there was some left), false otherwise.
     */
    public Boolean eat() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

    /**
     * Increases the quantity of the grass by 1.
     * If the quantity exceeds 4, the grass is upgraded to thick vegetation.
     */
    public void increaseQuantity() {
        quantity++;
        if (quantity > 4) {
            upgradeToThickVegetation();
        }
    }

    /**
     * Upgrades the grass to thick vegetation.
     * The grass is removed from the ground and replaced with thick vegetation.
     */
    private void upgradeToThickVegetation() {
        ground.removeGrass();
        ground.setThickVegetation(new ThickVegetation(ground, 5));
    }

}
