package com.racinggame.topdownracinggame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class SportsCar extends Vehicle {
    public SportsCar() { super("Sports Car", 320, 220, 300, 140); }

    @Override
    protected Polygon createBody(Color color) {
        Polygon p = new Polygon(-10, 20, -10, -14, -5, -20, 5, -20, 10, -14, 10, 20);
        p.setFill(color);
        return p;
    }
}
