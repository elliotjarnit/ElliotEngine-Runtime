package src.main.java;

import src.main.java.Game.EScene;
import src.main.java.Graphics.RenderingEngine;
import src.main.java.Window.InputManager;
import src.main.java.Window.WindowManager;

public abstract class ElliotEngine {
    private boolean isSetup = false;
    private boolean running = false;
    private EScene currentScene;
    private WindowManager windowManager;
    public InputManager inputManager;
    public RenderingEngine renderer;

    public abstract void setup();
    public abstract void loop();

    private void update() {
        if (currentScene != null) {
            renderer.renderScene(currentScene);
        }
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
