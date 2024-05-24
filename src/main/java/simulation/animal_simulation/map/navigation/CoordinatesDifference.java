package simulation.animal_simulation.map.navigation;

import org.jetbrains.annotations.NotNull;

/**
 * The CoordinatesDifference class represents the difference between two sets of coordinates in the simulation.
 * Each CoordinatesDifference object has an x and y difference, which are calculated based on the source and target coordinates.
 *
 * <p>This class is used to calculate the difference in position between two coordinates, which can be used for navigation
 * and movement in the simulation.</p>
 */
public class CoordinatesDifference {

    public final int x;
    public final int y;

    /**
     * Constructor for the CoordinatesDifference class.
     *
     * @param source The source coordinates.
     * @param target The target coordinates.
     */
    public CoordinatesDifference(@NotNull Coordinates source, @NotNull Coordinates target) {
        x = target.x - source.x;
        y = target.y - source.y;
    }

}
