package BasicCubeDraw;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Graphics.RenderingEngine;
import dev.elliotjarnit.elliotengine.Objects.*;
import dev.elliotjarnit.elliotengine.Utils.Matrix4;
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
        this.setOption(Options.NAME, "Basic Cube Draw Demo");
        this.setOption(Options.DESCRIPTION, "A  very simple demo of ElliotEngine");
        this.setOption(Options.VERSION, "0.0.1");
        this.setOption(Options.WINDOW_WIDTH, "800");
        this.setOption(Options.WINDOW_HEIGHT, "800");
        this.setOption(Options.LOADING_SCREEN, "false");
    }

    @Override
    public void setup() {
        this.inputManager.takeoverMouse();
        this.renderer.setProjectionMode(RenderingEngine.ProjectionMode.PERSPECTIVE);

        EScene mainScene = new EScene(false);
        playerCamera = new ECamera(new Vector3(0.1, 2, -10), 60.0);
        playerCamera.setRenderDistance(1000.0);
        SpinningPyramid myObject = new SpinningPyramid(new Vector3(0, 0, 50), 5, 5, EColor.RED);
        KnightPiece knight1 = new KnightPiece(new Vector3(0, 0, 20));
        ECube myCube = new ECube(new Vector3(20, 0, 0), 5, 5, 5, EColor.CYAN);
        System.out.println("Cube");
        for (EFace face : myCube.getFaces()) {
            System.out.println(face);
        }
        mainScene.addObject(myObject);
        mainScene.addObject(myCube);
        mainScene.addObject(knight1);
        mainScene.addObject(playerCamera);
        mainScene.setCamera(playerCamera);
        this.setScene(mainScene);

        Matrix4 test = new Matrix4(new double[] {
                1313, 131, 333, 2,
                222, 123, 3, 33,
                22, 52, 5, 555,
                33, 33, 555, 5
        });
    }

    @Override
    public void loop() {
        if (this.inputManager.isKeyPressed(InputManager.Key.W)) {
            playerCamera.moveForward(0.5);
        }

        if (this.inputManager.isKeyPressed(InputManager.Key.S)) {
            playerCamera.moveBackward(0.5);
        }
        if (this.inputManager.isKeyPressed(InputManager.Key.A)) {
            playerCamera.moveLeft(0.5);
        }

        if (this.inputManager.isKeyPressed(InputManager.Key.D)) {
            playerCamera.moveRight(0.5);
        }

        if (this.inputManager.isKeyPressed(InputManager.Key.SPACE)) {
            playerCamera.moveUp(0.5);
        }

        if (this.inputManager.isKeyPressed(InputManager.Key.SHIFT)) {
            playerCamera.moveDown(0.5);
        }

        if (this.inputManager.isKeyDown(InputManager.Key.R)) {
            if (this.renderer.getProjectionMode() == RenderingEngine.ProjectionMode.PERSPECTIVE) {
                this.renderer.setProjectionMode(RenderingEngine.ProjectionMode.ORTHOGRAPHIC);
            } else {
                this.renderer.setProjectionMode(RenderingEngine.ProjectionMode.PERSPECTIVE);
            }
        }

        if (this.inputManager.isKeyDown(InputManager.Key.T)) {
            if (this.renderer.getRenderMode() == RenderingEngine.RenderMode.WIREFRAME) {
                this.renderer.setRenderMode(RenderingEngine.RenderMode.SOLID);
            } else if (this.renderer.getRenderMode() == RenderingEngine.RenderMode.SOLID) {
                this.renderer.setRenderMode(RenderingEngine.RenderMode.WIREFRAME);
            }
        }

        if (this.inputManager.isKeyDown(InputManager.Key.ESCAPE)) {
            this.stop();
        }



        if (this.inputManager.isMouseDown(InputManager.MouseButton.LEFT)) {
            System.out.println("Left mouse button down");
            Vector2 mousePos = this.inputManager.getMousePos();

            System.out.println(this.renderer.getObjectAtPoint(mousePos));
        }

        // Mouse movement
        Vector2 mouseDelta = this.inputManager.getMouseDelta();

        playerCamera.setRotationDegrees(playerCamera.getRotationDegrees().add(new Vector2(mouseDelta.y * this.sensitivity, mouseDelta.x * this.sensitivity)));
    }
}