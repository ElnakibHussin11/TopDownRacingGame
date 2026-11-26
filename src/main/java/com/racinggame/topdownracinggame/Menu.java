package com.racinggame.topdownracinggame;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/** Scene 1: vehicle selection screen. */
public class Menu {

    // Display-only stats for the screen. Replace with real values from the
    // Vehicle classes (Member 2) once they are merged into main.
    private static final Map<String, String> VEHICLE_STATS = new LinkedHashMap<>();
    static {
        VEHICLE_STATS.put("Sports Car", "Top speed: High\nAcceleration: High\nHandling: Medium");
        VEHICLE_STATS.put("Truck", "Top speed: Low\nAcceleration: Low\nHandling: Low");
        VEHICLE_STATS.put("Bike", "Top speed: Medium\nAcceleration: Medium\nHandling: High");
    }

    private final Consumer<RaceSettings> onStartRace;

    public Menu(Consumer<RaceSettings> onStartRace) {
        this.onStartRace = onStartRace;
    }

    public Scene createScene() {
        Label title = new Label("Top-Down Racing Game");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        ComboBox<String> p1Choice = new ComboBox<>();
        ComboBox<String> p2Choice = new ComboBox<>();
        VBox p1Panel = createPlayerPanel("Player 1 (W A S D)", p1Choice);
        VBox p2Panel = createPlayerPanel("Player 2 (Arrow keys)", p2Choice);
        HBox players = new HBox(60, p1Panel, p2Panel);
        players.setAlignment(Pos.CENTER);

        ComboBox<Integer> lapsChoice = new ComboBox<>();
        lapsChoice.getItems().addAll(1, 3, 5);
        lapsChoice.setValue(3);
        HBox lapsRow = new HBox(10, new Label("Number of laps:"), lapsChoice);
        lapsRow.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start Race");
        startButton.setStyle("-fx-font-size: 18px;");
        startButton.setOnAction(e -> onStartRace.accept(
                new RaceSettings(p1Choice.getValue(), p2Choice.getValue(), lapsChoice.getValue())));

        VBox root = new VBox(25, title, players, lapsRow, startButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        return new Scene(root, 800, 500);
    }

    /** One column: heading, dropdown, and a label showing the selected vehicle's stats. */
    private VBox createPlayerPanel(String heading, ComboBox<String> choice) {
        Label headingLabel = new Label(heading);
        headingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        choice.getItems().addAll(VEHICLE_STATS.keySet());
        Label stats = new Label();
        stats.setMinHeight(60);

        choice.valueProperty().addListener((obs, oldValue, newValue) ->
                stats.setText(VEHICLE_STATS.get(newValue)));
        choice.setValue(choice.getItems().get(0)); // triggers the listener -> shows stats

        VBox panel = new VBox(10, headingLabel, choice, stats);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPrefWidth(250);
        return panel;
    }
}
