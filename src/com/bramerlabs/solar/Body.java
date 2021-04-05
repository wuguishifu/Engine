package com.bramerlabs.solar;

import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;

public class Body {

    // the position and motion of the planet
    private Vector3f position, velocity;

    // the mass of the planet
    private float mass;

    // the radius of the planet
    private float radius;

    // the universal gravitational constant
    private static final float G = 0.000010f;

    // the sphere used for rendering this planet
    private Sphere sphere;

    /**
     * calculates the force b1 experiences from b2
     * @param b1 - the first body
     * @param b2 - the second body
     * @return - the force vector
     */
    public static Vector3f calculateForce(Body b1, Body b2) {
        Vector3f r = Vector3f.subtract(b2.position, b1.position);
        float radius = Vector3f.length(r);
        r = Vector3f.normalize(r);
        float gravCoeff = (-G * b1.mass * b2.mass) / (radius * radius);
        return Vector3f.scale(r, gravCoeff);
    }

    /**
     * default constructor
     * @param position - the position of the planet
     * @param velocity - the velocity of the planet
     * @param mass - the mass of this planet
     * @param radius - the radius of the planet
     */
    public Body(Vector3f position, Vector3f velocity, float mass, float radius) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.radius = radius;
        this.sphere = Sphere.getInstance(position, new Vector4f(0.5f, 0.5f, 0.5f, 1), radius);
        this.sphere.createMesh();
    }

    /**
     * applies a force to the planet
     * @param force - the force to be applied
     */
    public void applyForce(Vector3f force) {
        Vector3f acceleration = Vector3f.divide(force, this.mass);
        this.velocity = Vector3f.add(velocity, acceleration);
    }

    /**
     * getter method
     * @return - the sphere used to render this object
     */
    public Sphere getSphere() {
        return this.sphere;
    }

    public void update() {
        sphere.setPosition(Vector3f.add(position, velocity));
        this.position = sphere.getPosition();
    }

}
