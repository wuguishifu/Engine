package com.bramerlabs.solar;

import com.bramerlabs.engine.math.vector.Vector3f;

public class Body {

    // the position and motion of the planet
    private Vector3f position;
    private Vector3f velocity;

    private float mass;
    private float radius;

    private static final float G = 10;

    public static Vector3f calculateForce(Body b1, Body b2) {
        float radius = Vector3f.length(Vector3f.subtract(b2.position, b1.position));
        Vector3f positionDelta = Vector3f.subtract(b1.position, b2.position);
        Vector3f positionLevel = Vector3f.subtract(
                new Vector3f(b1.position.x, 0, b1.position.y),
                new Vector3f(b2.position.x, 0, b2.position.y));
        float angle = Vector3f.angleBetween(positionDelta, positionLevel);
        float F = (G * b1.mass * b2.mass) / (radius * radius);
        Vector3f force = new Vector3f(0);
        return null;
    }

}
