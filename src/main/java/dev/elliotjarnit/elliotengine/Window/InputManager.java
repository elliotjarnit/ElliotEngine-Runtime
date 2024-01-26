package dev.elliotjarnit.elliotengine.Window;
import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Utils.Vector2;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.nio.DoubleBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {
    private ElliotEngine engine;
    private boolean mouseTaken = false;
    private final Map<Key, Boolean> keyDown = new HashMap<>();
    private final Map<Key, Boolean> keyUp = new HashMap<>();
    private final Map<Key, Boolean> keyPressed = new HashMap<>();

    public InputManager(ElliotEngine engine) {
        this.engine = engine;
    }

    public void setup() {
        for (Key key : Key.values()) {
            keyDown.put(key, false);
            keyUp.put(key, false);
            keyPressed.put(key, false);
        }

        GLFWKeyCallback keyCallback;
        glfwSetKeyCallback(engine.windowManager.getWindow(), keyCallback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS) {
                keyDown.put(keyMap.get(key), true);
                keyPressed.put(keyMap.get(key), true);
            } else if (action == GLFW_RELEASE) {
                keyUp.put(keyMap.get(key), true);
                keyPressed.put(keyMap.get(key), false);
            }
        }));
    }

    public boolean isKeyPressed(Key key) {
        synchronized (this) {
            return keyPressed.get(key);
        }
    }

    public boolean isKeyDown(Key key) {
        synchronized (this) {
            boolean down = keyDown.get(key);
            for (Key k : Key.values()) {
                keyDown.put(k, false);
            }
            return down;
        }
    }

    public boolean isKeyUp(Key key) {
        synchronized (this) {
            boolean up = keyUp.get(key);
            for (Key k : Key.values()) {
                keyUp.put(k, false);
            }
            return up;
        }
    }

    public boolean isMouseDown(MouseButton button) {
        int buttonCode;
        if (button == MouseButton.LEFT) buttonCode = MouseEvent.BUTTON1;
        else if (button == MouseButton.RIGHT) buttonCode = MouseEvent.BUTTON3;
        else if (button == MouseButton.MIDDLE) buttonCode = MouseEvent.BUTTON2;
        else return false;

        return glfwGetMouseButton(engine.windowManager.getWindow(), buttonCode) == 1 ? true : false;
    }

    public Vector2 getMousePos() {
        DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(engine.windowManager.getWindow(), b1, b2);

        return new Vector2((float) b1.get(0), (float) b2.get(0));
    }

    public Vector2 getMouseDelta() {
        if (!mouseTaken) return new Vector2(0, 0);
        DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(engine.windowManager.getWindow(), b1, b2);

        return new Vector2((float) b1.get(0), (float) b2.get(0));
    }

    public void takeoverMouse() {
        glfwSetInputMode(engine.windowManager.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        mouseTaken = true;
    }

    public enum MouseButton {
        LEFT, RIGHT, MIDDLE;
    }

    public enum Key {
        A, B, C, D, E, F, G, H, I, J, K, L, M,
        N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
        NUM_0, NUM_1, NUM_2, NUM_3, NUM_4,
        NUM_5, NUM_6, NUM_7, NUM_8, NUM_9,
        ESCAPE, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12,
        GRAVE, MINUS, EQUALS, BACKSPACE, TAB, OPEN_BRACKET, CLOSE_BRACKET,
        BACKSLASH, CAPS_LOCK, SEMICOLON, QUOTE, ENTER, COMMA, PERIOD,
        SLASH, SPACE, INSERT, DELETE, HOME, END, PAGE_UP, PAGE_DOWN,
        PRINT_SCREEN, SCROLL_LOCK, PAUSE, UP, DOWN, LEFT, RIGHT,
        NUM_LOCK, NUMPAD_0, NUMPAD_1, NUMPAD_2, NUMPAD_3, NUMPAD_4, NUMPAD_5,
        NUMPAD_6, NUMPAD_7, NUMPAD_8, NUMPAD_9, NUMPAD_ADD, NUMPAD_SUBTRACT,
        NUMPAD_MULTIPLY, NUMPAD_DIVIDE, NUMPAD_DECIMAL, NUMPAD_ENTER,
        CONTROL_L, CONTROL_R, ALT_L, ALT_R, SHIFT_L, SHIFT_R, SUPER_L, SUPER_R,
        MENU;
    }

    // Map GLFW key codes to Key enum
    private final Map<Integer, Key> keyMap = new HashMap<Integer, Key>() {{
        put(GLFW_KEY_A, Key.A);
        put(GLFW_KEY_B, Key.B);
        put(GLFW_KEY_C, Key.C);
        put(GLFW_KEY_D, Key.D);
        put(GLFW_KEY_E, Key.E);
        put(GLFW_KEY_F, Key.F);
        put(GLFW_KEY_G, Key.G);
        put(GLFW_KEY_H, Key.H);
        put(GLFW_KEY_I, Key.I);
        put(GLFW_KEY_J, Key.J);
        put(GLFW_KEY_K, Key.K);
        put(GLFW_KEY_L, Key.L);
        put(GLFW_KEY_M, Key.M);
        put(GLFW_KEY_N, Key.N);
        put(GLFW_KEY_O, Key.O);
        put(GLFW_KEY_P, Key.P);
        put(GLFW_KEY_Q, Key.Q);
        put(GLFW_KEY_R, Key.R);
        put(GLFW_KEY_S, Key.S);
        put(GLFW_KEY_T, Key.T);
        put(GLFW_KEY_U, Key.U);
        put(GLFW_KEY_V, Key.V);
        put(GLFW_KEY_W, Key.W);
        put(GLFW_KEY_X, Key.X);
        put(GLFW_KEY_Y, Key.Y);
        put(GLFW_KEY_Z, Key.Z);
        put(GLFW_KEY_0, Key.NUM_0);
        put(GLFW_KEY_1, Key.NUM_1);
        put(GLFW_KEY_2, Key.NUM_2);
        put(GLFW_KEY_3, Key.NUM_3);
        put(GLFW_KEY_4, Key.NUM_4);
        put(GLFW_KEY_5, Key.NUM_5);
        put(GLFW_KEY_6, Key.NUM_6);
        put(GLFW_KEY_7, Key.NUM_7);
        put(GLFW_KEY_8, Key.NUM_8);
        put(GLFW_KEY_9, Key.NUM_9);
        put(GLFW_KEY_ESCAPE, Key.ESCAPE);
        put(GLFW_KEY_F1, Key.F1);
        put(GLFW_KEY_F2, Key.F2);
        put(GLFW_KEY_F3, Key.F3);
        put(GLFW_KEY_F4, Key.F4);
        put(GLFW_KEY_F5, Key.F5);
        put(GLFW_KEY_F6, Key.F6);
        put(GLFW_KEY_F7, Key.F7);
        put(GLFW_KEY_F8, Key.F8);
        put(GLFW_KEY_F9, Key.F9);
        put(GLFW_KEY_F10, Key.F10);
        put(GLFW_KEY_F11, Key.F11);
        put(GLFW_KEY_F12, Key.F12);
        put(GLFW_KEY_GRAVE_ACCENT, Key.GRAVE);
        put(GLFW_KEY_MINUS, Key.MINUS);
        put(GLFW_KEY_EQUAL, Key.EQUALS);
        put(GLFW_KEY_BACKSPACE, Key.BACKSPACE);
        put(GLFW_KEY_TAB, Key.TAB);
        put(GLFW_KEY_LEFT_BRACKET, Key.OPEN_BRACKET);
        put(GLFW_KEY_RIGHT_BRACKET, Key.CLOSE_BRACKET);
        put(GLFW_KEY_BACKSLASH, Key.BACKSLASH);
        put(GLFW_KEY_CAPS_LOCK, Key.CAPS_LOCK);
        put(GLFW_KEY_SEMICOLON, Key.SEMICOLON);
        put(GLFW_KEY_APOSTROPHE, Key.QUOTE);
        put(GLFW_KEY_ENTER, Key.ENTER);
        put(GLFW_KEY_COMMA, Key.COMMA);
        put(GLFW_KEY_PERIOD, Key.PERIOD);
        put(GLFW_KEY_SLASH, Key.SLASH);
        put(GLFW_KEY_SPACE, Key.SPACE);
        put(GLFW_KEY_INSERT, Key.INSERT);
        put(GLFW_KEY_DELETE, Key.DELETE);
        put(GLFW_KEY_HOME, Key.HOME);
        put(GLFW_KEY_END, Key.END);
        put(GLFW_KEY_PAGE_UP, Key.PAGE_UP);
        put(GLFW_KEY_PAGE_DOWN, Key.PAGE_DOWN);
        put(GLFW_KEY_PRINT_SCREEN, Key.PRINT_SCREEN);
        put(GLFW_KEY_SCROLL_LOCK, Key.SCROLL_LOCK);
        put(GLFW_KEY_PAUSE, Key.PAUSE);
        put(GLFW_KEY_UP, Key.UP);
        put(GLFW_KEY_DOWN, Key.DOWN);
        put(GLFW_KEY_LEFT, Key.LEFT);
        put(GLFW_KEY_RIGHT, Key.RIGHT);
        put(GLFW_KEY_NUM_LOCK, Key.NUM_LOCK);
        put(GLFW_KEY_KP_0, Key.NUMPAD_0);
        put(GLFW_KEY_KP_1, Key.NUMPAD_1);
        put(GLFW_KEY_KP_2, Key.NUMPAD_2);
        put(GLFW_KEY_KP_3, Key.NUMPAD_3);
        put(GLFW_KEY_KP_4, Key.NUMPAD_4);
        put(GLFW_KEY_KP_5, Key.NUMPAD_5);
        put(GLFW_KEY_KP_6, Key.NUMPAD_6);
        put(GLFW_KEY_KP_7, Key.NUMPAD_7);
        put(GLFW_KEY_KP_8, Key.NUMPAD_8);
        put(GLFW_KEY_KP_9, Key.NUMPAD_9);
        put(GLFW_KEY_KP_DECIMAL, Key.NUMPAD_DECIMAL);
        put(GLFW_KEY_KP_DIVIDE, Key.NUMPAD_DIVIDE);
        put(GLFW_KEY_KP_MULTIPLY, Key.NUMPAD_MULTIPLY);
        put(GLFW_KEY_KP_SUBTRACT, Key.NUMPAD_SUBTRACT);
        put(GLFW_KEY_KP_ADD, Key.NUMPAD_ADD);
        put(GLFW_KEY_KP_ENTER, Key.NUMPAD_ENTER);
        put(GLFW_KEY_LEFT_CONTROL, Key.CONTROL_L);
        put(GLFW_KEY_LEFT_ALT, Key.ALT_L);
        put(GLFW_KEY_LEFT_SHIFT, Key.SHIFT_L);
        put(GLFW_KEY_LEFT_SUPER, Key.SUPER_L);
        put(GLFW_KEY_RIGHT_CONTROL, Key.CONTROL_R);
        put(GLFW_KEY_RIGHT_ALT, Key.ALT_R);
        put(GLFW_KEY_RIGHT_SHIFT, Key.SHIFT_R);
        put(GLFW_KEY_RIGHT_SUPER, Key.SUPER_R);
        put(GLFW_KEY_MENU, Key.MENU);
    }};
}
