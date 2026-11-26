package com.racinggame.topdownracinggame;

/** Slow and heavy: obstacles slow it down much less. */
public class Truck extends Vehicle {
    public Truck() { super("Truck", 220, 110, 200, 80); }

    @Override
    public void onCollision() {
        loseSpeed(0.10);
    }

    @Override
    public String getSpecialEffect() { return "Obstacles slow it down much less"; }

    @Override
    public double[] getBodyShape() {
        return new double[] {-14, 28, -14, -28, 14, -28, 14, 28};
    }
}
