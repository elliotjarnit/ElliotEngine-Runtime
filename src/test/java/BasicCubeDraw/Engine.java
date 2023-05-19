package src.test.java.BasicCubeDraw;

import src.main.java.ElliotEngine;
import src.main.java.Game.ECamera;
import src.main.java.Game.EScene;
import src.main.java.Game.ETetrahedron;
import src.main.java.Graphics.Color;
import src.main.java.Utils.MathUtils;
import src.main.java.Window.InputManager;

public class Engine extends ElliotEngine {
    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.run();
    }

    @Override
    public void setup() {
        EScene mainScene = new EScene();
        ECamera playerCamera = new ECamera(new MathUtils.Vector3(0, 0, 0), 90.0);
        SpinningPyramid myObject = new SpinningPyramid(new MathUtils.Vector3(0, 0, 0), 200, 200, Color.RED);
        mainScene.addObject(myObject);
        mainScene.addObject(playerCamera);
//        mainScene.setCamera(playerCamera);
        this.setScene(mainScene);
    }

    @Override
    public void loop() {

    }
}
