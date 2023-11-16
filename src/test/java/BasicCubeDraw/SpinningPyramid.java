package BasicCubeDraw;

import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Objects.ETetrahedron;
import dev.elliotjarnit.elliotengine.Utils.Vector2;
import dev.elliotjarnit.elliotengine.Utils.Vector3;

public class SpinningPyramid extends ETetrahedron {
    public SpinningPyramid(Vector3 origin, double width, double height, EColor EColor) {
        super(origin, width, height, EColor);
        EColor[] EColors = {
                EColor.RED,
                EColor.GREEN,
                EColor.BLUE,
                EColor.YELLOW
        };
        for (int i = 0; i < 4; i++) {
            EFace face = this.getFaces()[i];
            face.setColor(EColors[i]);
        }
    }

    @Override
    public void update() {
        this.setRotationDegrees(this.getRotationDegrees().add(new Vector2(1, 0.0)));
    }
}
