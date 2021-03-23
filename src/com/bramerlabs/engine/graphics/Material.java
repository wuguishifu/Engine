package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.file_util.FileUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL46;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {

    // the path to the textures
    private String pathToBaseMap, pathToSpecularMap, pathToNormalMap;

    // the format of the texture files
    private static final String FORMAT = "PNG";

    // the dimensions of the texture
    private float width, height;

    // the texture id
    private int textureID, specularID, normalID;

    // the texture interface
    private Texture texture, specular, normal;

    /**
     * default constructor
     * @param pathToBaseMap - the base color map
     * @param pathToSpecularMap - the specular map
     * @param pathToNormalMap - the normal map
     */
    public Material(String pathToBaseMap, String pathToSpecularMap, String pathToNormalMap) {
        this.pathToBaseMap = pathToBaseMap;
        this.pathToNormalMap = pathToNormalMap;
        this.pathToSpecularMap = pathToSpecularMap;
        this.create();
    }

    /**
     * creates the texture
     */
    public void create() {
        // attempt to read the files
        try {
            texture = TextureLoader.getTexture(FORMAT, FileUtils.class.getModule().getResourceAsStream(pathToBaseMap), GL46.GL_NEAREST);
            specular = TextureLoader.getTexture(FORMAT, FileUtils.class.getModule().getResourceAsStream(pathToSpecularMap), GL46.GL_NEAREST);
            normal = TextureLoader.getTexture(FORMAT, FileUtils.class.getModule().getResourceAsStream(pathToNormalMap), GL46.GL_NEAREST);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("Could not load texture.");
        }

        // get the size of the textures
        width = texture.getWidth();
        height = texture.getHeight();

        // get the pointers
        textureID = texture.getTextureID();
        specularID = specular.getTextureID();
        normalID = normal.getTextureID();
    }

    /**
     * release the textures
     */
    public void destroy() {
        GL20.glDeleteTextures(textureID);
        GL20.glDeleteTextures(specularID);
        GL20.glDeleteTextures(normalID);
    }

    /**
     * getter method
     * @return - the width of the texture
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * getter method
     * @return - the height of the texture
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * getter method
     * @return - the id of the base texture
     */
    public int getTextureID() {
        return textureID;
    }

    /**
     * getter method
     * @return - the id of the specular map
     */
    public int getSpecularID() {
        return specularID;
    }

    /**
     * getter method
     * @return - the id of the normal map
     */
    public int getNormalID() {
        return normalID;
    }
}
