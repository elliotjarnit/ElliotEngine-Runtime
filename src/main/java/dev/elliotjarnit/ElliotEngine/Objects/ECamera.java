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

    // Row major
    public Matrix4 getCameraToWorld() {
        Vector3 position = this.getOrigin();
        Vector2 rotation = new Vector2();
        rotation.x = Math.toRadians(this.getRotation().x);
        rotation.y = Math.toRadians(this.getRotation().y);

        // Row major matrix
        Matrix4 translation = new Matrix4(new double[] {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                position.x, position.y, position.z, 1
        });

        Matrix4 rotationX = new Matrix4(new double[] {
                1, 0, 0, 0,
                0, Math.cos(rotation.x), Math.sin(rotation.x), 0,
                0, -Math.sin(rotation.x), Math.cos(rotation.x), 0,
                0, 0, 0, 1
        });

        Matrix4 rotationY = new Matrix4(new double[] {
                Math.cos(rotation.y), 0, -Math.sin(rotation.y), 0,
                0, 1, 0, 0,
                Math.sin(rotation.y), 0, Math.cos(rotation.y), 0,
                0, 0, 0, 1
        });

        return translation.mul(rotationX).mul(rotationY);
    }
}
