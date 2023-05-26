package BasicCubeDraw;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Objects.ECamera;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Utils.MathUtils;

public class Engine extends ElliotEngine {
    public ECamera playerCamera;

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run();
    }

    @Override
    public void setup() {
        EScene mainScene = new EScene();
        playerCamera = new ECamera(new MathUtils.Vector3(0, 0, 0), 90.0);
        SpinningPyramid myObject = new SpinningPyramid(new MathUtils.Vector3(0, 0, 200), 200, 200, Color.RED);
        mainScene.addObject(myObject);
        mainScene.addObject(playerCamera);
        mainScene.setCamera(playerCamera);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {
    }
}
