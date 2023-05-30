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

    public Matrix3 getViewMatrix() {
        double heading = Math.toRadians(this.getRotation().x);
        double pitch = Math.toRadians(this.getRotation().y);

        Matrix3 headingTransform = new Matrix3(new double[] {
                Math.cos(heading), 0, -Math.sin(heading),
                0, 1, 0,
                Math.sin(heading), 0, Math.cos(heading)
        });

        Matrix3 pitchTransform = new Matrix3(new double[] {
                1, 0, 0,
                0, Math.cos(pitch), Math.sin(pitch),
                0, -Math.sin(pitch), Math.cos(pitch)
        });

        Vector3 negativeCameraPosition = new Vector3(-this.getOrigin().x, -this.getOrigin().y, -this.getOrigin().z);

        Matrix3 cameraTranslation = new Matrix3(new double[] {
                1, 0, negativeCameraPosition.x,
                0, 1, negativeCameraPosition.y,
                0, 0, negativeCameraPosition.z
        });

        return headingTransform.mul(pitchTransform).mul(cameraTranslation);
    }
}
