package src.test.java.BasicCubeDraw;

import src.dev.elliotjarnit.ElliotEngine.Game.EFace;
import src.dev.elliotjarnit.ElliotEngine.Game.ETetrahedron;
import src.dev.elliotjarnit.ElliotEngine.Graphics.Color;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils;

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
        this.setRotation(this.getRotation().add(new src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Vector2(0.000003, 0.0)));
    }
}
