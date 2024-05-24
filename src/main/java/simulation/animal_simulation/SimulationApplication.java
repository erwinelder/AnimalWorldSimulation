package simulation.animal_simulation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import simulation.animal_simulation.animals.Animal;
import simulation.animal_simulation.map.Ground;
import simulation.animal_simulation.map.Map;
import simulation.animal_simulation.map.ShelterType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The SimulationApplication class is the main class of the application.
 * It extends the JavaFX Application class and is responsible for starting the simulation.
 * The SimulationApplication class contains methods for setting up the simulation, showing the map setup window,
 * shelters' ids window, settings window, and starting the simulation.
 * The simulation is run in steps, with each step representing a unit of time.
 * During each step, the grass on the map regrows and each animal takes its turn.
 * The simulation continues until there are no more living animals.
 */
public class SimulationApplication extends Application {

    private Thread simulationThread;
    private Map map;
    @NotNull private final SimulationSettings simulationSettings = new SimulationSettings();

    private int cellSizeInDp = 50;
    @NotNull private final CellImageResources cellImageResources = new CellImageResources();
    private XYChart.Series<Number, Number> rabbitsSeries;
    private XYChart.Series<Number, Number> foxesSeries;
    private XYChart.Series<Number, Number> grassSeries;

    @Override
    public void start(Stage stage) {
        showMapSetupWindow(stage, new Simulation());
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        simulationThread.interrupt();
    }

