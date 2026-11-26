package com.racinggame.topdownracinggame;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point. Owns the single Stage and switches between scenes.
 * Other members can call Main.showMenu() (Back to Menu)
 * and Main.startRace(settings) (Restart).
 */
public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("Top-Down Racing Game");
        stage.setResizable(false);
        showMenu();
        stage.show();
    }

    public static void showMenu() {
        Menu menu = new Menu(Main::startRace);
        primaryStage.setScene(menu.createScene());
        primaryStage.centerOnScreen();
    }

    public static void startRace(RaceSettings settings) {
        GameUI gameUI = new GameUI(settings);
        primaryStage.setScene(gameUI.createScene());
        primaryStage.centerOnScreen();
        gameUI.startCountdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
