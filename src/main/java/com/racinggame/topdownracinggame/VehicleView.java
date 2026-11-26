package com.racinggame.topdownracinggame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/** JavaFX drawing of a Vehicle. Keeps all JavaFX code out of the Vehicle classes. */
public class VehicleView {
    private final Vehicle vehicle;
    private final Group node;

    public VehicleView(Vehicle vehicle, Color color) {
        this.vehicle = vehicle;
        Polygon body = new Polygon(vehicle.getBodyShape());
        body.setFill(color);
        this.node = new Group(body);
        update();
    }

    /** Copies the vehicle's position and heading onto the shape. */
    public void update() {
        node.setTranslateX(vehicle.getX());
        node.setTranslateY(vehicle.getY());
        node.setRotate(vehicle.getAngle());
    }

    public Group getNode() { return node; }
    public Vehicle getVehicle() { return vehicle; }
}
