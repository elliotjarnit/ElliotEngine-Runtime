package src.main.java.Game;

import src.main.java.Utils.MathUtils.Matrix4;
import src.main.java.Utils.MathUtils.Vector3;

public class ECamera extends EEntity {
    private double fov;

    public ECamera() {
        super();
        this.fov = 90;
    }
    public ECamera(Vector3 origin) {
        super(origin);
        this.fov = 90;
    }
    public ECamera(Vector3 origin, double fov) {
        super(origin);
        this.fov = fov;
    }

    @Override
    public void update() {

    }

    public Matrix4 getViewMatrix() {
        double cosPitch = (double) Math.cos(this.getRotation().x);
        double sinPitch = (double) Math.sin(this.getRotation().x);
        double cosYaw = (double) Math.cos(this.getRotation().y);
        double sinYaw = (double) Math.sin(this.getRotation().y);

        Vector3 xAxis = new Vector3(cosYaw, 0, -sinYaw);
        Vector3 yAxis = new Vector3(sinYaw * sinPitch, cosPitch, cosYaw * sinPitch);
        Vector3 zAxis = new Vector3(sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw);

        return new Matrix4(
                new double[] {
                        xAxis.x, yAxis.x, zAxis.x, 0,
                        xAxis.y, yAxis.y, zAxis.y, 0,
                        xAxis.z, yAxis.z, zAxis.z, 0,
                        -xAxis.dot(this.getOrigin()), -yAxis.dot(this.getOrigin()), -zAxis.dot(this.getOrigin()), 1
                }
        );
    }

    public Matrix4 getProjectionMatrix(double aspectRatio) {
        double fovRad = 1.0 / Math.tan(Math.toRadians(this.fov / 2.0));
        double zNear = 0.1;
        double zFar = 1000.0;

        return new Matrix4(
                new double[] {
                        aspectRatio * fovRad, 0, 0, 0,
                        0, fovRad, 0, 0,
                        0, 0, zFar / (zFar - zNear), 1,
                        0, 0, (-zFar * zNear) / (zFar - zNear), 0
                }
        );
    }
}
