package simulation.animal_simulation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The SimulationSettings class holds the settings for the animal simulation.
 * It includes properties such as map size, grass amount, thick vegetation amount, rabbit and fox shelter IDs,
 * timeout between simulation steps, and a flag for enabling logs.
 *
 * <p>This class provides methods to set these properties, as well as to export the current settings into a JSON file
 * and import settings from a JSON file.</p>
 */
public class SimulationSettings {

    public int mapSize;
    public int grassAmount;
    public int thickVegetationAmount;
    public List<Integer> rabbitShelterIds;
    public List<Integer> foxShelterIds;
    public int timeoutBetweenSimulationSteps;
    public boolean logsEnabled;

    /**
     * Default constructor for the SimulationSettings class.
     */
    public SimulationSettings() {
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public void setGrassAmount(int grassAmount) {
        this.grassAmount = grassAmount;
    }

    public void setThickVegetationAmount(int thickVegetationAmount) {
        this.thickVegetationAmount = thickVegetationAmount;
    }

    public void setRabbitShelterIds(List<Integer> rabbitShelterIds) {
        this.rabbitShelterIds = rabbitShelterIds;
    }

    public void setFoxShelterIds(List<Integer> foxShelterIds) {
        this.foxShelterIds = foxShelterIds;
    }

    public void setTimeoutBetweenSimulationSteps(int timeoutBetweenSimulationSteps) {
        this.timeoutBetweenSimulationSteps = timeoutBetweenSimulationSteps;
    }

    public void setLogsEnabled(boolean logsEnabled) {
        this.logsEnabled = logsEnabled;
    }


    public void exportSimulationSettingsIntoFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime date = LocalDateTime.now();
        String formattedDate = date.format(formatter);
        try {
            objectMapper.writeValue(new File("./exported_settings/simulation_settings_" + formattedDate + ".json"), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importSimulationSettingsFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SimulationSettings simulationSettings = objectMapper.readValue(
//                    new File("./exported_settings/simulation_settings.json"),
                    new File(filePath),
                    SimulationSettings.class
            );
            this.mapSize = simulationSettings.mapSize;
            this.grassAmount = simulationSettings.grassAmount;
            this.thickVegetationAmount = simulationSettings.thickVegetationAmount;
            this.rabbitShelterIds = simulationSettings.rabbitShelterIds;
            this.foxShelterIds = simulationSettings.foxShelterIds;
            this.timeoutBetweenSimulationSteps = simulationSettings.timeoutBetweenSimulationSteps;
            this.logsEnabled = simulationSettings.logsEnabled;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
