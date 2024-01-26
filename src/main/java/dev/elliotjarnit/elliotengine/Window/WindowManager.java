package dev.elliotjarnit.elliotengine.Window;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Graphics.RenderingEngine;
import dev.elliotjarnit.elliotengine.Overlay.EOButton;
import dev.elliotjarnit.elliotengine.Utils.Vector2;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;

public class WindowManager {
    private long window;
    private ElliotEngine engine;

    public WindowManager(ElliotEngine engine) {
        this.engine = engine;
    }

    public void setup() {
        initGLFW();
        // Configure window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Get options
        int width = Integer.parseInt(this.engine.getOption(ElliotEngine.Options.WINDOW_WIDTH));
        int height = Integer.parseInt(this.engine.getOption(ElliotEngine.Options.WINDOW_HEIGHT));

        // Make window
        window = glfwCreateWindow(width, height, this.engine.getOption(ElliotEngine.Options.NAME), NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create window");
    }

    public void initGLFW() {
        GLFWErrorCallback.createPrint(System.err).set();
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");
    }

    public void start() {
        glfwShowWindow(window);
    }

    public void stop() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();

        window = 0;
    }

    public Vector2 getWindowSize() {
//        Insets insets = window.getInsets();
//        return new Vector2(window.getWidth() - (insets.left + insets.right), window.getHeight() - (insets.top + insets.bottom));
        return new Vector2(0, 0);
    }

    public Vector2 getWindowCenter() {
//        Insets insets = window.getInsets();
//        return new Vector2((window.getWidth() - (insets.left + insets.right)) / 2, (window.getHeight() - (insets.top + insets.bottom)) / 2);
        return new Vector2(0, 0);
    }

    public Vector2 getWindowPosition() {
//        return new Vector2(window.getLocationOnScreen().x, window.getLocationOnScreen().y);
        return new Vector2(0, 0);
    }

    public Vector2 getWindowCenterPosition() {
//        Point location = window.getLocationOnScreen();
//        return new Vector2(location.x + ((double) window.getWidth() / 2), location.y + ((double) window.getHeight() / 2));
        return new Vector2(0, 0);
    }

    public long getWindow() {
        return window;
    }
}
