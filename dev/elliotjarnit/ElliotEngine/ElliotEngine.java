package dev.elliotjarnit.ElliotEngine;

import dev.elliotjarnit.ElliotEngine.Window.WindowManager;

public class ElliotEngine {
    private WindowManager windowManager;
    private boolean running = false;


    // Main
    public ElliotEngine() {
        setup();
    }

    private void setup() {
        // Setup window
        windowManager = new WindowManager();
        windowManager.setup();
    }

    private void loop() {
        input();
        update();
        render();
    }
    // Draw
    public void draw() {}
    // Update
    public void update() {}
    // Render
    public void render() {}
    // Input
    public void input() {}
    // Cleanup
    public void cleanup() {}
    // Run
    public void run() {
        running = true;
        while (running) {
            loop();
        }
        cleanup();
    }

    public void stop() {
        running = false;
    }
}
