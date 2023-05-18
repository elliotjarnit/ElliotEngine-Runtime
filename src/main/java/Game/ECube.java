package src.main.java.Game;

import src.main.java.Utils.MathUtils;

public class ECube extends EObject {
    public ECube() {
        super();
    }
    public ECube(double x, double y, double z) {
        super(x, y, z);
    }
    public ECube(MathUtils.Vector3 position) {
        super(position);
    }

    @Override
    public void update() {}
}
