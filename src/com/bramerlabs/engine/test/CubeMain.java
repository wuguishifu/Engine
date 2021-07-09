package com.bramerlabs.engine.test;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cube;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

import java.util.ArrayList;

public class CubeMain implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Shader instanceShader;
    private Renderer renderer;
    private Vector3f lightPosition = new Vector3f(5, 20, 10);

    private ArrayList<Vector3f> renderPositions;
    private ArrayList<Vector4f> renderColors;
    Vector3f rotation;
    Vector3f scale;

    // handles keyboard inputs
    private boolean[] keysDown;
    private boolean[] keysDownOld;

    // handles mouse inputs
    private boolean[] buttonsDown;
    private boolean[] buttonsDownOld;

    private Cube cube;
    private Sphere sphere;

    public static void main(String[] args) {
        new CubeMain().start();
    }

    public void start() {
        Thread main = new Thread(this, "Test Thread");
        main.start();
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
        }
        close();
    }

    private void init() {
        window.create();

        shader = new Shader("shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl").create();
        instanceShader = new Shader("shaders/uniform_color/vertex.glsl",
                "shaders/uniform_color/fragment.glsl").create();

        renderer = new Renderer(window, lightPosition);

        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        cube = new Cube(new Vector3f(0, 0, 0), new Vector4f(0.5f, 0.5f, 0.5f, 1));
        cube.createMesh();

        sphere = new Sphere(new Vector3f(0, 0, 0),
                new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 0.5f);
        sphere.createMesh();

        renderPositions = new ArrayList<>();
        renderColors = new ArrayList<>();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    renderPositions.add(new Vector3f(i * 2, j * 2, k * 2));
                    float r = (float) ((i + 2) / 5.);
                    float g = (float) ((j + 2) / 5.);
                    float b = (float) ((k + 2) / 5.);
                    renderColors.add(new Vector4f(r, g, b, 1.0f));
                }
            }
        }

        // the input detecting objects
        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownOld = new boolean[GLFW.GLFW_KEY_LAST];

        buttonsDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        buttonsDownOld = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

        rotation = new Vector3f(0.0f, 0.0f, 0.0f);
        scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    private void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        if (!keysDown[GLFW.GLFW_KEY_SPACE]) {
            camera.setHorizontalAngle(camera.getHorizontalAngle() + 0.1f);
        }

        camera.updateArcball();
        handleInputs();
    }

    private boolean keyPressed(int key) {
        return keysDown[key] && !keysDownOld[key];
    }

    private void handleInputs() {

        // update buttons
        System.arraycopy(keysDown, 0, keysDownOld, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);

        // update mouse buttons
        System.arraycopy(buttonsDown, 0, buttonsDownOld, 0, buttonsDown.length);
        System.arraycopy(input.getButtonsDown(), 0, buttonsDown, 0, input.getButtonsDown().length);
    }

    private void render() {
        for (int i = 0; i < renderPositions.size(); i++) {
            renderer.renderInstancedMesh(cube, renderPositions.get(i), rotation , scale,
                    renderColors.get(i), camera, instanceShader);
        }
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        cube.destroy();
        shader.destroy();
    }
}