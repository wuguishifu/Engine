package com.bramerlabs.solar;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.graphics.structures.LightStructure;
import com.bramerlabs.engine.graphics.structures.MaterialStructure;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import org.lwjgl.opengl.GL46;

public class Main implements Runnable {

    // the object to track inputs
    private final Input input = new Input();

    // the window to render to
    private final Window window = new Window(input);

    // the POV camera
    private Camera camera;

    private Shader structureShader;

    // renderers to use
    private Renderer renderer;

    // the position of the light source
    private Vector3f lightPosition = new Vector3f(0, 0, 0);

    // objects to render

    // structures
    private MaterialStructure material;
    private LightStructure light;

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        Thread main = new Thread(this, "Solar");

        // start the thread
        main.start();
    }

    public void run() {
        // initialize
        init();

        // application run loop
        while (!window.shouldClose()) {
            update();
            render();
        }

        // clean up
        close();
    }

    private void init() {
        window.create();

        structureShader = new Shader(
                "shaders/structured/vertex.glsl",
                "shaders/structured/fragment.glsl"
        );

        // initialize renderers
        renderer = new Renderer(window, lightPosition);

        // initialize camera
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        // initialize objects
        light = new LightStructure(lightPosition, new Vector3f(1.0f), 0.3f);
        material = new MaterialStructure(32, 1, 1);
    }

    private void update() {
        window.update();

        // clear the screen and mask bits
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        camera.update();
        window.setMouseState(true);
    }

    private void render() {

        // swap the frame buffers
        window.swapBuffers();
    }

    private void close() {
        // release things
        window.destroy();
        structureShader.destroy();
    }

}
