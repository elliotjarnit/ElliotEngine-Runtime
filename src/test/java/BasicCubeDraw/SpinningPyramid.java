package src.test.java.BasicCubeDraw;

import src.main.java.Game.ETetrahedron;
import src.main.java.Graphics.Color;
import src.main.java.Utils.MathUtils;

public class SpinningPyramid extends ETetrahedron {
    public SpinningPyramid(MathUtils.Vector3 origin, double width, double height, Color color) {
        super(origin, width, height, color);
    }

    @Override
    public void update() {
        this.setRotation(this.getRotation().add(new src.main.java.Utils.MathUtils.Vector2(0.000001, 0.000001)));
    }
}
