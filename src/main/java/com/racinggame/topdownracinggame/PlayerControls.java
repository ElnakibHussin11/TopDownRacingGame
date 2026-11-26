package com.racinggame.topdownracinggame;

import javafx.scene.input.KeyCode;

public class PlayerControls {
    public final KeyCode up, down, left, right;

    public PlayerControls(KeyCode up, KeyCode down, KeyCode left, KeyCode right) {
        this.up = up; this.down = down; this.left = left; this.right = right;
    }

    public static PlayerControls player1() {
        return new PlayerControls(KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D);
    }

    public static PlayerControls player2() {
        return new PlayerControls(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);
    }
}
