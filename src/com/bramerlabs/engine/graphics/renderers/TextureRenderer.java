package com.bramerlabs.engine.graphics.renderers;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class TextureRenderer {

    // the window to render to
    private final Window window;

    // the position of the light source;
    private final Vector3f lightPosition;

    // the light color
    private final Vector3f lightColor = new Vector3f(1.0f, 1.0f, 1.0f);

    /**
     * default constructor
     * @param window - the window to render to
     * @param lightPosition - the position of the light source
     */
    public TextureRenderer(Window window, Vector3f lightPosition) {
        this.window = window;
        this.lightPosition = lightPosition;
    }

    /**
     * renders an object
     * @param object - the object to render
     * @param camera - the camera perspective
     * @param shader - the shader to use to render
     */
    public void renderMesh(RenderObject object, Camera camera, Shader shader) {
        // bind the vertex array
        GL30.glBindVertexArray(object.getMesh().getVAO());

        // enable the vertex attribute arrays
        GL30.glEnableVertexAttribArray(0); // position buffer
        GL30.glEnableVertexAttribArray(1); // texture buffer
        GL30.glEnableVertexAttribArray(2); // normal buffer
        GL30.glEnableVertexAttribArray(3); // tangent buffer
        GL30.glEnableVertexAttribArray(4); // bitangent buffer

        // bind the index array draw order
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        // bind the textures
        GL13.glActiveTexture(GL13.GL_TEXTURE0); // the base color map
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 1); // the specular map
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, object.getMesh().getMaterial().getSpecularID());
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 2); // the normal map
        GL13.glBindTexture(GL13.GL_TEXTURE_2D, object.getMesh().getMaterial().getNormalID());

        // bind the shader
        shader.bind();

        // set the shader uniforms
        shader.setUniform("vModel", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("vView", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("vProjection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.1f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("lightColor", lightColor);

        // draw the elements based on the index array draw order
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        // unbind the shader
        shader.unbind();

        // unbind the index array object
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        // disable the vertex attributes
        GL30.glDisableVertexAttribArray(0); // position buffer
        GL30.glDisableVertexAttribArray(1); // texture buffer
        GL30.glDisableVertexAttribArray(2); // normal buffer
        GL30.glDisableVertexAttribArray(3); // tangent buffer
        GL30.glDisableVertexAttribArray(4); // bitangent buffer

        // unbind the vertex array
        GL30.glBindVertexArray(0);
    }

}
