package com.racinggame.topdownracinggame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Truck extends Vehicle {
    public Truck() { super("Truck", 220, 110, 200, 80); }

    @Override
    protected Polygon createBody(Color color) {
        Polygon p = new Polygon(-14, 28, -14, -28, 14, -28, 14, 28);
        p.setFill(color);
        return p;
    }
}
