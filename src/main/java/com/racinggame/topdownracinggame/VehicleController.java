package com.racinggame.topdownracinggame;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/** Reads the keyboard and moves both vehicles every frame, but only while the race is active. */
public class VehicleController {
    private final Vehicle p1, p2;
    private final PlayerControls c1 = PlayerControls.player1();
    private final PlayerControls c2 = PlayerControls.player2();
    private final BooleanSupplier raceActive;
    private final Set<KeyCode> pressed = new HashSet<>();
    private AnimationTimer timer;

    public VehicleController(Vehicle p1, Vehicle p2, BooleanSupplier raceActive) {
        this.p1 = p1;
        this.p2 = p2;
        this.raceActive = raceActive;
    }

    public void attachTo(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> { pressed.add(e.getCode()); e.consume(); });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> { pressed.remove(e.getCode()); e.consume(); });
    }

    public void start() {
        timer = new AnimationTimer() {
            private long last = 0;
            @Override
            public void handle(long now) {
                if (last == 0) { last = now; return; }
                double dt = Math.min((now - last) / 1e9, 0.05);
                last = now;
                if (raceActive.getAsBoolean()) {
                    update(p1, c1, dt);
                    update(p2, c2, dt);
                }
            }
        };
        timer.start();
    }

    public void stop() {
        if (timer != null) timer.stop();
        pressed.clear();
    }

    private void update(Vehicle v, PlayerControls c, double dt) {
        int steer = (pressed.contains(c.right) ? 1 : 0) - (pressed.contains(c.left) ? 1 : 0);
        v.drive(pressed.contains(c.up), pressed.contains(c.down), steer, dt);
    }
}
