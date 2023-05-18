package src.test.java.BasicCubeDraw;

import src.main.java.ElliotEngine;
import src.main.java.Game.EScene;

public class Engine extends ElliotEngine {

    @Override
    public void setup() {
        EScene mainScene = new EScene();
        setScene(mainScene);
    }

    @Override
    public void loop() {

    }
}
