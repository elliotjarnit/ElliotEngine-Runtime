package dev.elliotjarnit.ElliotEngine.Utils;

public class Vector4 {
    public double x;
    public double y;
    public double z;
    public double w;

    public Vector4() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }
    public Vector4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public Vector4(Vector4 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    public Vector4 add(Vector4 v) {
        return new Vector4(this.x + v.x, this.y + v.y, this.z + v.z, this.w + v.w);
    }

    public Vector4 sub(Vector4 v) {
        return new Vector4(this.x - v.x, this.y - v.y, this.z - v.z, this.w - v.w);
    }

    public Vector4 mul(Vector4 v) {
        return new Vector4(this.x * v.x, this.y * v.y, this.z * v.z, this.w * v.w);
    }
    public Vector4 mul(double d) {
        return new Vector4(this.x * d, this.y * d, this.z * d, this.w * d);
    }

    public Vector4 div(Vector4 v) {
        return new Vector4(this.x / v.x, this.y / v.y, this.z / v.z, this.w / v.w);
    }
    public Vector4 div(double d) {
        return new Vector4(this.x / d, this.y / d, this.z / d, this.w / d);
    }
}