package dev.elliotjarnit.ElliotEngine.Window;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Graphics.RenderingEngine;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

import javax.swing.*;
import java.awt.*;

public class WindowManager {
    private JFrame window;
    private ElliotEngine engine;
    private RenderingEngine renderingEngine;

    public WindowManager(ElliotEngine engine) {
        this.engine = engine;
        this.renderingEngine = engine.renderer;
    }

    public void setup() {
        // Setup window
        window = new JFrame(this.engine.getClass().getSimpleName());
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setup renderer
        Container pane = window.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(renderingEngine, BorderLayout.CENTER);
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

    public Vector2 getScreenSize() {
        return new Vector2(window.getWidth(), window.getHeight());
    }

    public JFrame getWindow() {
        return window;
    }
}
