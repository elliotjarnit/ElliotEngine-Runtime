package dev.elliotjarnit.ElliotEngine.Objects;

import java.util.ArrayList;

import dev.elliotjarnit.ElliotEngine.Assets.SceneBaseplate;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;

public class EScene {
    private boolean isSetup = false;
    private ArrayList<EObject> objects;
    private ECamera currentCamera;
    private Color skyColor = Color.BLACK;

    public EScene() {
        setup(true);
    }

    public EScene(boolean withBaseplate) {
        setup(withBaseplate);
    }

    public void setup(boolean withBaseplate) {
        if (!isSetup) {
            // Setup objects
            if (withBaseplate) {
                objects = new ArrayList<>();
                EObject ground = new SceneBaseplate();
                objects.add(ground);
            } else {
                objects = new ArrayList<>();
            }
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

    public void setSkyColor(Color color) {
        skyColor = color;
    }
    public Color getSkyColor() {
        return skyColor;
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
