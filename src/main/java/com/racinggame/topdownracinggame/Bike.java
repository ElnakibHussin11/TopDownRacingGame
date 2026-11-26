package com.racinggame.topdownracinggame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Bike extends Vehicle {
    public Bike() { super("Bike", 270, 180, 250, 170); }

    @Override
    protected Polygon createBody(Color color) {
        Polygon p = new Polygon(-4, 17, -5, -10, 0, -17, 5, -10, 4, 17);
        p.setFill(color);
        return p;
    }
}
