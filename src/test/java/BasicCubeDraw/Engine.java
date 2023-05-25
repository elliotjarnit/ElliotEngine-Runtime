package src.test.java.BasicCubeDraw;

import src.dev.elliotjarnit.ElliotEngine.ElliotEngine;
import src.dev.elliotjarnit.ElliotEngine.Game.ECamera;
import src.dev.elliotjarnit.ElliotEngine.Game.EScene;
import src.dev.elliotjarnit.ElliotEngine.Graphics.Color;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils;

public class Engine extends ElliotEngine {
    public ECamera playerCamera;

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run();
    }

    @Override
    public void setup() {
        EScene mainScene = new EScene();
        playerCamera = new ECamera(new MathUtils.Vector3(800, 0, 0), 90.0);
        SpinningPyramid myObject = new SpinningPyramid(new MathUtils.Vector3(5, 0, 400), 200, 200, Color.RED);
        mainScene.addObject(myObject);
        mainScene.addObject(playerCamera);
        mainScene.setCamera(playerCamera);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {

    }
}
