package dev.elliotjarnit.ElliotEngine.Window;

import dev.elliotjarnit.ElliotEngine.Graphics.RenderingEngine;

import javax.swing.*;

public class WindowManager {
    // Window
    public JFrame window;
    // Setup
    public void setup() {
        // Setup window
        window = new JFrame("ElliotEngine");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setup renderer
        window.setContentPane(new RenderingEngine());
        // Start window
        window.setVisible(true);
    }
}
