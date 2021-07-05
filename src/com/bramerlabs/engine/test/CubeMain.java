package com.bramerlabs.engine.test;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Cube;
import org.lwjgl.opengl.GL46;

public class CubeMain implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Renderer renderer;
    private Vector3f lightPosition = new Vector3f(0, 2.0f, 3.0f);

    private Cube cube;

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
        renderer = new Renderer(window, lightPosition);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));

        cube = new Cube(new Vector3f(0, 0, 0), new Vector4f(0.5f, 0.5f, 0.5f, 1));
        cube.createMesh();
    }

    private void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();
    }

    private void render() {
        renderer.renderMesh(cube, camera, shader, Renderer.COLOR);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        cube.destroy();
        shader.destroy();
    }

}
