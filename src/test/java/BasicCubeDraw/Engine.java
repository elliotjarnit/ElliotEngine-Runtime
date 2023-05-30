package BasicCubeDraw;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Objects.ECamera;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

public class Engine extends ElliotEngine {
    public ECamera playerCamera;

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run();
    }

    @Override
    public void setup() {
        EScene mainScene = new EScene();
        playerCamera = new ECamera(new Vector3(0, 0, 0), 90.0);
        playerCamera.setRenderDistance(1000.0);
        SpinningPyramid myObject = new SpinningPyramid(new Vector3(5, 0, 0), 5, 5, Color.RED);
        SpinningPyramid myObject2 = new SpinningPyramid(new Vector3(-5, 0, 0), 5, 5, Color.RED);
        mainScene.addObject(myObject);
        mainScene.addObject(myObject2);
        mainScene.addObject(playerCamera);
        mainScene.setCamera(playerCamera);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {
        playerCamera.setRotation(new Vector2(playerCamera.getRotation().x + 1, 0));
    }
}
