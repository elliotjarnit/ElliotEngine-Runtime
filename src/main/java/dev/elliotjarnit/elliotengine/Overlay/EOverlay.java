package dev.elliotjarnit.elliotengine.Overlay;

import java.util.ArrayList;

public class EOverlay {
    private ArrayList<EOComponent> components;
    private boolean isSetup = false;

    public EOverlay() {
        this.setup();
    }

    public void setup() {
        if (!isSetup) {
            components = new ArrayList<EOComponent>();
            isSetup = true;
        }
    }

    public ArrayList<EOComponent> getComponents() {
        return new ArrayList<>(components);
    }

    public void addComponent(EOComponent component) {
        components.add(component);
    }

    public void removeComponent(EOComponent component) {
        components.remove(component);
    }
}
