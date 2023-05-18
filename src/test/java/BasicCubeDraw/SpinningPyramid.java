package src.test.java.BasicCubeDraw;

import src.main.java.Game.EFace;
import src.main.java.Game.ETetrahedron;
import src.main.java.Graphics.Color;
import src.main.java.Utils.MathUtils;

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
        this.setRotation(this.getRotation().add(new src.main.java.Utils.MathUtils.Vector2(0.000003, 0.0)));
    }
}
