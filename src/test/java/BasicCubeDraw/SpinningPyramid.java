package BasicCubeDraw;

import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.ETetrahedron;
import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Utils.MathUtils;

public class SpinningPyramid extends ETetrahedron {
    public SpinningPyramid(MathUtils.Vector3 origin, double width, double height, Color color) {
        super(origin, width, height, color);
        Color[] colors = {
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW
        };
        for (int i = 0; i < 4; i++) {
            EFace face = this.getFaces()[i];
            face.setColor(colors[i]);
        }
    }

    @Override
    public void update() {
        this.setRotation(this.getRotation().add(new MathUtils.Vector2(1, 0.0)));
    }
}
