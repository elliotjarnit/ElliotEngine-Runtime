package dev.elliotjarnit.ElliotEngine.Assets;

import dev.elliotjarnit.ElliotEngine.Objects.ECube;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;

public class SceneBaseplate extends ECube {
    public SceneBaseplate() {
        super(new Vector3(0, -10, 0), 1000, 1000, 1000, new Color(0, 0, 0));
    }
}
