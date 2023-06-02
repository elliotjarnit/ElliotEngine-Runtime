package dev.elliotjarnit.ElliotEngine;

import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Graphics.RenderingEngine;
import dev.elliotjarnit.ElliotEngine.Window.InputManager;
import dev.elliotjarnit.ElliotEngine.Window.WindowManager;

import java.util.HashMap;

public abstract class ElliotEngine {
    private boolean isSetup = false;
    private boolean running = false;
    private EScene currentScene;
    private final HashMap<Options, String> options = new HashMap<>();
    private final HashMap<AdvancedOptions, String> advancedOptions = new HashMap<>();
    public WindowManager windowManager;
    public InputManager inputManager;
    public RenderingEngine renderer;

    public abstract void optionSetup();

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
            this.optionSetup();

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

    public Platform getPlatform() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return Platform.WINDOWS;
        if (os.contains("mac")) return Platform.MAC;
        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) return Platform.LINUX;
        return Platform.OTHER;
    }

    public void setOption(Options option, String value) {
        options.put(option, value);
    }

    public void setOption(AdvancedOptions option, String value) {
        advancedOptions.put(option, value);
    }

    public String getOption(Options option) {
        return options.get(option);
    }

    public String getOption(AdvancedOptions option) {
        return advancedOptions.get(option);
    }

    public String[] getOptions() {
        String[] options = new String[this.options.size()];
        int i = 0;
        for (Options option : this.options.keySet()) {
            options[i] = option.toString();
            i++;
        }
        return options;
    }

    public String[] getAdvancedOptions() {
        String[] options = new String[this.advancedOptions.size()];
        int i = 0;
        for (AdvancedOptions option : this.advancedOptions.keySet()) {
            options[i] = option.toString();
            i++;
        }
        return options;
    }

    public enum Platform {
        WINDOWS,
        MAC,
        LINUX,
        OTHER
    }

    public enum Options {
        NAME,
        VERSION,
        AUTHOR,
        DESCRIPTION,
        LICENSE,
        WINDOW_WIDTH,
        WINDOW_HEIGHT,
        WINDOW_FULLSCREEN,
    }

    public enum AdvancedOptions {
        MAX_CLIPPING_VERTEXES,
    }
}
