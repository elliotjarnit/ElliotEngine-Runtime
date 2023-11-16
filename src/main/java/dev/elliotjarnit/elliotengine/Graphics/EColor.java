package dev.elliotjarnit.elliotengine.Graphics;

public class EColor {
    public static final EColor WHITE = new EColor(255, 255, 255);
    public static final EColor BLACK = new EColor(0, 0, 0);
    public static final EColor RED = new EColor(255, 0, 0);
    public static final EColor ORANGE = new EColor(255, 128, 0);
    public static final EColor GREEN = new EColor(0, 255, 0);
    public static final EColor LIGHT_GREEN = new EColor(144, 238, 144);
    public static final EColor BLUE = new EColor(0, 0, 255);
    public static final EColor LIGHT_BLUE = new EColor(173,216,230);
    public static final EColor YELLOW = new EColor(255, 255, 0);
    public static final EColor PURPLE = new EColor(255, 0, 255);
    public static final EColor CYAN = new EColor(0, 255, 255);

    public int r;
    public int g;
    public int b;

    public EColor(int r, int g, int b) {
        this.r = Math.max(Math.min(r, 255), 0);
        this.g = Math.max(Math.min(g, 255), 0);
        this.b = Math.max(Math.min(b, 255), 0);
    }

    public EColor() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public EColor(EColor c) {
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
    }

    public EColor add(EColor c) {
        return new EColor(this.r + c.r, this.g + c.g, this.b + c.b);
    }

    public EColor sub(EColor c) {
        return new EColor(this.r - c.r, this.g - c.g, this.b - c.b);
    }

    public EColor mul(EColor c) {
        return new EColor(this.r * c.r, this.g * c.g, this.b * c.b);
    }

    public EColor div(EColor c) {
        return new EColor(this.r / c.r, this.g / c.g, this.b / c.b);
    }

    public EColor add(int i) {
        return new EColor(this.r + i, this.g + i, this.b + i);
    }

    public EColor sub(int i) {
        return new EColor(this.r - i, this.g - i, this.b - i);
    }

    public EColor mul(int i) {
        return new EColor(this.r * i, this.g * i, this.b * i);
    }

    public EColor div(int i) {
        return new EColor(this.r / i, this.g / i, this.b / i);
    }

    public EColor add(double d) {
        return new EColor((int)(this.r + d), (int)(this.g + d), (int)(this.b + d));
    }

    public EColor sub(double d) {
        return new EColor((int)(this.r - d), (int)(this.g - d), (int)(this.b - d));
    }

    public EColor mul(double d) {
        return new EColor((int)(this.r * d), (int)(this.g * d), (int)(this.b * d));
    }

    public EColor div(double d) {
        return new EColor((int)(this.r / d), (int)(this.g / d), (int)(this.b / d));
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(this.r, this.g, this.b);
    }

    public String toString() {
        return "EColor(" + this.r + ", " + this.g + ", " + this.b + ")";
    }
}
