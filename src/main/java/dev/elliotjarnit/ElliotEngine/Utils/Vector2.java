package dev.elliotjarnit.ElliotEngine.Utils;

public class Vector2 {
    public double x;
    public double y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    public Vector2 sub(Vector2 v) {
        return new Vector2(this.x - v.x, this.y - v.y);
    }

    public Vector2 mul(Vector2 v) {
        return new Vector2(this.x * v.x, this.y * v.y);
    }
    public Vector2 mul(double d) {
        return new Vector2(this.x * d, this.y * d);
    }

    public Vector2 div(Vector2 v) {
        return new Vector2(this.x / v.x, this.y / v.y);
    }

    public Vector2 normalize() {
        return new Vector2(this.x / this.length(), this.y / this.length());
    }

    public int length() {
        return (int) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}