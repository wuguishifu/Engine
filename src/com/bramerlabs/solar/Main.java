package com.bramerlabs.solar;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.shapes.shapes_3d.Sphere;
import org.lwjgl.opengl.GL46;

public class Main implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Renderer renderer;
    private Vector3f lightPosition = new Vector3f(0, 100f, 0);
    private Body sun, planet;

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
        sun = new Body(new Vector3f(-2, 0, 0), new Vector3f(0, 0, 0), 10000, 0.5f);
        planet = new Body(new Vector3f(2, 0, 0), new Vector3f(0, 0, 0.1f), 1000, 0.1f);
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        Vector3f force = Body.calculateForce(sun, planet);
        planet.applyForce(force);
        force = Body.calculateForce(planet, sun);
//        sun.applyForce(force);
        planet.update();
        sun.update();
        camera.updateArcball();
    }

    public void render() {
        renderer.renderMesh(sun.getSphere(), camera, shader, Renderer.COLOR);
        renderer.renderMesh(planet.getSphere(), camera, shader, Renderer.COLOR);
        window.swapBuffers();
    }
}