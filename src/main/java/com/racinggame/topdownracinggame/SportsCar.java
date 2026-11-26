package com.racinggame.topdownracinggame;

/** Fast, but loses control for a moment after hitting an obstacle. */
public class SportsCar extends Vehicle {
    public SportsCar() { super("Sports Car", 320, 220, 300, 140); }

    @Override
    public void onCollision() {
        loseSpeed(0.35);
        loseControl(0.6);
    }

    @Override
    public String getSpecialEffect() { return "Loses control after hitting an obstacle"; }

    @Override
    public double[] getBodyShape() {
        return new double[] {-10, 20, -10, -14, -5, -20, 5, -20, 10, -14, 10, 20};
    }
}
