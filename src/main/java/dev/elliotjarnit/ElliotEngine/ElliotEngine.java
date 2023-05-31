package dev.elliotjarnit.ElliotEngine;

import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Graphics.RenderingEngine;
import dev.elliotjarnit.ElliotEngine.Window.InputManager;
import dev.elliotjarnit.ElliotEngine.Window.WindowManager;

public abstract class ElliotEngine {
    private boolean isSetup = false;
    private boolean running = false;
    private EScene currentScene;
    public WindowManager windowManager;
    public InputManager inputManager;
    public RenderingEngine renderer;

    public abstract void setup();
    public abstract void loop();

    private void update() {
        if (currentScene != null) {
            for (int i = 0; i < currentScene.getObjects().size(); i++) {
                currentScene.getObjects().get(i).update();
            }
        }
    }

    public void run() {
        if (!isSetup) {
            // Setup renderer
            renderer = new RenderingEngine(this);
            // Setup window
            windowManager = new WindowManager(this);
            windowManager.setup();
            // Setup input
            inputManager = new InputManager(this);
            inputManager.setup();

            // Setup engine
            setup();

            isSetup = true;
        }
        windowManager.start();
        running = true;


        // Updates 24 times per second
        double currentTime = System.nanoTime();
        double nextUpdate = System.nanoTime();
        double skipTicks = 1000000000.0 / 24.0;

        while (running) {
            currentTime = System.nanoTime();

            while (currentTime > nextUpdate) {
                update();
                loop();
                nextUpdate += skipTicks;
            }

            if (currentScene != null) renderer.renderScene(currentScene);
        }
    }

    public void stop() {
        running = false;
        windowManager.stop();
        windowManager = null;
    }

    public void setScene(EScene scene) {
        currentScene = scene;
    }
}
