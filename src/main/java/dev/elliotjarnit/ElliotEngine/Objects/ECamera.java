package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Utils.Matrix3;
import dev.elliotjarnit.ElliotEngine.Utils.Matrix4;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

public class ECamera extends EEntity {
    private double fov;
    private double renderDistance;
    private double nearDistance;

    public ECamera() {
        super();
        this.fov = 90;
        this.nearDistance = 0.1;
    }
    public ECamera(Vector3 origin) {
        super(origin);
        this.fov = 90;
        this.renderDistance = 1000;
        this.nearDistance = 0.1;
    }
    public ECamera(Vector3 origin, double fov) {
        super(origin);
        this.fov = fov;
        this.renderDistance = 1000;
        this.nearDistance = 0.1;
    }
    public ECamera(Vector3 origin, double fov, double renderDistance) {
        super(origin);
        this.fov = fov;
        this.renderDistance = renderDistance;
        this.nearDistance = 0.1;
    }
    public ECamera(Vector3 origin, double fov, double renderDistance, double nearDistance) {
        super(origin);
        this.fov = fov;
        this.renderDistance = renderDistance;
        this.nearDistance = nearDistance;
    }

    @Override
    public void update() {

    }

    public double getFov() {
        return this.fov;
    }
    public void setFov(double fov) {
        this.fov = fov;
    }

    public double getRenderDistance() {
        return this.renderDistance;
    }
    public void setRenderDistance(double renderDistance) {
        this.renderDistance = renderDistance;
    }

    public double getNearDistance() {
        return this.nearDistance;
    }
    public void setNearDistance(double nearDistance) {
        this.nearDistance = nearDistance;
    }

    public void move(Vector3 direction) {
        this.setOrigin(this.getOrigin().add(direction));
    }

    public Matrix4 getPerspectiveProjectionMatrix(double aspectRatio) {
        double far = this.getRenderDistance();
        double near = this.getNearDistance();

        double t = Math.tan(this.fov * 0.5 * Math.PI / 180) * near;
        double r = aspectRatio * t;
        double l = -r;
        double b = -t;

        return new Matrix4(new double[] {
                2 * near / (r - l), 0, 0,  0,
                0, 2 * near / (t - b), 0,  0,
                (r + l) / (r - l), (t + b) / (t - b), -(far + near) / (far / near), -1,
                0, 0, -2 * far * near / (far - near), 0
        });
    }

    public Matrix4 getCameraToWorld() {
        Vector3 position = this.getOrigin();

        double yaw = Math.toRadians(this.getRotation().x);
        double pitch = Math.toRadians(this.getRotation().y);

        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);
        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);

        // Row major
        return new Matrix4(new double[] {
            cosYaw, 0, -sinYaw, 0,
            sinYaw * sinPitch, cosPitch, cosYaw * sinPitch, 0,
            sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch, 0,
            position.x, position.y, position.z, 1
        });
    }
}
