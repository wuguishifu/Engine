package com.bramerlabs.solar;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import org.lwjgl.opengl.GL46;

public class Main implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Renderer renderer;
    private Vector3f lightPosition = new Vector3f(0, 100f, 0);
    private Body[] p;

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        Thread main = new Thread(this, "Solar");
        main.start();
    }

    @Override
    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
        }
    }

    public void init() {
        window.create();
        shader = new Shader(
                "shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl"
        ).create();
        renderer = new Renderer(window, lightPosition);
        camera = new Camera(new Vector3f(0), new Vector3f(0), input);
        camera.setFocus(new Vector3f(0));
        camera.setDistance(20.0f);
        p = new Body[3];
        p[0] = new Body(new Vector3f(7, 0, 5), new Vector3f(-0.05f, 0, 0), 100000.0f, 1f);
        p[1] = new Body(new Vector3f(0, 0, 0), new Vector3f(-0.025f, 0, 0.025f), 100000.0f, 1f);
        p[2] = new Body(new Vector3f(-7, 0, -5), new Vector3f(0.05f, 0, 0), 100000.0f, 1f);
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        Vector3f force;
        for (Body b1 : p) {
            for (Body b2 : p) {
                if (b2 != b1) {
                    force = Body.calculateForce(b2, b1);
                    b1.applyForce(force);
                }
            }
            b1.update();
        }
        camera.updateArcball();
    }

    public void render() {
        renderer.renderMesh(p[0].getSphere(), camera, shader, Renderer.COLOR);
        renderer.renderMesh(p[1].getSphere(), camera, shader, Renderer.COLOR);
        renderer.renderMesh(p[2].getSphere(), camera, shader, Renderer.COLOR);

        window.swapBuffers();
    }
}