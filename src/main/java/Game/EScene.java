package src.main.java.Game;

import java.util.ArrayList;

import src.main.java.Assets.SceneBaseplate;

public class EScene {
    private boolean isSetup = false;
    private ArrayList<EObject> objects;

    public EScene() {
        setup();
    }

    public void setup() {
        if (!isSetup) {
            // Setup objects
            objects = new ArrayList<>();
            EObject ground = new SceneBaseplate();
            objects.add(ground);
            isSetup = true;
        }
    }

    public void addObject(EObject object) {
        objects.add(object);
    }

    public ArrayList<EObject> getObjects() {
        // Create a copy of the objects array
        ArrayList<EObject> objectsCopy = new ArrayList<>(objects);
        return objectsCopy;
    }
}
