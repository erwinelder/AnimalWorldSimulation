package simulation.animal_simulation.map;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.animals.Animal;
import simulation.animal_simulation.map.navigation.Coordinates;
import simulation.animal_simulation.map.plants.Grass;
import simulation.animal_simulation.map.plants.ThickVegetation;

import java.util.ArrayList;
import java.util.List;

/**
 * The Ground class represents a single unit of the simulation map.
 * Each Ground object has a unique identifier and coordinates on the map.
 * A Ground can contain grass, thick vegetation, a shelter, or an animal.
 * It also keeps track of its neighboring Grounds in all four directions (top, right, bottom, left).
 * The Ground class provides methods for setting and getting its contents and neighbors.
 */
public class Ground {

    public final int id;
    boolean isEmpty = true;
    public final Coordinates coordinates;

    private Grass grass = null;
    private ThickVegetation thickVegetation = null;
    private Shelter shelter = null;
    private Animal animal = null;

    private Ground nextTop = null;
    private Ground nextRight = null;
    private Ground nextBottom = null;
    private Ground nextLeft = null;

    /**
     * Constructor for the Ground class.
     *
     * @param id The unique identifier for this ground.
     * @param coordinates The coordinates of this ground on the map.
     */
    public Ground(int id, Coordinates coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    /**
     * Constructor for the Ground class.
     *
     * @param id The unique identifier for this ground.
     * @param coordinates The coordinates of this ground on the map.
     * @param grass The grass located on this ground, if any.
     */
    public Ground(int id, Coordinates coordinates, @NotNull Grass grass) {
        this.id = id;
        this.isEmpty = false;
        this.coordinates = coordinates;
        this.grass = grass;
    }

    /**
     * Constructor for the Ground class.
     *
     * @param id The unique identifier for this ground.
     * @param coordinates The coordinates of this ground on the map.
     * @param thickVegetation The thick vegetation located on this ground, if any.
     */
    public Ground(int id, Coordinates coordinates, @NotNull ThickVegetation thickVegetation) {
        this.id = id;
        this.isEmpty = false;
        this.coordinates = coordinates;
        this.thickVegetation = thickVegetation;
    }

    /**
     * Constructor for the Ground class.
     *
     * @param id The unique identifier for this ground.
     * @param coordinates The coordinates of this ground on the map.
     * @param shelter The shelter located on this ground, if any.
     */
    public Ground(int id, Coordinates coordinates, @NotNull Shelter shelter) {
        this.id = id;
        this.isEmpty = false;
        this.coordinates = coordinates;
        this.shelter = shelter;
    }


    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isAvailable() {
        return shelter == null && animal == null;
    }

    public boolean isAvailableForGrassSpread() {
        return shelter == null && !hasGrass() && !hasThickVegetation();
    }

    public boolean hasGrass() {
        return grass != null;
    }
    public void setGrass(@NotNull Grass grass) {
        if (this.grass != null || thickVegetation != null || shelter != null) {
            throw new IllegalArgumentException();
        }

        this.grass = grass;
        this.isEmpty = false;
    }
    public Grass getGrass() {
        return grass;
    }
    public void removeGrass() {
        this.grass = null;
        this.isEmpty = true;
    }

    public boolean hasThickVegetation() {
        return thickVegetation != null;
    }
    public void setThickVegetation(@NotNull ThickVegetation thickVegetation) {
        if (grass != null || this.thickVegetation != null || shelter != null) {
            throw new IllegalArgumentException();
        }

        this.thickVegetation = thickVegetation;
        this.isEmpty = false;
    }
    public ThickVegetation getThickVegetation() {
        return thickVegetation;
    }
    public void removeThickVegetation() {
        this.thickVegetation = null;
        this.isEmpty = true;
    }

    public int getGrassQuantity() {
        return grass != null ? grass.quantity : (
            thickVegetation != null ? thickVegetation.quantity : 0
        );
    }

    public void setNextTop(Ground nextTop) {
        this.nextTop = nextTop;
    }
    public void setNextRight(Ground nextRight) {
        this.nextRight = nextRight;
    }
    public void setNextBottom(Ground nextBottom) {
        this.nextBottom = nextBottom;
    }
    public void setNextLeft(Ground nextLeft) {
        this.nextLeft = nextLeft;
    }

    public Ground getNextTop() {
        return nextTop;
    }
    public Ground getNextRight() {
        return nextRight;
    }
    public Ground getNextBottom() {
        return nextBottom;
    }
    public Ground getNextLeft() {
        return nextLeft;
    }

    public boolean hasShelter() {
        return shelter != null;
    }
    public boolean setShelter(Shelter shelter) {
        if (grass != null || thickVegetation != null || this.shelter != null) {
            return false;
        }

        this.shelter = shelter;
        return true;
    }
    public Shelter getShelter() {
        return shelter;
    }

    public ShelterType getShelterType() {
        if (shelter == null) return null;
        return shelter.type;
    }

    public boolean hasAnimal() {
        return animal != null;
    }
    public void setAnimal(Animal animal) {
        if (this.animal != null) {
            return;
        }

        animal.setGround(this);
        this.animal = animal;
    }
    public Animal getAnimal() {
        return animal;
    }
    public void removeAnimal() {
        this.animal = null;
    }

    /**
     * Returns the neighboring Grounds of this Ground. Does not include null values.
     *
     * @return A list of neighboring Grounds.
     */
    public List<Ground> getNearestGrounds() {
        ArrayList<Ground> grounds = new ArrayList<>();
        if (getNextTop() != null) grounds.add(getNextTop());
        if (getNextRight() != null) grounds.add(getNextRight());
        if (getNextBottom() != null) grounds.add(getNextBottom());
        if (getNextLeft() != null) grounds.add(getNextLeft());
        return grounds.stream().toList();
    }

}
