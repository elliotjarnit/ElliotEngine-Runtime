package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Utils.Matrix4;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

public class EFace {
    private Vector3 v1;
    private Vector3 v2;
    private Vector3 v3;
    private EColor EColor;

    public EFace(Vector3 v1, Vector3 v2, Vector3 v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.EColor = EColor.WHITE;
    }
    public EFace(Vector3 v1, Vector3 v2, Vector3 v3, EColor EColor) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.EColor = EColor;
    }
    public EFace(Vector2 v1, Vector2 v2, Vector2 v3) {
        this.v1 = new Vector3(v1.x, v1.y, 0);
        this.v2 = new Vector3(v2.x, v2.y, 0);
        this.v3 = new Vector3(v3.x, v3.y, 0);
        this.EColor = EColor.WHITE;
    }
    public EFace(Vector2 v1, Vector2 v2, Vector2 v3, EColor EColor) {
        this.v1 = new Vector3(v1.x, v1.y, 0);
        this.v2 = new Vector3(v2.x, v2.y, 0);
        this.v3 = new Vector3(v3.x, v3.y, 0);
        this.EColor = EColor;
    }
    public EFace(Vector3 center, Vector3 v1, Vector3 v2, Vector3 v3) {
        this.v1 = center.add(v1);
        this.v2 = center.add(v2);
        this.v3 = center.add(v3);
        this.EColor = EColor.WHITE;
    }
    public EFace(Vector3 center, Vector3 v1, Vector3 v2, Vector3 v3, EColor EColor) {
        this.v1 = center.add(v1);
        this.v2 = center.add(v2);
        this.v3 = center.add(v3);
        this.EColor = EColor;
    }

    public void setV1(Vector3 v1) {
        this.v1 = v1;
    }

    public void setV2(Vector3 v2) {
        this.v2 = v2;
    }

    public void setV3(Vector3 v3) {
        this.v3 = v3;
    }

    public Vector3 getV1() {
        return this.v1;
    }

    public Vector3 getV2() {
        return this.v2;
    }

    public Vector3 getV3() {
        return this.v3;
    }

    public Vector3[] getVertices() {
        return new Vector3[] {this.v1, this.v2, this.v3};
    }

    public Vector3 getCenter() {
        return new Vector3((this.v1.x + this.v2.x + this.v3.x) / 3, (this.v1.y + this.v2.y + this.v3.y) / 3, (this.v1.z + this.v2.z + this.v3.z) / 3);
    }

    public Vector3 getNormal() {
        Vector3 v1 = this.v2.sub(this.v1);
        Vector3 v2 = this.v3.sub(this.v1);
        return new Vector3(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
    }

    public Vector3 getNormal(Vector3 v1, Vector3 v2, Vector3 v3) {
        Vector3 v1_ = v2.sub(v1);
        Vector3 v2_ = v3.sub(v1);
        return new Vector3(v1_.y * v2_.z - v1_.z * v2_.y, v1_.z * v2_.x - v1_.x * v2_.z, v1_.x * v2_.y - v1_.y * v2_.x);
    }

    public Vector3 getNormal(Vector3 center) {
        Vector3 v1 = this.v2.sub(this.v1);
        Vector3 v2 = this.v3.sub(this.v1);
        return new Vector3(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x).add(center);
    }

    public Vector3 getNormal(Vector3 center, Vector3 v1, Vector3 v2, Vector3 v3) {
        Vector3 v1_ = v2.sub(v1);
        Vector3 v2_ = v3.sub(v1);
        return new Vector3(v1_.y * v2_.z - v1_.z * v2_.y, v1_.z * v2_.x - v1_.x * v2_.z, v1_.x * v2_.y - v1_.y * v2_.x).add(center);
    }

    public EColor getColor() {
        return this.EColor;
    }

    public void setColor(EColor EColor) {
        this.EColor = EColor;
    }

    public Matrix4 getMatrix() {
        return new Matrix4(new double[] {
            this.v1.x, this.v2.x, this.v3.x, 0,
            this.v1.y, this.v2.y, this.v3.y, 0,
            this.v1.z, this.v2.z, this.v3.z, 0,
            1, 1, 1, 1
        });
    }

    public String toString() {
        return "EFace[v1=" + this.v1 + ", v2=" + this.v2 + ", v3=" + this.v3 + ", EColor=" + this.EColor + "]";
    }
}
