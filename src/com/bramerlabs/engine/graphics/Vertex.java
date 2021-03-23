package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;

public class Vertex {

    // the position of this vertex
    private Vector3f position;

    // vectors normal, tangent, and bitangent to this vertex
    private Vector3f normal, tangent, bitangent;

    // if this vertex has a tangent or bitangent assigned to it
    private boolean hasTangent;
    private boolean hasBitangent;

    // the texture coordinate of this vertex
    private Vector2f textureCoord;

    /**
     * default constructor
     * @param position - the position of this vertex
     * @param textureCoord - the texture coordinate of this vertex
     * @param normal - a vector normal to this vertex
     * @param tangent - a vector tangent to this vertex
     * @param bitangent - a vector bitangent to this vertex
     */
    public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal, Vector3f tangent, Vector3f bitangent) {
        this.position = position;
        this.textureCoord = textureCoord;
        this.normal = normal;
        this.tangent = tangent;
        this.bitangent = bitangent;

        this.hasTangent = true;
        this.hasBitangent = true;
    }

    /**
     * constructor for position, texture coord, and normal
     * @param position - the position of this vertex
     * @param textureCoord - the texture coordinate of this vertex
     * @param normal - a vector normal to this vertex
     */
    public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal) {
        this.position = position;
        this.textureCoord = textureCoord;
        this.normal = normal;

        this.hasTangent = false;
        this.hasBitangent = false;
    }

    /**
     * sets the tangent vector
     * @param tangent - the new tangent vector
     */
    public void setTangent(Vector3f tangent) {
        this.tangent = tangent;
    }

    /**
     * sets the bitangent vector
     * @param bitangent - the new bitangent vector
     */
    public void setBitangent(Vector3f bitangent) {
        this.bitangent = bitangent;
    }

    /**
     * getter method
     * @return - if this vertex has a tangent vector
     */
    public boolean hasTangent() {
        return hasTangent;
    }

    /**
     * getter method
     * @return - if this vertex has a bitangent vector
     */
    public boolean hasBitangent() {
        return this.hasBitangent;
    }

    /**
     * getter method
     * @return - the position of this vertex
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * getter method
     * @return - the vector normal to this vertex
     */
    public Vector3f getNormal() {
        return normal;
    }

    /**
     * getter method
     * @return - the vector tangent to this vertex
     */
    public Vector3f getTangent() {
        return tangent;
    }

    /**
     * getter method
     * @return - the vector bitangent to this vertex
     */
    public Vector3f getBitangent() {
        return bitangent;
    }

    /**
     * getter method
     * @return - the texture coord of this vertex
     */
    public Vector2f getTextureCoord() {
        return textureCoord;
    }
}
