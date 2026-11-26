package com.racinggame.topdownracinggame;

/** Creates a vehicle from the name chosen in the menu. */
public class VehicleFactory {
    public static Vehicle create(String name) {
        String n = name == null ? "" : name.toLowerCase();
        if (n.contains("truck")) return new Truck();
        if (n.contains("bike")) return new Bike();
        return new SportsCar();
    }
}
