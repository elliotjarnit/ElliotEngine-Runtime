package dev.elliotjarnit.elliotengine.Assets;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Overlay.EOText;
import dev.elliotjarnit.elliotengine.Utils.Vector2;

public class FPSCounter extends EOText {
    public FPSCounter(Vector2 position) {
        super(position, "FPS: 0", 14, EColor.WHITE);
    }

    public FPSCounter(Vector2 position, int fontSize) {
        super(position, "FPS: 0", fontSize, EColor.WHITE);
    }

    public FPSCounter(Vector2 position, int fontSize, EColor color) {
        super(position, "FPS: 0", fontSize, color);
    }

    @Override
    public void update(ElliotEngine engine) {
        this.setText("FPS: " + engine.renderer.getFPS());

        super.update(engine);
    }
}
