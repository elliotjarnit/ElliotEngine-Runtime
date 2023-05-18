package src.main.java;

import src.main.java.Game.EScene;
import src.main.java.Graphics.RenderingEngine;
import src.main.java.Window.InputManager;
import src.main.java.Window.WindowManager;

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

            renderer.renderScene(currentScene);
        }
    }

    public void run() {
        if (!isSetup) {
            // Setup engine
            setup();
            // Setup renderer
            renderer = new RenderingEngine(this);
            // Setup window
            windowManager = new WindowManager(this);
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
