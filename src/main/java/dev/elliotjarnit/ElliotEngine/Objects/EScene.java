package dev.elliotjarnit.ElliotEngine.Objects;

import java.util.ArrayList;

import dev.elliotjarnit.ElliotEngine.Assets.SceneBaseplate;

public class EScene {
    private boolean isSetup = false;
    private ArrayList<EObject> objects;
    private ECamera currentCamera;

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

    public void removeObject(EObject object) {
        objects.remove(object);
    }

    public ArrayList<EObject> getObjects() {
        // Create a copy of the objects array
        return new ArrayList<>(objects);
    }

    public EObject getObject(int index) {
        return objects.get(index);
    }

    public void setCamera(ECamera camera) {
        if (!objects.contains(camera)) {
            addObject(camera);
        }
        currentCamera = camera;
    }

    public ECamera getCamera() {
        return currentCamera;
    }
}
