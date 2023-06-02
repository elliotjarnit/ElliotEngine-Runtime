package BasicCubeDraw;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Objects.ECamera;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Objects.ECube;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Utils.Matrix4;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;
import dev.elliotjarnit.ElliotEngine.Window.InputManager;

public class Engine extends ElliotEngine {
    private ECamera playerCamera;
    private double sensitivity = 0.3;

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run();
    }

    @Override
    public void setup() {
        this.inputManager.takeoverMouse();

        EScene mainScene = new EScene();
        playerCamera = new ECamera(new Vector3(0.1, 0.1, -10), 60.0);
        playerCamera.setRenderDistance(1000.0);
        SpinningPyramid myObject = new SpinningPyramid(new Vector3(0, 0, 20), 5, 5, Color.RED);
        ECube myCube = new ECube(new Vector3(20, 0, 0), 5, 5, 5, Color.CYAN);
        mainScene.addObject(myObject);
        mainScene.addObject(myCube);
        mainScene.addObject(playerCamera);
        mainScene.setCamera(playerCamera);
        this.setScene(mainScene);

        Matrix4 test = new Matrix4(new double[] {
                1313, 131, 333, 2,
                222, 123, 3, 33,
                22, 52, 5, 555,
                33, 33, 555, 5
        });


        System.out.println(test.inverse());
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

        // Mouse movement
        Vector2 mouseDelta = this.inputManager.getMouseDelta();

        playerCamera.setRotationDegrees(playerCamera.getRotationDegrees().add(new Vector2(mouseDelta.y * this.sensitivity, mouseDelta.x * this.sensitivity)));
    }
}
