package dev.elliotjarnit.ElliotEngine;

import dev.elliotjarnit.ElliotEngine.Game.EScene;
import dev.elliotjarnit.ElliotEngine.Graphics.RenderingEngine;
import dev.elliotjarnit.ElliotEngine.Window.InputManager;
import dev.elliotjarnit.ElliotEngine.Window.WindowManager;

public abstract class ElliotEngine {
    private boolean isSetup = false;
    private boolean running = false;
    private EScene currentScene;
    private WindowManager windowManager;
    public InputManager inputManager;
    public RenderingEngine renderer;

    abstract void setup();
    abstract void loop();

    private void update() {
        renderer.renderScene(currentScene);
    }

    public void run() {
        if (!isSetup) {
            // Setup engine
            setup();
            // Setup renderer
            renderer = new RenderingEngine();
            // Setup window
            windowManager = new WindowManager(renderer);
            windowManager.setup();
            // Setup input
            inputManager = new InputManager();
            inputManager.setup();

            isSetup = true;
        }
        windowManager.start();
        running = true;
        while (running) {
            update();
            loop();
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
