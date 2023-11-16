package RaycastTest;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Objects.ECamera;
import dev.elliotjarnit.elliotengine.Objects.EScene;
import dev.elliotjarnit.elliotengine.Objects.ECube;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Overlay.EOButton;
import dev.elliotjarnit.elliotengine.Overlay.EOText;
import dev.elliotjarnit.elliotengine.Overlay.EOverlay;
import dev.elliotjarnit.elliotengine.Utils.Vector2;
import dev.elliotjarnit.elliotengine.Utils.Vector3;
import dev.elliotjarnit.elliotengine.Window.InputManager;

public class Engine extends ElliotEngine {
    private ECamera playerCamera;
    private double sensitivity = 0.5;

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run();
    }


    @Override
    public void optionSetup() {
        this.setOption(Options.NAME, "Raycast Test");
        this.setOption(Options.DESCRIPTION, "Testing raycasting and collision detection");
        this.setOption(Options.VERSION, "0.0.1");
        this.setOption(Options.WINDOW_WIDTH, "800");
        this.setOption(Options.WINDOW_HEIGHT, "800");
    }

    @Override
    public void setup() {
        this.inputManager.takeoverMouse();

        Vector2 windowSize = this.windowManager.getWindowSize();

        EOverlay mainOverlay = new EOverlay();
        mainOverlay.addComponent(new EOText(new Vector2(windowSize.x / 2, windowSize.y / 2), "Hello world!", 10, EColor.WHITE));
        EOButton testButton = new EOButton(new Vector2(windowSize.x / 2, windowSize.y / 2 + 20), "Click Me", 15, 5, EColor.WHITE, EColor.BLACK);
        testButton.addListener(new EOButton.ClickListener() {
            @Override
            public void onClick() {
                System.out.println("Button clicked!");
            }
        });
        mainOverlay.addComponent(testButton);
        this.setOverlay(mainOverlay);

        EScene mainScene = new EScene(false);
        playerCamera = new ECamera(new Vector3(0, 5, 0), 60.0);
        playerCamera.setRenderDistance(500.0);
        ECube testCube = new ECube(new Vector3(0, 0, 20), 5, 5, 5, EColor.CYAN);
        mainScene.addObject(testCube);
        mainScene.setCamera(playerCamera);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {
        if (this.inputManager.isKeyDown(InputManager.Key.W)) {
            playerCamera.moveForward(0.5);
        }

        if (this.inputManager.isKeyDown(InputManager.Key.S)) {
            playerCamera.moveForward(-0.5);
        }
        if (this.inputManager.isKeyDown(InputManager.Key.A)) {
            playerCamera.moveRight(-0.5);
        }

        if (this.inputManager.isKeyDown(InputManager.Key.D)) {
            playerCamera.moveRight(0.5);
        }

        if (this.inputManager.isMouseDown(InputManager.MouseButton.LEFT)) {
            System.out.println("Left mouse button pressed");
        }

        // Mouse movement
        Vector2 mouseDelta = this.inputManager.getMouseDelta();

        playerCamera.setRotationDegrees(playerCamera.getRotationDegrees().add(new Vector2(mouseDelta.y * this.sensitivity, mouseDelta.x * this.sensitivity)));
    }
}