package dev.elliotjarnit.elliotengine.Assets;

import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Objects.ECube;
import dev.elliotjarnit.elliotengine.Utils.Vector3;

public class SceneBaseplate extends ECube {
    public SceneBaseplate() {
        super(new Vector3(0, -1, 0), 100, 1, 100, EColor.WHITE);
    }
}
