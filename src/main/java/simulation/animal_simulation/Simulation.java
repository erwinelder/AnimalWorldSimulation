package simulation.animal_simulation;

import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.animals.*;
import simulation.animal_simulation.map.Map;
import simulation.animal_simulation.map.Shelter;
import simulation.animal_simulation.map.ShelterType;

import java.util.ArrayList;
import java.util.List;

/**
 * The Simulation class is responsible for managing and running the entire simulation.
 * It contains methods for initializing the simulation, adding animals to the map, and running the simulation.
 * The simulation is run in steps, with each step representing a unit of time.
 * During each step, the grass on the map regrows and each animal takes its turn.
 * The simulation continues until there are no more living animals.
 */
public class Simulation {

    /**
     * Main method that starts the simulation.
     *
     * @param simulationSettings The simulation settings.
     * @param loggerController The logger controller.
     *
     * @return The map with all the shelters and animals.
     *
     * @throws IllegalAccessException If there is illegal access to a field.
     */
    public Map prepareSimulation(
        SimulationSettings simulationSettings,
        LoggerController loggerController
    ) throws IllegalAccessException {

        List<Shelter> rabbitShelters = getDefaultRabbitShelters(simulationSettings.rabbitShelterIds);
        List<Shelter> foxShelters = getDefaultFoxShelters(simulationSettings.foxShelterIds);

        Map map = new Map(
                simulationSettings.mapSize,
                simulationSettings.grassAmount,
                simulationSettings.thickVegetationAmount,
                rabbitShelters,
                foxShelters,
                loggerController
        );

        setNearestSheltersForEachInList(rabbitShelters);
        setNearestSheltersForEachInList(foxShelters);

        return map;
    }


    /**
     * This is a test method. It will be removed or refactored in the future.
     * Returns a list of default rabbit shelters.
     */
    private static List<Shelter> getDefaultRabbitShelters(List<Integer> shelterIds) {
        ArrayList<Shelter> shelters = new ArrayList<>();
        for (int i : shelterIds) {
            shelters.add(
                new Shelter(
                    i, ShelterType.Burrow, AnimalSpecies.Rabbit, 5,
                    getDefaultRabbits(3)
                )
            );
            setCurrentNearestShelterForAnimals(shelters.getLast(), shelters.getLast().animals);
        }
        return shelters;
    }

    /**
     * Returns a list of default rabbits.
     *
     * @param count Number of rabbits.
     */
    private static List<Animal> getDefaultRabbits(int count) {
        ArrayList<Animal> rabbits = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Sex finalSex = i % 2 == 0 ? Sex.Female : Sex.Male;
            rabbits.add(new Rabbit(finalSex));
        }
        return rabbits;
    }

    /**
     * This is a test method. It will be removed or refactored in the future.
     * Returns a list of default fox shelters.
     */
    private static List<Shelter> getDefaultFoxShelters(List<Integer> shelterIds) {
        ArrayList<Shelter> shelters = new ArrayList<>();
        for (int i : shelterIds) {
            shelters.add(
                new Shelter(
                    i, ShelterType.Burrow, AnimalSpecies.Fox, 5,
                    getDefaultFoxes(3)
                )
            );
            setCurrentNearestShelterForAnimals(shelters.getLast(), shelters.getLast().animals);
        }
        return shelters;
    }

    /**
     * Returns a list of default foxes.
     *
     * @param count Number of foxes.
     */
    private static List<Animal> getDefaultFoxes(int count) {
        ArrayList<Animal> foxes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Sex finalSex = i % 2 == 0 ? Sex.Female : Sex.Male;
            foxes.add(new Fox(finalSex));
        }
        return foxes;
    }

    /**
     * Sets the nearest shelter for each animal in the list.
     */
    private static void setCurrentNearestShelterForAnimals(@NotNull Shelter shelter, @NotNull List<Animal> animals) {
        for (Animal animal : animals) {
            animal.setNearestShelter(shelter);
        }
    }

    /**
     * Sets the nearest shelters for each shelter in the list.
     */
    private static void setNearestSheltersForEachInList(@NotNull List<Shelter> shelters) {
        for (Shelter shelter : shelters) {
            shelter.setNearestSheltersFromSheltersList(shelters);
        }
    }

}