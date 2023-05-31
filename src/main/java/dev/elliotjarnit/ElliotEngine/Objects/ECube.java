package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

public class ECube extends EObject {
    private double width;
    private double height;
    private double depth;
    private Color color;


    public ECube() {
        super();
        this.width = 100;
        this.height = 100;
        this.depth = 100;
        this.color = Color.WHITE;

        this.calculateFaces();
    }
    public ECube(Vector3 origin) {
        super(origin);
        this.width = 100;
        this.height = 100;
        this.depth = 100;
        this.color = Color.WHITE;

        this.calculateFaces();
    }
    public ECube(Vector3 origin, double width, double height, double depth) {
        super(origin);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.color = Color.WHITE;

        this.calculateFaces();
    }
    public ECube(Vector3 origin, double width, double height, double depth, Color color) {
        super(origin);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.color = color;

        this.calculateFaces();
    }

    // Faces are triangles
    private void calculateFaces() {
        Vector3 origin = this.getOrigin();
        EFace[] faces = new EFace[12];

        // Front face
        Vector3 frontTopLeft = new Vector3(origin.x - width / 2, origin.y + height / 2, origin.z + depth / 2);
        Vector3 frontTopRight = new Vector3(origin.x + width / 2, origin.y + height / 2, origin.z + depth / 2);
        Vector3 frontBottomLeft = new Vector3(origin.x - width / 2, origin.y - height / 2, origin.z + depth / 2);
        Vector3 frontBottomRight = new Vector3(origin.x + width / 2, origin.y - height / 2, origin.z + depth / 2);
        faces[0] = new EFace(frontTopLeft, frontTopRight, frontBottomLeft, color);
        faces[1] = new EFace(frontTopRight, frontBottomLeft, frontBottomRight, color);

        // Back face
        Vector3 backTopLeft = new Vector3(origin.x - width / 2, origin.y + height / 2, origin.z - depth / 2);
        Vector3 backTopRight = new Vector3(origin.x + width / 2, origin.y + height / 2, origin.z - depth / 2);
        Vector3 backBottomLeft = new Vector3(origin.x - width / 2, origin.y - height / 2, origin.z - depth / 2);
        Vector3 backBottomRight = new Vector3(origin.x + width / 2, origin.y - height / 2, origin.z - depth / 2);
        faces[2] = new EFace(backTopLeft, backTopRight, backBottomLeft, color);
        faces[3] = new EFace(backTopRight, backBottomLeft, backBottomRight, color);

        // Left face
        Vector3 leftTopLeft = new Vector3(origin.x - width / 2, origin.y + height / 2, origin.z + depth / 2);
        Vector3 leftTopRight = new Vector3(origin.x - width / 2, origin.y + height / 2, origin.z - depth / 2);
        Vector3 leftBottomLeft = new Vector3(origin.x - width / 2, origin.y - height / 2, origin.z + depth / 2);
        Vector3 leftBottomRight = new Vector3(origin.x - width / 2, origin.y - height / 2, origin.z - depth / 2);
        faces[4] = new EFace(leftTopLeft, leftTopRight, leftBottomLeft, color);
        faces[5] = new EFace(leftTopRight, leftBottomLeft, leftBottomRight, color);

        // Right face
        Vector3 rightTopLeft = new Vector3(origin.x + width / 2, origin.y + height / 2, origin.z + depth / 2);
        Vector3 rightTopRight = new Vector3(origin.x + width / 2, origin.y + height / 2, origin.z - depth / 2);
        Vector3 rightBottomLeft = new Vector3(origin.x + width / 2, origin.y - height / 2, origin.z + depth / 2);
        Vector3 rightBottomRight = new Vector3(origin.x + width / 2, origin.y - height / 2, origin.z - depth / 2);
        faces[6] = new EFace(rightTopLeft, rightTopRight, rightBottomLeft, color);
        faces[7] = new EFace(rightTopRight, rightBottomLeft, rightBottomRight, color);

        // Top face
        Vector3 topTopLeft = new Vector3(origin.x - width / 2, origin.y + height / 2, origin.z + depth / 2);
        Vector3 topTopRight = new Vector3(origin.x + width / 2, origin.y + height / 2, origin.z + depth / 2);
        Vector3 topBottomLeft = new Vector3(origin.x - width / 2, origin.y + height / 2, origin.z - depth / 2);
        Vector3 topBottomRight = new Vector3(origin.x + width / 2, origin.y + height / 2, origin.z - depth / 2);
        faces[8] = new EFace(topTopLeft, topTopRight, topBottomLeft, color);
        faces[9] = new EFace(topTopRight, topBottomLeft, topBottomRight, color);

        // Bottom face
        Vector3 bottomTopLeft = new Vector3(origin.x - width / 2, origin.y - height / 2, origin.z + depth / 2);
        Vector3 bottomTopRight = new Vector3(origin.x + width / 2, origin.y - height / 2, origin.z + depth / 2);
        Vector3 bottomBottomLeft = new Vector3(origin.x - width / 2, origin.y - height / 2, origin.z - depth / 2);
        Vector3 bottomBottomRight = new Vector3(origin.x + width / 2, origin.y - height / 2, origin.z - depth / 2);
        faces[10] = new EFace(bottomTopLeft, bottomTopRight, bottomBottomLeft, color);
        faces[11] = new EFace(bottomTopRight, bottomBottomLeft, bottomBottomRight, color);

        this.setFaces(faces);
    }

    @Override
    public void update() {

    }

    public double getWidth() {
        return this.width;
    }
    public void setWidth(double width) {
        this.width = width;
        this.calculateFaces();
    }
    public double getHeight() {
        return this.height;
    }
    public void setHeight(double height) {
        this.height = height;
        this.calculateFaces();
    }
    public double getDepth() {
        return this.depth;
    }
    public void setDepth(double depth) {
        this.depth = depth;
        this.calculateFaces();
    }
    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
        this.calculateFaces();
    }
}