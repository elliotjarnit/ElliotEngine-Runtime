package src.main.java.Game;

import src.main.java.Utils.MathUtils.Vector3;

import java.util.Vector;

public abstract class EObject {
    private Vector3 position;
    private float rotation;

    private boolean shown = true;


    public EObject() {
        this.position = new Vector3(0.0, 0.0, 0.0);
    }
    public EObject(double x, double y, double z) {
        this.position = new Vector3(x, y, z);
    }
    public EObject(Vector3 position) {
        this.position = new Vector3(position);
    }

    public abstract void update();





    public Vector3 getPosition() {
        return this.position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setPosition(int x, int y, int z) {
        this.position = new Vector3(x, y, z);
    }

    public void setPositionX(int x) {
        this.position.x = x;
    }

    public void setPositionY(int y) {
        this.position.y = y;
    }

    public void setPositionZ(int z) {
        this.position.z = z;
    }

    public int getPositionX() {
        return (int) this.position.x;
    }

    public int getPositionY() {
        return (int) this.position.y;
    }

    public int getPositionZ() {
        return (int) this.position.z;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void show() {
        this.shown = true;
    }

    public void hide() {
        this.shown = false;
    }

    public boolean isShown() {
        return this.shown;
    }
}
