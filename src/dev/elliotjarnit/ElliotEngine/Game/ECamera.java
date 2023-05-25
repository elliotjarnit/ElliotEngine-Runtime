package src.dev.elliotjarnit.ElliotEngine.Game;

import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Matrix4;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Matrix3;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Vector3;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Vector2;

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

    // Aspect ratio is height / width
    public Matrix4 getPerspectiveProjectionMatrix(double aspectRatio) {
        double zNear = this.getNearDistance();
        double zFar = this.getRenderDistance();
        // Look at your Perspective Project Matrix notes for explanation
        double a = aspectRatio;
        double f = 1.0 / (Math.tan(this.getFov() / 2));
        double z1 = (zFar / (zFar - zNear));
        double z2 = -((zFar / (zFar - zNear)) * zNear);

        // This was very complicated. I'm dumb as shit.
        return new Matrix4(new double[] {
                f * a, 0, 0, 0,
                0, f, 0, 0,
                0, 0, z1, z2,
                0, 0, 1, 0
        });
    }

    public Matrix4 getCameraMatrix() {
        Vector2 rotation = this.getRotation();
        double yaw = Math.toRadians(rotation.x);
        double pitch = Math.toRadians(rotation.y);

        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);
        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);

        Vector3 xaxis = new Vector3(cosYaw, 0, -sinYaw);
        Vector3 yaxis = new Vector3(sinYaw * sinPitch, cosPitch, cosYaw * sinPitch);
        Vector3 zaxis = new Vector3(sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw);

        return new Matrix4(new double[] {
                xaxis.x, xaxis.y, xaxis.z, -this.getOriginX(),
                yaxis.x, yaxis.y, yaxis.z, -this.getOriginY(),
                zaxis.x, zaxis.y, zaxis.z, -this.getOriginZ(),
                0, 0, 0, 1
        });
    }
}