    /**
     * Shows the map setup window where the user can input the map size, grass amount, and thick vegetation amount.
     * After submitting the values, the user will be redirected to the shelters' ids window.
     * The user can also import settings from a file. If the file is selected, the settings will be imported and the
     * simulation will start.
     */
    private void showMapSetupWindow(@NotNull Stage stage, @NotNull Simulation simulation) {
        GridPane grid = new GridPane();
        setGridPaneForStartWindows(grid);

        TextField mapSizeField = new TextField();
        TextField grassAmountField = new TextField();
        TextField thickVegetationAmountField = new TextField();

        grid.add(new Label("Map size:"), 0, 0);
        grid.add(mapSizeField, 1, 0);
        grid.add(new Label("Grass amount:"), 0, 1);
        grid.add(grassAmountField, 1, 1);
        grid.add(new Label("Thick vegetation amount:"), 0, 2);
        grid.add(thickVegetationAmountField, 1, 2);

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            simulationSettings.setMapSize(Integer.parseInt(mapSizeField.getText()));
            simulationSettings.setGrassAmount(Integer.parseInt(grassAmountField.getText()));
            simulationSettings.setThickVegetationAmount(Integer.parseInt(thickVegetationAmountField.getText()));

            calculateCellSize();
            showSheltersSetupWindow(stage, simulation);
        });

        Button importSettingsButton = getImportSettingsButton(stage, simulation);

        grid.add(continueButton, 1, 3);
        grid.add(importSettingsButton, 1, 4);
        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Returns the import settings button with the action to import settings from a file.
     */
    private @NotNull Button getImportSettingsButton(@NotNull Stage stage, @NotNull Simulation simulation) {
        Button importSettingsButton = new Button("Import settings");
        importSettingsButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pick settings file");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                simulationSettings.importSimulationSettingsFromFile(selectedFile.getAbsolutePath());
                calculateCellSize();
                stage.hide();
                finishSetup(simulation);
            }
        });
        return importSettingsButton;
    }

    /**
     * Shows the shelters' ids window where the user can input the rabbit and fox shelter ground IDs.
     * After submitting the values, the user will be redirected to the settings window.
     */
    private void showSheltersSetupWindow(@NotNull Stage stage, @NotNull Simulation simulation) {
        GridPane grid = new GridPane();
        setGridPaneForStartWindows(grid);

        TextField rabbitShelterField = new TextField();
        TextField foxShelterField = new TextField();

        grid.add(new Label("Rabbit Shelter Ground IDs:"), 0, 0);
        grid.add(rabbitShelterField, 1, 0);
        grid.add(new Label("Fox Shelter Ground IDs:"), 0, 1);
        grid.add(foxShelterField, 1, 1);

        Button btn = new Button("Submit");
        btn.setOnAction(e -> {
            simulationSettings.setRabbitShelterIds(parseIds(rabbitShelterField.getText()).stream().sorted().toList());
            simulationSettings.setFoxShelterIds(parseIds(foxShelterField.getText()).stream().sorted().toList());

            showSettingsWindow(stage, simulation);
        });

        grid.add(btn, 1, 2);
        Scene scene = new Scene(grid, 500, 275);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Shows the settings window where the user can input the timeout between simulation steps and turn on logs.
     * After submitting the values, the simulation will start.
     */
    private void showSettingsWindow(@NotNull Stage stage, @NotNull Simulation simulation) {
        GridPane grid = new GridPane();
        setGridPaneForStartWindows(grid);

        TextField timeoutBetweenSimulationStepsField = new TextField();
        CheckBox logsCheckBox = new CheckBox();
        CheckBox exportSettingsCheckBox = new CheckBox();

        grid.add(new Label("Timeout between simulation steps (ms):"), 0, 0);
        grid.add(timeoutBetweenSimulationStepsField, 1, 0);
        grid.add(new Label("Turn on logs"), 0, 1);
        grid.add(logsCheckBox, 1, 1);
        grid.add(new Label("Export these settings into a file"), 0, 2);
        grid.add(exportSettingsCheckBox, 1, 2);

        Button btn = new Button("Submit");
        btn.setOnAction(e -> {
            simulationSettings
                    .setTimeoutBetweenSimulationSteps(Integer.parseInt(timeoutBetweenSimulationStepsField.getText()));
            simulationSettings.setLogsEnabled(logsCheckBox.isSelected());
            if (exportSettingsCheckBox.isSelected()) {
                simulationSettings.exportSimulationSettingsIntoFile();
            }

            stage.hide();
            finishSetup(simulation);
        });

        grid.add(btn, 1, 3);
        Scene scene = new Scene(grid, 500, 275);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the grid pane for the start windows.
     */
    private void setGridPaneForStartWindows(@NotNull GridPane grid) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
    }

    /**
     * Calculates the cell size based on the screen size and the map size.
     */
    private void calculateCellSize() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth() * 0.95;
        double screenHeight = screenBounds.getHeight() * 0.95;
        double maxMapSize = Math.min(screenWidth, screenHeight);
        cellSizeInDp = (int) maxMapSize / simulationSettings.mapSize;
    }

    /**
     * Finishes the setup of the simulation and starts it.
     */
    private void finishSetup(@NotNull Simulation simulation) {
        try {
            map = simulation
                    .prepareSimulation(simulationSettings, new LoggerController(simulationSettings.logsEnabled));
            startSimulation();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    /**
     * Starts the simulation.
     */
    private void startSimulation() throws IllegalAccessException {

        setupLineCharts();

        GridPane gridPane = new GridPane();
        drawMap(gridPane);

        VBox vBox = new VBox(setupLineChartDataForAnimals(), setupLineChartDataForGrass());
        HBox hBox = new HBox(gridPane, vBox);
        Scene scene = new Scene(
                hBox, simulationSettings.mapSize * cellSizeInDp * 2,
                simulationSettings.mapSize * cellSizeInDp
        );
        Stage mapStage = new Stage();
        mapStage.setScene(scene);
        mapStage.show();

        simulationThread = new Thread(() -> {
            while (map.hasAliveAnimals()) {
                try {
                    Thread.sleep(simulationSettings.timeoutBetweenSimulationSteps);
                } catch (InterruptedException e) {
                    break;
                }

                map.doNextStep();

                Platform.runLater(() -> {
                    drawMap(gridPane);
                    updateLineChartData();
                    map.resetGrassQuantity();
                });
            }
        });
        simulationThread.start();
    }

    /**
     * Draws the map on the grid pane.
     */
    private void drawMap(@NotNull GridPane gridPane) {
        gridPane.getChildren().clear();

        Ground groundInVerticalLine = map.rootGround;
        Ground groundInHorizontalLine = groundInVerticalLine;

        while (groundInVerticalLine != null) {
            while (groundInHorizontalLine != null) {
                ImageView imageView = new ImageView();
                setCellImageViewBasedOnGround(imageView, groundInHorizontalLine);
                gridPane.add(
                    imageView,
                    (groundInHorizontalLine.coordinates.x - 1) * cellSizeInDp,
                    (groundInHorizontalLine.coordinates.y - 1) * cellSizeInDp
                );

                map.increaseGrassQuantity(groundInHorizontalLine.getGrassQuantity());
                groundInHorizontalLine = groundInHorizontalLine.getNextRight();
            }

            groundInVerticalLine = groundInVerticalLine.getNextBottom();
            groundInHorizontalLine = groundInVerticalLine;
        }
    }

    /**
     * Sets the image of the cell based on the ground.
     * If the ground has an animal, the image will be set based on the animal's species, age, and if it's alive.
     * If the ground has grass or thick vegetation, the image will be set based on the quantity of grass.
     * If the ground has a burrow, the image will be set to a burrow image.
     * If the ground is empty, the image will be set to an empty cell image.
     */
    private void setCellImageViewBasedOnGround(@NotNull ImageView imageView, @NotNull Ground ground) {
        if (ground.getAnimal() != null) {
            Animal animal = ground.getAnimal();
            imageView.setImage(
                switch (animal.getSpecies()) {
                    case Rabbit -> cellImageResources
                            .getRabbitCellImage(animal.isAlive(), animal.getAge(), ground.getGrassQuantity());
                    case Fox -> cellImageResources
                            .getFoxCellImage(animal.isAlive(), animal.getAge(), ground.getGrassQuantity());
                }
            );
        } else if (ground.hasGrass()) {
            imageView.setImage(cellImageResources.getGrassCellImageViewBasedOnQuantity(ground.getGrass().quantity));
        } else if (ground.hasThickVegetation()) {
            imageView.setImage(
                cellImageResources.getGrassCellImageViewBasedOnQuantity(ground.getThickVegetation().quantity)
            );
        } else if (ground.getShelterType() == ShelterType.Burrow) {
            imageView.setImage(cellImageResources.burrowCellImage);
        } else {
            imageView.setImage(cellImageResources.emptyCellImage);
        }

        imageView.setFitWidth(cellSizeInDp);
        imageView.setFitHeight(cellSizeInDp);
    }

    /**
     * Sets up the line charts for the animals and the grass.
     */
    private void setupLineCharts() {
        rabbitsSeries = new XYChart.Series<>();
        rabbitsSeries.setName("Rabbits");
        foxesSeries = new XYChart.Series<>();
        foxesSeries.setName("Foxes");
        grassSeries = new XYChart.Series<>();
        grassSeries.setName("Grass");
    }

    /**
     * Sets up the line chart data for animals.
     */
    private LineChart<Number, Number> setupLineChartDataForAnimals() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for (int i = 0; i < 25; i++) {
            rabbitsSeries.getData().add(new XYChart.Data<>(i, map.getRabbitCount()));
            foxesSeries.getData().add(new XYChart.Data<>(i, map.getFoxCount()));
        }
        lineChart.getData().addAll(rabbitsSeries, foxesSeries);
        return lineChart;
    }

    /**
     * Sets up the line chart data for grass.
     */
    private LineChart<Number, Number> setupLineChartDataForGrass() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for (int i = 0; i < 25; i++) {
            grassSeries.getData().add(new XYChart.Data<>(i, map.getGrassQuantity()));
        }
        lineChart.getData().addAll(grassSeries);
        return lineChart;
    }

    /**
     * Updates the line chart data for animals and grass.
     */
    private void updateLineChartData() {

        rabbitsSeries.getData().removeFirst();
        foxesSeries.getData().removeFirst();
        grassSeries.getData().removeFirst();

        int dataSize = rabbitsSeries.getData().size();
        for (int i = 0; i < dataSize; i++) {
            XYChart.Data<Number, Number> rabbitData = rabbitsSeries.getData().get(i);
            XYChart.Data<Number, Number> foxData = foxesSeries.getData().get(i);
            XYChart.Data<Number, Number> grassData = grassSeries.getData().get(i);
            int rabbitX = rabbitData.getXValue().intValue();
            int foxX = foxData.getXValue().intValue();
            int grassX = grassData.getXValue().intValue();
            rabbitData.setXValue(rabbitX - 1);
            foxData.setXValue(foxX - 1);
            grassData.setXValue(grassX - 1);
        }

        rabbitsSeries.getData().add(new XYChart.Data<>(dataSize, map.getRabbitCount()));
        foxesSeries.getData().add(new XYChart.Data<>(dataSize, map.getFoxCount()));
        grassSeries.getData().add(new XYChart.Data<>(dataSize, map.getGrassQuantity()));

    }

    /**
     * Parses the text of ground IDs separated by commas.
     */
    private List<Integer> parseIds(@NotNull String idsText) {
        List<Integer> ids = new ArrayList<>();
        String[] idsArray = idsText.split(",");
        for (String id : idsArray) {
            ids.add(Integer.parseInt(id.trim()));
        }
        return ids;
    }

    public static void main(String[] args) {
        launch();
    }

}