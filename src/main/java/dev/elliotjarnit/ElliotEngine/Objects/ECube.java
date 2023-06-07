package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Graphics.Color;
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
        Vector3 origin = new Vector3(0, 0, 0);
        EFace[] faces = new EFace[12];

        // Origin is the center of the bottom face

        // Points
        Vector3 backLeftBottom = new Vector3(origin.x - (this.width / 2), origin.y, origin.z - (this.depth / 2));
        Vector3 backRightBottom = new Vector3(origin.x + (this.width / 2), origin.y, origin.z - (this.depth / 2));
        Vector3 backLeftTop = new Vector3(origin.x - (this.width / 2), origin.y + this.height, origin.z - (this.depth / 2));
        Vector3 backRightTop = new Vector3(origin.x + (this.width / 2), origin.y + this.height, origin.z - (this.depth / 2));

        Vector3 frontLeftBottom = new Vector3(origin.x - (this.width / 2), origin.y, origin.z + (this.depth / 2));
        Vector3 frontRightBottom = new Vector3(origin.x + (this.width / 2), origin.y, origin.z + (this.depth / 2));
        Vector3 frontLeftTop = new Vector3(origin.x - (this.width / 2), origin.y + this.height, origin.z + (this.depth / 2));
        Vector3 frontRightTop = new Vector3(origin.x + (this.width / 2), origin.y + this.height, origin.z + (this.depth / 2));

        // Back face
        faces[0] = new EFace(backLeftBottom, backRightBottom, backLeftTop, this.color);
        faces[1] = new EFace(backRightBottom, backRightTop, backLeftTop, this.color);

        // Front face
        faces[2] = new EFace(frontLeftBottom, frontRightBottom, frontLeftTop, this.color);
        faces[3] = new EFace(frontRightBottom, frontRightTop, frontLeftTop, this.color);

        // Left face
        faces[4] = new EFace(backLeftBottom, frontLeftBottom, backLeftTop, this.color);
        faces[5] = new EFace(frontLeftBottom, frontLeftTop, backLeftTop, this.color);

        // Right face
        faces[6] = new EFace(backRightBottom, frontRightBottom, backRightTop, this.color);
        faces[7] = new EFace(frontRightBottom, frontRightTop, backRightTop, this.color);

        // Bottom face
        faces[8] = new EFace(backLeftBottom, backRightBottom, frontLeftBottom, this.color);
        faces[9] = new EFace(backRightBottom, frontRightBottom, frontLeftBottom, this.color);

        // Top face
        faces[10] = new EFace(backLeftTop, backRightTop, frontLeftTop, this.color);
        faces[11] = new EFace(backRightTop, frontRightTop, frontLeftTop, this.color);

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