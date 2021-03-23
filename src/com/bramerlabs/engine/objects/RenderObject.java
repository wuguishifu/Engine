package com.bramerlabs.engine.objects;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.math.Vector3f;

public class RenderObject {

    // the locational data of this object in 3d space
    private Vector3f position, rotation, scale;

    // the mesh that this object is made of
    private Mesh mesh;

    /**
     * default constructor
     * @param mesh - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale - the scale of this object
     */
    public RenderObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    /**
     * initializes the mesh in this object
     */
    public void createMesh() {
        this.mesh.create();
    }

    /**
     * releases the render object
     */
    public void destroy() {
        this.mesh.destroy();
    }

    /**
     * getter method
     * @return - the position of this object
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * getter method
     * @return - the rotation of this object
     */
    public Vector3f getRotation() {
        return this.rotation;
    }

    /**
     * getter method
     * @return - the scale of this object
     */
    public Vector3f getScale() {
        return this.scale;
    }

    /**
     * getter method
     * @return - the mesh of this object
     */
    public Mesh getMesh() {
        return this.mesh;
    }

}
