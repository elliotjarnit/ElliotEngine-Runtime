package dev.elliotjarnit.elliotengine.Overlay;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Utils.Vector2;

import java.awt.*;

public abstract class EOComponent {
    Vector2 position;
    public EOComponent() {
        this.position = new Vector2(0, 0);
    }

    public EOComponent(Vector2 position) {
        this.position = position;
    }

    public abstract void render(Graphics2D g2d);

    public abstract void update(ElliotEngine engine);

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return this.position;
    }
}
