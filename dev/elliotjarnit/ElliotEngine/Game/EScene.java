package dev.elliotjarnit.ElliotEngine.Game;

import dev.elliotjarnit.ElliotEngine.Assets.SceneBaseplate;

import java.util.ArrayList;

public class EScene {
    private boolean isSetup = false;
    private ArrayList<Object> objects;

    public void setup() {
        if (!isSetup) {
            // Setup objects
            objects = new ArrayList<Object>();
            EObject ground = new SceneBaseplate();
            objects.add(ground);
            isSetup = true;
        }
    }

    public Object[] getObjects() {
        return objects.toArray();
    }
}
