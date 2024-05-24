package simulation.animal_simulation.map.plants;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.map.Ground;
import simulation.animal_simulation.map.navigation.Coordinates;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * The ThickVegetation class represents thick vegetation in the simulation.
 * Each ThickVegetation object is located on a specific ground and has a quantity.
 * The ThickVegetation class provides methods for eating the vegetation and downgrading it to grass.
 * If the quantity of the vegetation drops below 5, the vegetation is downgraded to grass.
 * The ThickVegetation class is responsible for the vegetation's consumption and transformation during the simulation.
 * It also provides methods for regrowth of the vegetation and spreading of the grass to neighboring grounds.
 *
 * <p>This class extends the Plant class, inheriting its common properties and behaviors.</p>
 */
public class ThickVegetation extends Plant {

    /**
     * Constructor for the ThickVegetation class.
     * The initial quantity of the vegetation is set to 7.
     *
     * @param ground The ground where this thick vegetation is located.
     */
    public ThickVegetation(@NotNull Ground ground) {
        super(ground, 7);
    }

    /**
     * Constructor for the ThickVegetation class.
     *
     * @param ground The ground where this thick vegetation is located.
     * @param quantity The initial quantity of the vegetation.
     *
     * @throws IllegalArgumentException If the initial quantity is less than 5.
     */
    public ThickVegetation(@NotNull Ground ground, int quantity) {
        super(ground, quantity);
        if (quantity < 5) throw new IllegalArgumentException();
    }


    /**
     * Tries to regrow the vegetation.
     * The vegetation will regrow after 20 steps have passed since the last regrowth.
     * If the number of steps after the last regrowth is less than 20, it increments the steps after regrowth.
     * Otherwise, it increases the quantity of the vegetation and resets the steps after regrowth.
     */
    public void tryToRegrowth(BiConsumer<Coordinates, Coordinates> onLogGrassWasSpread) {
        if (stepsAfterRegrowth < 10) {
            stepsAfterRegrowth++;
        } else if (quantity < 10) {
            quantity++;
            stepsAfterRegrowth = 0;
        } else if (quantity == 10 && stepsAfterRegrowth == 10) {
            tryToSpread(onLogGrassWasSpread);
        }
    }

    private void tryToSpread(BiConsumer<Coordinates, Coordinates> onLogGrassWasSpread) {
        List<Ground> grounds = ground.getNearestGrounds();
        for (Ground ground : grounds) {
            if (ground.isAvailableForGrassSpread()) {
                ground.setGrass(new Grass(ground, 1));
                onLogGrassWasSpread.accept(this.ground.coordinates, ground.coordinates);
            }
        }
    }

    /**
     * Decreases the quantity of the vegetation by 1.
     * If the quantity drops below 5, the vegetation is downgraded to grass.
     */
    public void eat() {
        quantity--;

        if (quantity < 5) {
            downgradeToGrass();
        }
    }

    /**
     * Downgrades the vegetation to grass.
     * The thick vegetation is removed from the ground and replaced with grass.
     */
    private void downgradeToGrass() {
        ground.removeThickVegetation();
        ground.setGrass(new Grass(ground, 4));
    }

}
