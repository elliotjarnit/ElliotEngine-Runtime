package src.main.java.Window;

import src.main.java.Graphics.RenderingEngine;

import javax.swing.*;

public class WindowManager {
    private JFrame window;
    private RenderingEngine renderingEngine;

    public WindowManager(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

    public void setup() {
        // Setup window
        window = new JFrame("ElliotEngine");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setup renderer
        window.setContentPane(renderingEngine);
    }

    public void start() {
        window.setVisible(true);
    }

    public void stop() {
        window.setVisible(false);
        window.dispose();
        window = null;
        renderingEngine = null;
    }
}
