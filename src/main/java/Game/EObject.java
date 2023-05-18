package src.main.java.Game;

import src.main.java.Graphics.RenderingEngine;
import src.main.java.Utils.MathUtils.Vector2;
import src.main.java.Utils.MathUtils.Vector3;

import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Vector;

public abstract class EObject {
    private Vector3 origin;
    private Vector2 rotation;
    private EFace[] faces;

    private boolean shown = true;


    public EObject() {
        this.origin = new Vector3(0.0, 0.0, 0.0);
        this.rotation = new Vector2(0.0, 0.0);
        this.faces = new EFace[0];
    }
    public EObject(Vector3 origin) {
        this.origin = new Vector3(origin);
        this.rotation = new Vector2(0.0, 0.0);
        this.faces = new EFace[0];
    }
    public EObject(Vector3 origin, Vector2 rotation) {
        this.origin = new Vector3(origin);
        this.rotation = rotation;
        this.faces = new EFace[0];
    }
    public EObject(Vector3 origin, Vector2 rotation, EFace[] faces) {
        this.origin = new Vector3(origin);
        this.rotation = rotation;
        this.faces = faces;
    }

    public abstract void update();

    public Vector3 getPosition() {
        return this.origin;
    }

    public void setPosition(Vector3 position) {
        this.origin = position;
    }

    public void setPosition(int x, int y, int z) {
        this.origin = new Vector3(x, y, z);
    }

    public void setPositionX(int x) {
        this.origin.x = x;
    }

    public void setPositionY(int y) {
        this.origin.y = y;
    }

    public void setPositionZ(int z) {
        this.origin.z = z;
    }

    public int getPositionX() {
        return (int) this.origin.x;
    }

    public int getPositionY() {
        return (int) this.origin.y;
    }

    public int getPositionZ() {
        return (int) this.origin.z;
    }

    public void setRotation(Vector2 rotation) {
        this.rotation = rotation;
    }

    public Vector2 getRotation() {
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

    public void addFace(EFace face) {
        Vector<EFace> faces = new Vector<EFace>(Arrays.asList(this.faces));
        faces.add(face);
        this.faces = faces.toArray(new EFace[0]);
    }

    public boolean removeFace(EFace face) {
        Vector<EFace> faces = new Vector<EFace>(Arrays.asList(this.faces));
        boolean result = faces.remove(face);
        this.faces = faces.toArray(new EFace[0]);
        return result;
    }

    public void setFaces(EFace[] faces) {
        this.faces = faces;
    }

    public EFace[] getFaces() {
        return this.faces;
    }
}
