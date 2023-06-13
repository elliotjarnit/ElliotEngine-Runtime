package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Graphics.Color;
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
    public boolean _toRemove = false;


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
    public void addOrigin(Vector3 position) {
        this.setOrigin(this.origin.add(position));
    }
    public void subtractOrigin(Vector3 position) {
        this.setOrigin(this.origin.sub(position));
    }
    public void multiplyOrigin(Vector3 position) {
        this.setOrigin(this.origin.mul(position));
    }
    public void divideOrigin(Vector3 position) {
        this.setOrigin(this.origin.div(position));
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

    public void setRotationDegrees(Vector2 rotation) {
        this.rotation = rotation;
    }
    public void setRotationRadians(Vector2 rotation) {
        this.rotation = new Vector2(Math.toDegrees(rotation.x), Math.toDegrees(rotation.y));
    }

    public Vector2 getRotationDegrees() {
        return this.rotation;
    }
    public Vector2 getRotationRadians() {
        return new Vector2(Math.toRadians(this.rotation.x), Math.toRadians(this.rotation.y));
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

    public void setColor(Color color) {
        for (EFace face : this.faces) {
            face.setColor(color);
        }
    }

    public void setColor(int r, int g, int b) {
        this.setColor(new Color(r, g, b));
    }

    public Matrix4 getRotationMatrix() {
        Vector2 rotation = this.getRotationRadians();

        Matrix4 xRotation = new Matrix4(new double[] {
                1.0, 0.0, 0.0, 0.0,
                0.0, Math.cos(rotation.x), -Math.sin(rotation.x), 0.0,
                0.0, Math.sin(rotation.x), Math.cos(rotation.x), 0.0,
                0.0, 0.0, 0.0, 1.0
        });

        Matrix4 yRotation = new Matrix4(new double[] {
                Math.cos(rotation.y), 0.0, Math.sin(rotation.y), 0.0,
                0.0, 1.0, 0.0, 0.0,
                -Math.sin(rotation.y), 0.0, Math.cos(rotation.y), 0.0,
                0.0, 0.0, 0.0, 1.0
        });

        return xRotation.mul(yRotation);
    }

    public Matrix4 getTranslationMatrix() {
        Vector3 origin = this.getOrigin();

        return new Matrix4(new double[] {
                1.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                origin.x, -origin.y, origin.z, 1.0
        });
    }

    public Matrix4 getObjectToWorldMatrix() {
        return this.getRotationMatrix().mul(this.getTranslationMatrix());
    }

    public void delete() {
        for (EFace face : this.faces) {
            face = null;
        }

        this._toRemove = true;
    }
}
