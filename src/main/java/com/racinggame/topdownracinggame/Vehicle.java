package com.racinggame.topdownracinggame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/** Abstract base class for all vehicles. Angle 0 = facing up, degrees clockwise. */
public abstract class Vehicle {
    private final String name;
    private final double maxSpeed;      // pixels per second
    private final double acceleration;  // pixels per second^2
    private final double brakePower;    // pixels per second^2
    private final double handling;      // turn rate in degrees per second

    private double x, y, angle, speed;
    private double boostMultiplier = 1.0;
    private double boostTimeLeft = 0;

    private Color color = Color.RED;
    private Group node;

    protected Vehicle(String name, double maxSpeed, double acceleration,
                      double brakePower, double handling) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.brakePower = brakePower;
        this.handling = handling;
    }

    /** Each subclass draws its own body, pointing up, centered on (0,0). */
    protected abstract Polygon createBody(Color color);

    // ---------- movement ----------
    /** Call once per frame. gas/brake are key states, steer is -1 (left), 0, or +1 (right). */
    public void drive(boolean gas, boolean brake, int steer, double dt) {
        double top = getEffectiveMaxSpeed();

        if (gas) {
            speed += acceleration * dt;
        } else if (brake) {
            // braking slows you down, then reverses slowly
            speed -= brakePower * dt;
        } else {
            // friction: coast to a stop
            double friction = acceleration * 0.6 * dt;
            if (speed > friction) speed -= friction;
            else if (speed < -friction) speed += friction;
            else speed = 0;
        }
        if (speed > top) speed = Math.max(top, speed - brakePower * dt); // ease down after boost ends
        if (speed < -top * 0.3) speed = -top * 0.3;

        // steering only works while moving; slower = tighter at low speed
        if (steer != 0 && Math.abs(speed) > 5) {
            double factor = Math.min(1.0, Math.abs(speed) / (maxSpeed * 0.5));
            double dir = speed >= 0 ? 1 : -1;
            angle += steer * dir * handling * factor * dt;
        }

        double rad = Math.toRadians(angle);
        x += Math.sin(rad) * speed * dt;
        y -= Math.cos(rad) * speed * dt;

        if (boostTimeLeft > 0) {
            boostTimeLeft -= dt;
            if (boostTimeLeft <= 0) { boostTimeLeft = 0; boostMultiplier = 1.0; }
        }
        updateNode();
    }

    // ---------- hooks for Member 3 (track) ----------
    /** Temporary speed boost, e.g. applyBoost(1.5, 2.0). */
    public void applyBoost(double multiplier, double seconds) {
        boostMultiplier = multiplier;
        boostTimeLeft = seconds;
        speed = Math.min(getEffectiveMaxSpeed(), speed * multiplier);
    }

    /** Obstacle hit: lose a fraction of speed, e.g. hitObstacle(0.5). */
    public void hitObstacle(double speedLossFraction) {
        speed *= (1.0 - speedLossFraction);
    }

    // ---------- drawing ----------
    public Group getNode() {
        if (node == null) {
            node = new Group(createBody(color));
            updateNode();
        }
        return node;
    }

    private void updateNode() {
        if (node != null) {
            node.setTranslateX(x);
            node.setTranslateY(y);
            node.setRotate(angle);
        }
    }

    public void setColor(Color c) { color = c; node = null; }

    public void setPosition(double x, double y, double angle) {
        this.x = x; this.y = y; this.angle = angle; this.speed = 0;
        updateNode();
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
}
