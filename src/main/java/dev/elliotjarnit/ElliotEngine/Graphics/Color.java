package dev.elliotjarnit.ElliotEngine.Graphics;

public class Color {
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color ORANGE = new Color(255, 128, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color LIGHT_GREEN = new Color(144, 238, 144);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color LIGHT_BLUE = new Color(173,216,230);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color PURPLE = new Color(255, 0, 255);
    public static final Color CYAN = new Color(0, 255, 255);

    public int r;
    public int g;
    public int b;

    public Color(int r, int g, int b) {
        this.r = Math.max(Math.min(r, 255), 0);
        this.g = Math.max(Math.min(g, 255), 0);
        this.b = Math.max(Math.min(b, 255), 0);
    }

    public Color() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public Color(Color c) {
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
    }

    public Color add(Color c) {
        return new Color(this.r + c.r, this.g + c.g, this.b + c.b);
    }

    public Color sub(Color c) {
        return new Color(this.r - c.r, this.g - c.g, this.b - c.b);
    }

    public Color mul(Color c) {
        return new Color(this.r * c.r, this.g * c.g, this.b * c.b);
    }

    public Color div(Color c) {
        return new Color(this.r / c.r, this.g / c.g, this.b / c.b);
    }

    public Color add(int i) {
        return new Color(this.r + i, this.g + i, this.b + i);
    }

    public Color sub(int i) {
        return new Color(this.r - i, this.g - i, this.b - i);
    }

    public Color mul(int i) {
        return new Color(this.r * i, this.g * i, this.b * i);
    }

    public Color div(int i) {
        return new Color(this.r / i, this.g / i, this.b / i);
    }

    public Color add(double d) {
        return new Color((int)(this.r + d), (int)(this.g + d), (int)(this.b + d));
    }

    public Color sub(double d) {
        return new Color((int)(this.r - d), (int)(this.g - d), (int)(this.b - d));
    }

    public Color mul(double d) {
        return new Color((int)(this.r * d), (int)(this.g * d), (int)(this.b * d));
    }

    public Color div(double d) {
        return new Color((int)(this.r / d), (int)(this.g / d), (int)(this.b / d));
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(this.r, this.g, this.b);
    }

    public String toString() {
        return "Color(" + this.r + ", " + this.g + ", " + this.b + ")";
    }
}
