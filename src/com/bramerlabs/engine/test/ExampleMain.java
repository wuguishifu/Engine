package com.bramerlabs.engine.test;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cube;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

import java.awt.*;
import java.util.ArrayList;

public class ExampleMain implements Runnable {

    // the object to track inputs
    private final Input input = new Input();

    // the window to render to
    private final Window window = new Window(input);

    // the POV camera
    private Camera camera;

    // shaders to use
    private Shader defaultShader, lightShader;

    // renderers to use
    private Renderer renderer;

    // the position of the light source
    private final Vector3f lightPosition = new Vector3f(0.0f, 1.0f, 3.0f);

    // objects to render
    private ArrayList<RenderObject> renderObjects;

    // handles keyboard inputs
    private boolean[] keysDown;
    private boolean[] keysDownOld;

    // handles mouse inputs
    private boolean[] buttonsDown;
    private boolean[] buttonsDownOld;

    private boolean rotating = false;
    private boolean cubeRotating = false;

    /**
     * main runnable method
     * @param args - jvm args
     */
    public static void main(String[] args) {
        new ExampleMain().start();
    }

    /**
     * initializes the thread, starts the application
     */
    public void start() {
        Thread main = new Thread(this, "Example Application");
        main.start();
    }

    public void run() {
        // initialize
        this.init();

        // application loop
        while (!window.shouldClose()) {
            this.update();
            this.render();
        }

        // clean up after
        this.close();
    }

    private void init() {
        window.create();
        defaultShader = new Shader("shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl").create();
        lightShader = new Shader("shaders/light/vertex.glsl",
                "shaders/light/fragment.glsl").create();
        renderer = new Renderer(window, lightPosition);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        // the objects to render
        renderObjects = new ArrayList<>();

        // colored cube
        Color cubeColor = new Color(55, 75, 203);
        Vector3f color = Vector3f.scale(new Vector3f(cubeColor), 1/255f);
        Cube defaultCube = new Cube(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1), new Vector4f(new Vector3f(color), 0.5f));
        defaultCube.createMesh();
        renderObjects.add(defaultCube);

        // light cube
        Cube lightCube = new Cube(lightPosition, new Vector3f(0, 0, 0),
                new Vector3f(0.5f, 0.5f, 0.5f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
        lightCube.createMesh();
        renderObjects.add(lightCube);

        // the input detecting objects
        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownOld = new boolean[GLFW.GLFW_KEY_LAST];

        buttonsDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        buttonsDownOld = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    }

    private void update() {
        window.update();

        // clear the buffer bits
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        if (cubeRotating) {
            renderObjects.get(0).setRotation(Vector3f.add(renderObjects.get(0).getRotation(),
                    new Vector3f(0, 1.0f, 0)));
        }

        // update the camera
        camera.updateArcball(); // arcball camera
//        camera.update(); // first person camera

        if (rotating) {
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.5f);
        }


        // rising edge key inputs
        if (keysDown[GLFW.GLFW_KEY_R] && !keysDownOld[GLFW.GLFW_KEY_R]) {
            rotating = !rotating;
        }
        if (keysDown[GLFW.GLFW_KEY_C] && !keysDownOld[GLFW.GLFW_KEY_C]) {
            cubeRotating = !cubeRotating;
        }

        // rising edge button inputs
        if (buttonsDown[GLFW.GLFW_MOUSE_BUTTON_RIGHT] && !buttonsDownOld[GLFW.GLFW_MOUSE_BUTTON_RIGHT]) {
            System.out.println("Right Click");
        }

        // update buttons
        System.arraycopy(keysDown, 0, keysDownOld, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);

        // update buttons
        System.arraycopy(buttonsDown, 0, buttonsDownOld, 0, buttonsDown.length);
        System.arraycopy(input.getButtonsDown(), 0, buttonsDown, 0, input.getButtonsDown().length);
    }

    private void render() {
        // render the objects
        renderer.renderMesh(renderObjects.get(0), camera, defaultShader, Renderer.COLOR);
        renderer.renderMesh(renderObjects.get(1), camera, lightShader, Renderer.LIGHT);

        window.swapBuffers();
    }

    private void close() {
        // release the window
        window.destroy();

        // release the objects
        for (RenderObject object : renderObjects) {
            object.destroy();
        }

        // release the shaders
        defaultShader.destroy();
        lightShader.destroy();
    }

}
