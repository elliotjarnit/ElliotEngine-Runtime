package src.dev.elliotjarnit.ElliotEngine.Game;

import src.dev.elliotjarnit.ElliotEngine.Graphics.Color;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Vector3;

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

        Vector3[] points = new Vector3[4];
        points[0] = new Vector3(origin.x, origin.y, origin.z);
        points[1] = new Vector3(origin.x + width, origin.y, origin.z);
        points[2] = new Vector3(origin.x + width / 2, origin.y + height, origin.z);
        points[3] = new Vector3(origin.x + width / 2, origin.y + height / 2, origin.z + width);

        faces[0] = new EFace(points[0], points[1], points[2], color);
        faces[1] = new EFace(points[0], points[1], points[3], color);
        faces[2] = new EFace(points[1], points[2], points[3], color);
        faces[3] = new EFace(points[2], points[0], points[3], color);

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
