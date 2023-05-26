package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;
import dev.elliotjarnit.ElliotEngine.Utils.Matrix4;

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

    public Vector3 getOrigin() {
        return this.origin;
    }

    public void setOrigin(Vector3 position) {
        this.origin = position;
    }

    public void setOrigin(int x, int y, int z) {
        this.origin = new Vector3(x, y, z);
    }

    public void setOriginX(int x) {
        this.origin.x = x;
    }

    public void setOriginY(int y) {
        this.origin.y = y;
    }

    public void setOriginZ(int z) {
        this.origin.z = z;
    }

    public int getOriginX() {
        return (int) this.origin.x;
    }

    public int getOriginY() {
        return (int) this.origin.y;
    }

    public int getOriginZ() {
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

    public Matrix4 getRotationMatrix() {
        Matrix4 xRotation = new Matrix4(new double[] {
                1.0, 0.0, 0.0, 0.0,
                0.0, Math.cos(this.getRotation().x), -Math.sin(this.getRotation().x), 0.0,
                0.0, Math.sin(this.getRotation().x), Math.cos(this.getRotation().x), 0.0,
                0.0, 0.0, 0.0, 1.0
        });
        Matrix4 yRotation = new Matrix4(new double[] {
                Math.cos(this.getRotation().y), 0.0, Math.sin(this.getRotation().y), 0.0,
                0.0, 1.0, 0.0, 0.0,
                -Math.sin(this.getRotation().y), 0.0, Math.cos(this.getRotation().y), 0.0,
                0.0, 0.0, 0.0, 1.0
        });
        return xRotation.mul(yRotation);
    }
}
