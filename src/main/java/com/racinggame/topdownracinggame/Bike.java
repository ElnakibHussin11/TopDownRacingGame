package com.racinggame.topdownracinggame;

/** Agile, but hitting an obstacle costs a lot of speed. */
public class Bike extends Vehicle {
    public Bike() { super("Bike", 270, 180, 250, 170); }

    @Override
    public void onCollision() {
        loseSpeed(0.55);
    }

    @Override
    public String getSpecialEffect() { return "Hitting an obstacle costs more speed"; }

    @Override
    public double[] getBodyShape() {
        return new double[] {-4, 17, -5, -10, 0, -17, 5, -10, 4, 17};
    }
}
