package dev.elliotjarnit.ElliotEngine.Objects;

import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

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
        EFace[] faces = new EFace[4];

        // Origin is center of bottom face

        Vector3 top = new Vector3(origin.x, origin.y + height, origin.z);
        Vector3 frontLeft = new Vector3(origin.x - width / 2, origin.y, origin.z + width / 2);
        Vector3 frontRight = new Vector3(origin.x + width / 2, origin.y, origin.z + width / 2);
        Vector3 backLeft = new Vector3(origin.x - width / 2, origin.y, origin.z - width / 2);

        faces[0] = new EFace(frontLeft, frontRight, top, color);
        faces[1] = new EFace(frontRight, backLeft, top, color);
        faces[2] = new EFace(backLeft, frontLeft, top, color);
        faces[3] = new EFace(frontLeft, frontRight, backLeft, color);

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
