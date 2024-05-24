package simulation.animal_simulation.map.plants;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.map.Ground;

/**
 * The Plant class represents a plant in the animal simulation.
 * Each plant has a quantity and a ground on which it is located.
 * The steps after regrowth of the plant are also tracked.
 *
 * <p>This is an abstract class and forms the base for specific types of plants in the simulation.
 * The specific types of plants will define their own behaviors and properties.</p>
 */
public abstract class Plant {

    public int quantity;
    @NotNull final protected Ground ground;
    protected int stepsAfterRegrowth = 0;

    /**
     * Constructor for the Plant class.
     *
     * @param ground The ground on which the plant is located.
     * @param quantity The quantity of the plant.
     */
    public Plant(@NotNull Ground ground, int quantity) {
        this.quantity = quantity;
        this.ground = ground;
    }

}
