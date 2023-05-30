package dev.elliotjarnit.ElliotEngine.Utils;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3 sub(Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector3 mul(Vector3 v) {
        return new Vector3(this.x * v.x, this.y * v.y, this.z * v.z);
    }
    public Vector3 mul(double d) {
        return new Vector3(this.x * d, this.y * d, this.z * d);
    }

    public Vector3 div(Vector3 v) {
        return new Vector3(this.x / v.x, this.y / v.y, this.z / v.z);
    }
    public Vector3 div(double d) {
        return new Vector3(this.x / d, this.y / d, this.z / d);
    }

    public Vector3 cross(Vector3 v) {
        return new Vector3(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
