package simulation.animal_simulation.map.navigation;

/**
 * The Coordinates class represents the coordinates of a location in the simulation.
 * Each Coordinates object has an x and y value.
 * The Coordinates class provides methods for checking if these coordinates are within a certain range of other coordinates,
 * and for calculating the distance to other coordinates.
 * The Coordinates class is responsible for managing the position of objects in the simulation.
 */
public class Coordinates {

    public final int x;
    public final int y;

    /**
     * Constructor for the Coordinates class.
     *
     * @param mapSize The size of the map.
     * @param idOnMap The ID of the location on the map.
     */
    public Coordinates(int mapSize, int idOnMap) {
        x = idOnMap % mapSize == 0 ? mapSize : idOnMap % mapSize;
        y = idOnMap % mapSize == 0 ? idOnMap / mapSize : idOnMap / mapSize + 1;
    }

    /**
     * Constructor for the Coordinates class.
     *
     * @param x The size of the map.
     * @param y The ID of the location on the map.
     * @param areDirectCoordinates True if the passed coordinates are direct coordinates, false if they are map size
     *                             and id on map respectively, so they will be converted to direct coordinates.
     */
    public Coordinates(int x, int y, boolean areDirectCoordinates) {
        if (areDirectCoordinates) {
            this.x = x;
            this.y = y;
        } else {
            this.x = x % y == 0 ? y : x % y;
            this.y = x % y == 0 ? x / y : x / y + 1;
        }
    }

    /**
     * Checks if these coordinates are within a certain range of other coordinates.
     *
     * @param coordinates The other coordinates.
     * @param range The range.
     *
     * @return True if these coordinates are within the range of the other coordinates, false otherwise.
     */
    public boolean areInRageWithOtherCoordinates(Coordinates coordinates, int range) {
        return Math.pow(Math.abs(x - coordinates.x), 2) +
            Math.pow(Math.abs(y - coordinates.y), 2) <= Math.pow(range, 2);
    }

    /**
     * Calculates the distance to other coordinates.
     *
     * @param coordinates The other coordinates.
     *
     * @return The distance to the other coordinates.
     */
    public double getDistanceToOtherCoordinates(Coordinates coordinates) {
        return Math.sqrt(
            Math.pow(Math.abs(x - coordinates.x), 2) +
            Math.pow(Math.abs(y - coordinates.y), 2)
        );
    }

    /**
     * Returns the direction to other coordinates.
     *
     * @param targetCoordinates The target coordinates.
     *
     * @return The direction to the other coordinates.
     */
    public Direction getDirectionToOtherCoordinates(Coordinates targetCoordinates) {
        int xDifference = targetCoordinates.x - x;
        int yDifference = targetCoordinates.y - y;
        double ratio = xDifference != 0 || yDifference != 0 ? Math.abs(
                Math.abs(xDifference) > Math.abs(yDifference) ? yDifference / xDifference : xDifference / yDifference
        ) : 0;

        if (ratio < 0.5) {
            if (xDifference > yDifference) {
                return xDifference > 0 ? Direction.Right : Direction.Left;
            } else {
                return yDifference > 0 ? Direction.Bottom : Direction.Top;
            }
        } else {
            if (xDifference > 0 && yDifference > 0) {
                return Direction.TopLeft;
            } else if (xDifference > 0 && yDifference < 0) {
                return Direction.BottomLeft;
            } else if (xDifference < 0 && yDifference > 0) {
                return Direction.TopRight;
            } else {
                return Direction.BottomRight;
            }
        }
    }

    /**
     * Returns the opposite coordinates to the passed coordinates.
     *
     * @param coordinates The coordinates.
     *
     * @return The opposite coordinates to the passed coordinates.
     */
    public Coordinates getOppositeCoordinatesToCoordinates(Coordinates coordinates) {
        return new Coordinates(
            coordinates.x + (2 * (x - coordinates.x)),
            coordinates.y + (2 * (y - coordinates.y)),
            true
        );
    }

    public boolean areEqualTo(Coordinates coordinates) {
        return x == coordinates.x && y == coordinates.y;
    }

    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }

}
