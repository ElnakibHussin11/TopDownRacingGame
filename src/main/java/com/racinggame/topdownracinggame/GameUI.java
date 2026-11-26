package com.racinggame.topdownracinggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Scene 2: the race screen layout + the 3-2-1-GO countdown.
 *
 * Hooks for teammates:
 *  - getTrackPane()  -> Member 3 adds track shapes / Member 2 adds vehicle shapes
 *  - getHudPane()    -> Member 4 adds the HUD
 *  - isRaceActive()  -> Member 2's game loop must NOT move vehicles while this is false
 *  - setOnRaceStart  -> run code when GO appears (e.g. start RaceTimer)
 */
public class GameUI {

    private final RaceSettings settings;
    private final Pane trackPane = new Pane();
    private final HBox hudPane = new HBox(40);
    private final Label countdownLabel = new Label();
    private boolean raceActive = false;
    private Runnable onRaceStart = () -> { };
    private Vehicle vehicle1;
    private Vehicle vehicle2;

    public GameUI(RaceSettings settings) {
        this.settings = settings;
    }

    public Scene createScene() {
        trackPane.setPrefSize(1000, 600);
        trackPane.setStyle("-fx-background-color: #3a7d44;"); // placeholder grass

        hudPane.setPrefHeight(80);
        hudPane.setStyle("-fx-background-color: #222222;");

        countdownLabel.setStyle("-fx-font-size: 96px; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-effect: dropshadow(gaussian, black, 10, 0.8, 0, 0);");
        countdownLabel.setMouseTransparent(true);

        StackPane center = new StackPane(trackPane, countdownLabel);

        BorderPane root = new BorderPane();
        root.setTop(hudPane);
        root.setCenter(center);
        Scene scene = new Scene(root, 1000, 680);

        // ----- Member 2: vehicles and controls -----
        vehicle1 = VehicleFactory.create(settings.getPlayer1Vehicle());
        vehicle2 = VehicleFactory.create(settings.getPlayer2Vehicle());
        vehicle1.setPosition(150, 270, 90);   // facing right
        vehicle2.setPosition(150, 330, 90);

        VehicleView view1 = new VehicleView(vehicle1, Color.RED);
        VehicleView view2 = new VehicleView(vehicle2, Color.BLUE);
        trackPane.getChildren().addAll(view1.getNode(), view2.getNode());

        VehicleController controller = new VehicleController(view1, view2, this::isRaceActive);
        controller.attachTo(scene);
        controller.start();

        return scene;
    }

    /** Shows 3, 2, 1, GO! one second apart, then unlocks the race. */
    public void startCountdown() {
        raceActive = false;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> countdownLabel.setText("3")),
                new KeyFrame(Duration.seconds(1), e -> countdownLabel.setText("2")),
                new KeyFrame(Duration.seconds(2), e -> countdownLabel.setText("1")),
                new KeyFrame(Duration.seconds(3), e -> countdownLabel.setText("GO!")),
                new KeyFrame(Duration.seconds(4), e -> {
                    countdownLabel.setText("");
                    raceActive = true;
                    onRaceStart.run();
                }));
        timeline.play();
    }

    public boolean isRaceActive() { return raceActive; }
    public Pane getTrackPane() { return trackPane; }
    public HBox getHudPane() { return hudPane; }
    public RaceSettings getSettings() { return settings; }
    public Vehicle getVehicle1() { return vehicle1; }
    public Vehicle getVehicle2() { return vehicle2; }
    public void setOnRaceStart(Runnable onRaceStart) { this.onRaceStart = onRaceStart; }
}
