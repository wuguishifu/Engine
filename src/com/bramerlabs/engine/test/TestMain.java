package com.bramerlabs.engine.test;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.TextureRenderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.default_objects.Box;
import org.lwjgl.opengl.GL46;

public class TestMain implements Runnable {

    // the object to track inputs
    private final Input input = new Input();

    // the window to render to
    private final Window window = new Window(input);

    // the POV camera
    private Camera camera;

    // shaders to use
    private Shader shader;

    // renderers to use
    private TextureRenderer renderer;

    // the position of the light source
    private Vector3f lightPosition = new Vector3f(1.0f, 2.0f, 3.0f);

    // test objects to render
    private Box blank, box, wall;

    /**
     * the main runnable method
     * @param args - jvm arguments
     */
    public static void main(String[] args) {
        // start the main thread
        new TestMain().start();
    }

    /**
     * starts the thread
     */
    public void start() {
        // initialize the new thread
        Thread main = new Thread(this, "Test Thread");

        // start the thread
        main.start();
    }

    public void run() {
        // initialize the program
        init();

        // application run loop
        while (!window.shouldClose()) {
            update();
            render();
        }

        // clean up
        close();
    }

    /**
     * initialize the program
     */
    private void init() {
        // initialize the window
        window.create();

        // initialize the shader
        shader = new Shader(
                "shaders.texture/vertex.glsl", // path to the vertex shader
                "shaders.texture/fragment.glsl" // path to the fragment shader
        ).create();

        // initialize the renderers
        renderer = new TextureRenderer(window, lightPosition);

        // initialize the camera
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        // initialize objects
        blank = new Box(
                new Vector3f(-2, 0, 0),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1),
                "textures/blank"
        );
        box = new Box(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1),
                "textures/box"
        );
        wall = new Box(
                new Vector3f(2, 0, 0),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1),
                "textures/wall"
        );
        blank.createMesh();
        box.createMesh();
        wall.createMesh();
    }

    /**
     * updates the program
     */
    private void update() {
        window.update();

        // clear the screen and mask bits
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        // update the camera
        camera.updateArcball();
    }

    /**
     * renders the program
     */
    private void render() {
        // render the objects
        renderer.renderMesh(blank, camera, shader);
        renderer.renderMesh(box, camera, shader);
        renderer.renderMesh(wall, camera, shader);

        // swap the frame buffers
        window.swapBuffers();
    }

    /**
     * closes the program
     */
    private void close() {
        // release the window
        window.destroy();

        // release the objects
        box.destroy();

        // release the shaders
        shader.destroy();
    }

}