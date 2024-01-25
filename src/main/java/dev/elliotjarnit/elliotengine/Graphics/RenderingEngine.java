package dev.elliotjarnit.elliotengine.Graphics;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Objects.EObject;
import dev.elliotjarnit.elliotengine.Objects.EScene;
import dev.elliotjarnit.elliotengine.Overlay.EOComponent;
import dev.elliotjarnit.elliotengine.Overlay.EOverlay;
import dev.elliotjarnit.elliotengine.Utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class RenderingEngine extends JPanel {
    private EScene scene;
    private EOverlay overlay;
    private final ElliotEngine engine;
    private double lastFrameTime = System.nanoTime();
    private HashMap<String, EObject> pixelMap = new HashMap<>();
    private volatile boolean currentlyRendering = false;
    private int fps = 0;


    // Options
    private ProjectionMode projectionMode = ProjectionMode.PERSPECTIVE;
    private RenderMode renderMode = RenderMode.SOLID;
    private int fpsCap = -1;


    // Enums
    public enum RenderMode {
        WIREFRAME,
        SOLID,
        BOTH
    }
    public enum ProjectionMode {
        PERSPECTIVE,
        ORTHOGRAPHIC
    }


    public RenderingEngine(ElliotEngine engine) {
        super();
        this.engine = engine;
    }

    public void setProjectionMode(ProjectionMode mode) {
        this.projectionMode = mode;
    }
    public ProjectionMode getProjectionMode() {
        return this.projectionMode;
    }

    public void setRenderMode(RenderMode mode) {
        this.renderMode = mode;
    }
    public RenderMode getRenderMode() {
        return this.renderMode;
    }

    public void setFpsCap(int fpsCap) {
        this.fpsCap = fpsCap;
    }
    public int getFpsCap() {
        return this.fpsCap;
    }

    // JPanel built-in paint function
    // Start of rendering frame
    @Override
    public void paintComponent(Graphics g) {
        this.currentlyRendering = true;
        Graphics2D g2d = (Graphics2D) g;
        double currentTime = System.nanoTime();

        // FPS cap
        if (fpsCap != -1) {
            double targetTime = 1.0 / fpsCap;
            double timeTaken = (currentTime - lastFrameTime) / 1000000000;
            if (timeTaken < targetTime) {
                try {
                    Thread.sleep((long) ((targetTime - timeTaken) * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Calculate FPS
        currentTime = System.nanoTime();
        fps = (int) Math.round(1 / ((currentTime - lastFrameTime) / 1000000000));
        lastFrameTime = currentTime;

        // Clear screen
        if (scene != null) g2d.setColor(scene.getSkyColor().toAwtColor());
        else g2d.setColor(EColor.BLACK.toAwtColor());
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        // No scene selected visual
        if (scene == null) {
            g2d.setColor(EColor.WHITE.toAwtColor());
            drawCenteredString(g2d, "No scene", new Rectangle(0, 0, this.getWidth(), this.getHeight()));
            this.currentlyRendering = false;
            return;
        }

        // No camera selected visual
        if (scene.getCamera() == null) {
            g2d.setColor(EColor.WHITE.toAwtColor());
            drawCenteredString(g2d, "No camera in scene", new Rectangle(0, 0, this.getWidth(), this.getHeight()));
            this.currentlyRendering = false;
            return;
        }

        // Scene rendering
        this.renderScene(this.scene, g2d);

        // Overlay rendering
        if (overlay != null) {
            for (EOComponent component : overlay.getComponents()) {
                component.render(g2d);
            }
        }
        this.currentlyRendering = false;
    }

    public void renderScene(EScene scene, Graphics2D g2d) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        double[] zBuffer = new double[img.getWidth() * img.getHeight()];
        Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

        // Loop over all objects in scene
        for (EObject object : scene.getObjects()) {
            for (EFace face : object.getFaces()) {
                Vector3[] facePoints = new Vector3[3];

                // Loop over all points in face
                // All transformations are applied here
                for (int i = 0; i < 3; i++) {
                    Vector3 objectSpace = new Vector3(face.getVertices()[i]);
                    objectSpace.y *= -1;

                    Vector3 worldSpace = this.objectToWorldSpace(objectSpace, object);

                    Vector3 cameraSpace = this.worldToCameraSpace(worldSpace, scene);

                    // If point behind camera, skip
                    if (cameraSpace.z < 0) continue;

                    Vector3 screenSpace = this.cameraToScreenSpace(cameraSpace, scene);

                    facePoints[i] = screenSpace;
                }

                // Don't render face if point is missing
                if (facePoints[0] == null || facePoints[1] == null || facePoints[2] == null) continue;

                if (renderMode == RenderMode.SOLID) {
                    this.renderFaceInWorld(img, object, zBuffer, facePoints[0], facePoints[1], facePoints[2], face.getColor());
                } else if (renderMode == RenderMode.WIREFRAME) {
                    this.renderFaceInWorldWireframe(g2d, facePoints[0], facePoints[1], facePoints[2]);
                } else if (renderMode == RenderMode.BOTH) {
                    this.renderFaceInWorld(img, object, zBuffer, facePoints[0], facePoints[1], facePoints[2], face.getColor());
                    this.renderFaceInWorldWireframe(g2d, facePoints[0], facePoints[1], facePoints[2]);
                }
            }
        }

        g2d.drawImage(img, 0, 0, null);
    }

    public Vector3 objectToWorldSpace(Vector3 objectSpace, EObject object) {
        return object.getObjectToWorldMatrix().transform(objectSpace);
    }

    public Vector3 worldToCameraSpace(Vector3 worldSpace, EScene scene) {
        return scene.getCamera().getWorldToCameraMatrix().transform(worldSpace);
    }

    public Vector3 cameraToScreenSpace(Vector3 cameraSpace, EScene scene) {
        Vector3 screenSpace;
        if (projectionMode == ProjectionMode.PERSPECTIVE) {
            screenSpace = scene.getCamera().getPerspectiveProjectionMatrix((double) getWidth() / (double) getHeight()).transform(cameraSpace);
        } else if (projectionMode == ProjectionMode.ORTHOGRAPHIC) {
            screenSpace = scene.getCamera().getOrthographicProjectionMatrix((double) getWidth() / (double) getHeight()).transform(cameraSpace);
        } else {
            screenSpace = new Vector3();
        }

        screenSpace.x = (screenSpace.x + 1.0) * 0.5 * getWidth();
        screenSpace.y = (1.0 - screenSpace.y) * 0.5 * getHeight();

        // Depth used for depth buffer
        screenSpace.z = (screenSpace.z + 1.0) * 0.5;
        return screenSpace;
    }

    public void renderFaceInWorld(BufferedImage img, EObject object, double[] zBuffer, Vector3 point1, Vector3 point2, Vector3 point3, EColor color) {
        int minX = (int) Math.max(0, Math.ceil(Math.min(point1.x, Math.min(point2.x, point3.x))));
        int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(point1.x, Math.max(point2.x, point3.x))));
        int minY = (int) Math.max(0, Math.ceil(Math.min(point1.y, Math.min(point2.y, point3.y))));
        int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(point1.y, Math.max(point2.y, point3.y))));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Vector3 p = new Vector3(x, y, 0);

                boolean V1 = sameSide(point1, point2, point3, p);
                boolean V2 = sameSide(point2, point3, point1, p);
                boolean V3 = sameSide(point3, point1, point2, p);

                if (V1 && V2 && V3) {
                    Vector4 Vert1 = new Vector4(point1);
                    Vector4 Vert2 = new Vector4(point2);
                    Vector4 Vert3 = new Vector4(point3);
                    double depth = interpolateDepth((int) p.x, (int) p.y, Vert1, Vert2, Vert3);
                    int zIndex = y * img.getWidth() + x;
                    if (zBuffer[zIndex] < depth) {
                        zBuffer[zIndex] = depth;
                        img.setRGB(x, y, color.toAwtColor().getRGB());
                        this.pixelMap.put(x + "," + y, object);
                    }
                }
            }
        }
    }

    public void renderFaceInWorldWireframe(Graphics g2d, Vector3 point1, Vector3 point2, Vector3 point3) {
        g2d.setColor(EColor.WHITE.toAwtColor());
        Vector3[] points = {point1, point2, point3};

        for (int i = 0; i < points.length; i++) {
            Vector3 nextPoint = points[(i + 1) % points.length];
            g2d.drawLine((int) points[i].x, (int) points[i].y, (int) nextPoint.x, (int) nextPoint.y);
        }
    }

    public void setScene(EScene scene) {
        this.scene = scene;
        this.repaint();
    }

    public void setOverlay(EOverlay overlay) {
        this.overlay = overlay;
        this.repaint();
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect) {
        drawCenteredString(g, text, rect, g.getFont());
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    public int getFPS() {
        return this.fps;
    }

    public EObject getObjectAtPoint(Vector2 point) {
        while (this.currentlyRendering) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return this.pixelMap.get((int) point.x + "," + (int) point.y);
    }

    public EObject getObjectLookingAt() {
        // Calcuate middle of screen
        Vector2 middle = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        return this.getObjectAtPoint(middle);
    }

    private boolean pointInTriangle(Vector3 p, Vector3 A, Vector3 B, Vector3 C){
        return sameSide(A,B,C,p) && sameSide(B,C,A,p) && sameSide(C,A,B,p);
    }

    private boolean sameSide(Vector3 A, Vector3 B, Vector3 C, Vector3 p){
        Vector3 V1V2 = new Vector3(B.x - A.x,B.y - A.y,B.z - A.z);
        Vector3 V1V3 = new Vector3(C.x - A.x,C.y - A.y,C.z - A.z);
        Vector3 V1P = new Vector3(p.x - A.x,p.y - A.y,p.z - A.z);

        double V1V2CrossV1V3 = V1V2.x * V1V3.y - V1V3.x * V1V2.y;
        double V1V2CrossP = V1V2.x * V1P.y - V1P.x * V1V2.y;

        return V1V2CrossV1V3 * V1V2CrossP >= 0;
    }


    private double interpolateDepth(int x, int y, Vector4 vertexA, Vector4 vertexB, Vector4 vertexC) {
        double areaABC = calculateTriangleArea(vertexA, vertexB, vertexC);
        double areaPBC = calculateTriangleArea(new Vector4(x, y, 0, 1), vertexB, vertexC);
        double areaPCA = calculateTriangleArea(vertexA, new Vector4(x, y, 0, 1), vertexC);
        double areaPAB = calculateTriangleArea(vertexA, vertexB, new Vector4(x, y, 0, 1));

        double w1 = areaPBC / areaABC;
        double w2 = areaPCA / areaABC;
        double w3 = areaPAB / areaABC;

        return w1 * vertexA.z + w2 * vertexB.z + w3 * vertexC.z;
    }

    private double calculateTriangleArea(Vector4 vertex1, Vector4 vertex2, Vector4 vertex3) {
        return 0.5f * ((vertex2.x - vertex1.x) * (vertex3.y - vertex1.y) - (vertex3.x - vertex1.x) * (vertex2.y - vertex1.y));
    }
}
