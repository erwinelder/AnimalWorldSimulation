package simulation.animal_simulation;

import simulation.animal_simulation.animals.Animal;
import simulation.animal_simulation.animals.Rabbit;
import simulation.animal_simulation.map.navigation.Coordinates;

import java.util.logging.Logger;

/**
 * The LoggerController class is responsible for logging events in the animal simulation.
 * It includes methods for logging various events such as animal movement, birth of rabbits and foxes,
 * rabbit consumption, animal decomposition, and grass spreading.
 *
 * <p>This class uses the java.util.logging.Logger for logging these events. The logging can be enabled or disabled
 * using the isLoggingEnabled flag in the constructor.</p>
 */
public class LoggerController {

    private static final Logger LOGGER = Logger.getLogger(LoggerController.class.getName());

    public LoggerController(boolean isLoggingEnabled) {
        if (!isLoggingEnabled) {
            LOGGER.setUseParentHandlers(false);
        }
    }

    public void logAnimalMovement(Animal animal, Coordinates startCoordinates) {
        if (animal.getCoordinates().areEqualTo(startCoordinates)) return;
        LOGGER.info(animal + " has moved from " + startCoordinates + " to " + animal.getCoordinates());
    }

    public void logRabbitWasBorn(Rabbit babyRabbit) {
        LOGGER.info("Rabbit " + babyRabbit + " was born");
    }

    public void logFoxWasBorn(Animal babyFox) {
        LOGGER.info("Fox " + babyFox + " was born");
    }

    public void logRabbitWasEaten(Rabbit rabbit) {
        LOGGER.info("Rabbit " + rabbit + " was eaten");
    }

    public void logAnimalDecomposition(Animal animal) {
        LOGGER.info(animal + " has been decomposed and remove from the map");
    }

    public void logGrassWasSpread(Coordinates thisGrassCoordinates, Coordinates spreadCoordinates) {
        LOGGER.info("Grass on " + thisGrassCoordinates + " was spread to " + spreadCoordinates);
    }

}
