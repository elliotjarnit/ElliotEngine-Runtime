package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

public class ECube extends EObject {
    private double width;
    private double height;
    private double depth;
    private EColor EColor;


    public ECube() {
        super();
        this.width = 100;
        this.height = 100;
        this.depth = 100;
        this.EColor = EColor.WHITE;

        this.calculateFaces();
    }
    public ECube(Vector3 origin) {
        super(origin);
        this.width = 100;
        this.height = 100;
        this.depth = 100;
        this.EColor = EColor.WHITE;

        this.calculateFaces();
    }
    public ECube(Vector3 origin, double width, double height, double depth) {
        super(origin);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.EColor = EColor.WHITE;

        this.calculateFaces();
    }
    public ECube(Vector3 origin, double width, double height, double depth, EColor EColor) {
        super(origin);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.EColor = EColor;

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
        faces[0] = new EFace(backLeftBottom, backRightBottom, backLeftTop, this.EColor);
        faces[1] = new EFace(backRightBottom, backRightTop, backLeftTop, this.EColor);

        // Front face
        faces[2] = new EFace(frontLeftBottom, frontRightBottom, frontLeftTop, this.EColor);
        faces[3] = new EFace(frontRightBottom, frontRightTop, frontLeftTop, this.EColor);

        // Left face
        faces[4] = new EFace(backLeftBottom, frontLeftBottom, backLeftTop, this.EColor);
        faces[5] = new EFace(frontLeftBottom, frontLeftTop, backLeftTop, this.EColor);

        // Right face
        faces[6] = new EFace(backRightBottom, frontRightBottom, backRightTop, this.EColor);
        faces[7] = new EFace(frontRightBottom, frontRightTop, backRightTop, this.EColor);

        // Bottom face
        faces[8] = new EFace(backLeftBottom, backRightBottom, frontLeftBottom, this.EColor);
        faces[9] = new EFace(backRightBottom, frontRightBottom, frontLeftBottom, this.EColor);

        // Top face
        faces[10] = new EFace(backLeftTop, backRightTop, frontLeftTop, this.EColor);
        faces[11] = new EFace(backRightTop, frontRightTop, frontLeftTop, this.EColor);

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
    public EColor getColor() {
        return this.EColor;
    }
    public void setColor(EColor EColor) {
        this.EColor = EColor;
        this.calculateFaces();
    }
}