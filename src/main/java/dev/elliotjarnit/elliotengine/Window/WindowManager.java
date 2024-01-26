package dev.elliotjarnit.elliotengine.Window;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Graphics.RenderingEngine;
import dev.elliotjarnit.elliotengine.Overlay.EOButton;
import dev.elliotjarnit.elliotengine.Utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import org.lwjgl.glfw.*;

public class WindowManager {
    private JFrame window;
    private ElliotEngine engine;
    private RenderingEngine renderingEngine;
    private ArrayList<EventListener> listeners;

    public WindowManager(ElliotEngine engine) {
        this.engine = engine;
        this.renderingEngine = engine.renderer;
    }

    public void setup() {
        // Setup window
        window = new JFrame(this.engine.getOption(ElliotEngine.Options.NAME));

        // Setup window listeners
        window.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                for (EventListener listener : listeners) {
                    listener.onWindowResize();
                }
            }
        });

        String width = this.engine.getOption(ElliotEngine.Options.WINDOW_WIDTH);
        String height = this.engine.getOption(ElliotEngine.Options.WINDOW_HEIGHT);
        window.setSize(Integer.parseInt(width), Integer.parseInt(height));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Setup renderer
        Container pane = window.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(renderingEngine, BorderLayout.CENTER);
        this.listeners = new ArrayList<>();
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

    public void addEventListener(EventListener listener) {
        this.listeners.add(listener);
    }

    public Vector2 getWindowSize() {
        Insets insets = window.getInsets();
        return new Vector2(window.getWidth() - (insets.left + insets.right), window.getHeight() - (insets.top + insets.bottom));
    }

    public Vector2 getWindowCenter() {
        Insets insets = window.getInsets();
        return new Vector2((window.getWidth() - (insets.left + insets.right)) / 2, (window.getHeight() - (insets.top + insets.bottom)) / 2);
    }

    public Vector2 getWindowPosition() {
        return new Vector2(window.getLocationOnScreen().x, window.getLocationOnScreen().y);
    }

    public Vector2 getWindowCenterPosition() {
        Point location = window.getLocationOnScreen();
        return new Vector2(location.x + ((double) window.getWidth() / 2), location.y + ((double) window.getHeight() / 2));
    }

    public JFrame getWindow() {
        return window;
    }

    public static class EventListener {
        public void onWindowResize() {

        }
    }
}
