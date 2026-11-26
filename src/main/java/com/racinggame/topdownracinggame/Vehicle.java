package com.racinggame.topdownracinggame;

/**
 * Abstract base class for all vehicles. Pure game logic: no JavaFX in here.
 * Heading: 0 degrees = facing up, increasing clockwise.
 */
public abstract class Vehicle {
    private final String name;
    private final double maxSpeed;      // pixels per second
    private final double acceleration;  // pixels per second^2
    private final double brakePower;    // pixels per second^2
    private final double handling;      // turn rate in degrees per second

    private double x, y, angle, speed;
    private double boostMultiplier = 1.0;
    private double boostTimeLeft = 0;
    private double controlLostTimeLeft = 0;

    protected Vehicle(String name, double maxSpeed, double acceleration,
                      double brakePower, double handling) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.brakePower = brakePower;
        this.handling = handling;
    }

    // ---------- abstract: each vehicle type behaves differently ----------
    /** Called when the vehicle hits an obstacle. Each type reacts in its own way. */
    public abstract void onCollision();

    /** Short text describing the vehicle's special behavior. */
    public abstract String getSpecialEffect();

    /** Body outline as x,y pairs (pointing up, centered on 0,0). Drawn by VehicleView. */
    public abstract double[] getBodyShape();

    // ---------- movement ----------
    /** Call once per frame. steer is -1 (left), 0, or +1 (right). */
    public void drive(boolean gas, boolean brake, int steer, double dt) {
        boolean sliding = controlLostTimeLeft > 0;
        if (sliding) {
            controlLostTimeLeft -= dt;
            steer = 0; // no steering while sliding
        }
        double top = getEffectiveMaxSpeed();

        if (sliding) {
            // keeps sliding in its current direction, only a tiny friction
            double slip = acceleration * 0.1 * dt;
            if (speed > slip) speed -= slip;
            else if (speed < -slip) speed += slip;
        } else if (gas) {
            speed += acceleration * dt;
        } else if (brake) {
            speed -= brakePower * dt;
        } else {
            double friction = acceleration * 0.6 * dt;
            if (speed > friction) speed -= friction;
            else if (speed < -friction) speed += friction;
            else speed = 0;
        }
        if (speed > top) speed = Math.max(top, speed - brakePower * dt);
        if (speed < -top * 0.3) speed = -top * 0.3;

        // steering: the handling value decides how fast the heading changes
        if (steer != 0 && Math.abs(speed) > 5) {
            double factor = Math.min(1.0, Math.abs(speed) / (maxSpeed * 0.5));
            double dir = speed >= 0 ? 1 : -1;
            angle += steer * dir * handling * factor * dt;
        }

        // move in the direction the vehicle is facing
        double rad = Math.toRadians(angle);
        x += Math.sin(rad) * speed * dt;
        y -= Math.cos(rad) * speed * dt;

        if (boostTimeLeft > 0) {
            boostTimeLeft -= dt;
            if (boostTimeLeft <= 0) { boostTimeLeft = 0; boostMultiplier = 1.0; }
        }
    }

    // ---------- effects used by TrackObjects (Member 3) ----------
    /** Temporary speed boost, e.g. applyBoost(1.5, 2.0). */
    public void applyBoost(double multiplier, double seconds) {
        boostMultiplier = multiplier;
        boostTimeLeft = seconds;
    }

    /** Oil spill: no steering for a while, vehicle keeps sliding. e.g. loseControl(1.0). */
    public void loseControl(double seconds) {
        controlLostTimeLeft = Math.max(controlLostTimeLeft, seconds);
    }

    /** Reduces speed by a fraction, e.g. loseSpeed(0.5) halves it. */
    protected void loseSpeed(double fraction) {
        speed *= (1.0 - fraction);
    }

    /** For walls and grass: slows the vehicle without changing its direction. */
    public void slowDown(double fraction) {
        loseSpeed(fraction);
    }

    public void setPosition(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 0;
        this.boostMultiplier = 1.0;
        this.boostTimeLeft = 0;
        this.controlLostTimeLeft = 0;
    }

    // ---------- getters ----------
    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getAngle() { return angle; }
    public double getSpeed() { return speed; }
    public double getMaxSpeed() { return maxSpeed; }
    public double getEffectiveMaxSpeed() { return maxSpeed * boostMultiplier; }
    public double getAcceleration() { return acceleration; }
    public double getHandling() { return handling; }
    public boolean isBoosted() { return boostTimeLeft > 0; }
    public boolean isSliding() { return controlLostTimeLeft > 0; }

    /** Width of the body, for collision checks. */
    public double getWidth() {
        double[] s = getBodyShape();
        double max = 0;
        for (int i = 0; i < s.length; i += 2) max = Math.max(max, Math.abs(s[i]));
        return max * 2;
    }

    /** Length of the body, for collision checks. */
    public double getLength() {
        double[] s = getBodyShape();
        double max = 0;
        for (int i = 1; i < s.length; i += 2) max = Math.max(max, Math.abs(s[i]));
        return max * 2;
    }
}
