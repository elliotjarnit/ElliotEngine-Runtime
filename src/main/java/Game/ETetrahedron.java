package src.main.java.Game;

import src.main.java.Graphics.Color;
import src.main.java.Graphics.RenderingEngine;
import src.main.java.Utils.MathUtils.Vector3;
import src.main.java.Utils.MathUtils.Matrix3;

import java.awt.*;

public class ETetrahedron extends EObject {
    private final double width;
    private final double height;
    private final Vector3 origin;
    private Color color;

    public ETetrahedron() {
        super();
        this.width = 100;
        this.height = 100;
        this.origin = new Vector3(0, 0, 0);
        this.color = Color.WHITE;
        calculateFaces();
    }
    public ETetrahedron(Vector3 origin) {
        super(origin);
        this.width = 100;
        this.height = 100;
        this.origin = new Vector3(0, 0, 0);
        this.color = Color.WHITE;
        calculateFaces();
    }
    public ETetrahedron(Vector3 origin, double width, double height) {
        super(origin);
        this.width = width;
        this.height = height;
        this.origin = origin;
        this.color = Color.WHITE;
        calculateFaces();
    }
    public ETetrahedron(Vector3 origin, double width, double height, Color color) {
        super(origin);
        this.width = width;
        this.height = height;
        this.origin = origin;
        this.color = color;
        calculateFaces();
    }
    public ETetrahedron(Vector3 origin, Color color) {
        super(origin);
        this.width = 100;
        this.height = 100;
        this.origin = origin;
        this.color = color;
        calculateFaces();
    }



    private void calculateFaces() {
        // Origin is the middle of the bottom face
        // Width is the length of the lines of the bottom face
        // Height is the height of the tetrahedron from the middle of the bottom face to the top point

        EFace[] faces = new EFace[4];

        double halfWidth = this.width / 2.0;
        double halfHeight = this.height / 2.0;

        Vector3 top = new Vector3(this.origin.x, this.origin.y - halfHeight, this.origin.z);
        Vector3 bottomLeft = new Vector3(this.origin.x - halfWidth, this.origin.y + halfHeight, this.origin.z);
        Vector3 bottomRight = new Vector3(this.origin.x + halfWidth, this.origin.y + halfHeight, this.origin.z);
        Vector3 back = new Vector3(this.origin.x, this.origin.y + halfHeight, this.origin.z + halfHeight);


        // Bottom face
        faces[0] = new EFace(bottomLeft, bottomRight, back);
        // Front
        faces[1] = new EFace(bottomRight, top, bottomLeft);
        // Left
        faces[2] = new EFace(bottomLeft, top, back);
        // Right
        faces[3] = new EFace(back, top, bottomRight);

        this.setFaces(faces);
    }


    @Override
    public void update() {}

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        calculateFaces();
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3 origin) {
        this.origin.x = origin.x;
        this.origin.y = origin.y;
        this.origin.z = origin.z;
        calculateFaces();
    }
}
