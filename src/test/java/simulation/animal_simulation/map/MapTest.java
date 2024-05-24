package simulation.animal_simulation.map;

import org.junit.jupiter.api.Test;
import simulation.animal_simulation.LoggerController;
import simulation.animal_simulation.animals.Animal;
import simulation.animal_simulation.animals.AnimalSpecies;
import simulation.animal_simulation.animals.Rabbit;
import simulation.animal_simulation.animals.Sex;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void addRabbitsFromSheltersAndGetRabbitCount() throws IllegalAccessException {
        List<Animal> rabbits = List.of(
                new Rabbit(Sex.Male),
                new Rabbit(Sex.Male),
                new Rabbit(false)
        );
        Shelter shelter = new Shelter(1, ShelterType.Burrow, AnimalSpecies.Rabbit, 5, rabbits);
        Map map = new Map(
                10, 10, 10,
                List.of(shelter), List.of(), new LoggerController(false)
        );
        assertEquals(2, map.getRabbitCount());
    }
}