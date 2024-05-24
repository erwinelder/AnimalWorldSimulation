module simulation.animal_simulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.logging;
    requires com.fasterxml.jackson.databind;


    opens simulation.animal_simulation to javafx.fxml;
    exports simulation.animal_simulation;
}