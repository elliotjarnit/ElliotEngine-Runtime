package dev.elliotjarnit.ElliotEngine.Graphics;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Objects.ECamera;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class RenderingEngine extends JPanel {
    private EScene scene;
    private final ElliotEngine engine;
    private final boolean meshMap = false;
    private final int MAX_POINTS;

    public RenderingEngine(ElliotEngine engine) {
        super();
        this.engine = engine;
        MAX_POINTS = Integer.parseInt(this.engine.getOption(ElliotEngine.AdvancedOptions.MAX_CLIPPING_VERTEXES));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(scene.getSkyColor().toAwtColor());
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (scene == null) return;
        if (scene.getCamera() == null) {
            g2d.setColor(Color.WHITE.toAwtColor());
            drawCenteredString(g2d, "No camera in scene", new Rectangle(0, 0, this.getWidth(), this.getHeight()));

            return;
        }

        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        double aspectRatio = (double) getWidth() / (double) getHeight();

        Matrix4 perspectiveProjectionMatrix = scene.getCamera().getPerspectiveProjectionMatrix(aspectRatio);
        Matrix4 worldToCameraMatrix = scene.getCamera().getWorldToCameraMatrix();

        double[] zBuffer = new double[img.getWidth() * img.getHeight()];
        Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

        // This is the main object loop. Render everything in here.
        // Render all objects
        for (EObject object : scene.getObjects()) {
            Matrix4 objectToWorld = object.getObjectToWorldMatrix();

            if (object.getFaces() == null) continue;
            if (object.getClass() == ECamera.class) continue;

            for (EFace face : object.getFaces()) {
                ArrayList<Vector3> pointsToRender = new ArrayList<>();

                // Loop over all points in face
                // All transformations are applied here
                for (int i = 1; i <= 3; i++) {
                    // Object space
                    Vector3 point = new Vector3(face.getVertices()[i - 1]);
                    point.y *= -1;

                    // World Space
                    point = objectToWorld.transform(point);

                    // Camera space
                    point = worldToCameraMatrix.transform(point);

                    if (point.z < 0) continue;

                    Vector4 point4 = new Vector4(point.x, point.y, point.z, 1.0);

                    // Clip space
                    point4 = perspectiveProjectionMatrix.transform(point4);

                    double z = point4.z;

                    // Screen space
                    if (point4.w != 0) {
                        point.x /= point4.w;
                        point.y /= point4.w;
                        point.z /= point4.w;
                    }

                    // Raster space
                    point.x = (point.x + 1.0) * 0.5 * getWidth();
                    point.y = (1.0 - point.y) * 0.5 * getHeight();

                    // Add point to list
                    pointsToRender.add(new Vector3(point.x, point.y, z));
                }

                if (!meshMap) {
                    if (pointsToRender.size() != 3) continue;
                    int minX = (int) Math.max(0, Math.ceil(Math.min(pointsToRender.get(0).x, Math.min(pointsToRender.get(1).x, pointsToRender.get(2).x))));
                    int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(pointsToRender.get(0).x, Math.max(pointsToRender.get(1).x, pointsToRender.get(2).x))));
                    int minY = (int) Math.max(0, Math.ceil(Math.min(pointsToRender.get(0).y, Math.min(pointsToRender.get(1).y, pointsToRender.get(2).y))));
                    int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(pointsToRender.get(0).y, Math.max(pointsToRender.get(1).y, pointsToRender.get(2).y))));

                    for (int y = minY; y <= maxY; y++) {
                        for (int x = minX; x <= maxX; x++) {
                            Vector3 p = new Vector3(x, y, 0);

                            boolean V1 = sameSide(pointsToRender.get(0), pointsToRender.get(1), pointsToRender.get(2), p);
                            boolean V2 = sameSide(pointsToRender.get(1), pointsToRender.get(2), pointsToRender.get(0), p);
                            boolean V3 = sameSide(pointsToRender.get(2), pointsToRender.get(0), pointsToRender.get(1), p);

                            if (V1 && V2 && V3) {
                                double depth = pointsToRender.get(0).z + pointsToRender.get(1).z + pointsToRender.get(2).z;
                                int zIndex = y * img.getWidth() + x;
                                if (zBuffer[zIndex] < depth) {
                                    img.setRGB(x, y, face.getColor().toAwtColor().getRGB());
                                    zBuffer[zIndex] = depth;
                                }
                            }
                        }
                    }
                } else {
                    g2d.setColor(Color.WHITE.toAwtColor());

                    for (int i = 0; i < pointsToRender.size(); i++) {
                        Vector3 point = pointsToRender.get(i);
                        Vector3 nextPoint = pointsToRender.get((i + 1) % pointsToRender.size());

                        g2d.drawLine((int) point.x, (int) point.y, (int) nextPoint.x, (int) nextPoint.y);
                    }
                }
            }
        }
        g2d.drawImage(img, 0, 0, null);
    }

    public void renderScene(EScene scene) {
        this.scene = scene;
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

    static boolean sameSide(Vector3 A, Vector3 B, Vector3 C, Vector3 p){
        Vector3 V1V2 = new Vector3(B.x - A.x,B.y - A.y,B.z - A.z);
        Vector3 V1V3 = new Vector3(C.x - A.x,C.y - A.y,C.z - A.z);
        Vector3 V1P = new Vector3(p.x - A.x,p.y - A.y,p.z - A.z);

        // If the cross product of vector V1V2 and vector V1V3 is the same as the one of vector V1V2 and vector V1p, they are on the same side.
        // We only need to judge the direction of z
        double V1V2CrossV1V3 = V1V2.x * V1V3.y - V1V3.x * V1V2.y;
        double V1V2CrossP = V1V2.x * V1P.y - V1P.x * V1V2.y;

        return V1V2CrossV1V3 * V1V2CrossP >= 0;
    }

    public double x_intersect(Vector2[] line1, Vector2[] line2) {
        double num = (line1[0].x * line1[1].y - line1[0].y * line1[1].x) * (line2[0].x - line2[1].x) - (line1[0].x - line1[1].x) * (line2[0].x * line2[1].y - line2[0].y * line2[1].x);
        double den = (line1[0].x - line1[1].x) * (line2[0].y - line2[1].y) - (line1[0].y - line1[1].y) * (line2[0].x - line2[1].x);
        return num / den;
    }

    public double y_intersect(Vector2[] line1, Vector2[] line2) {
        double num = (line1[0].x * line1[1].y - line1[0].y * line1[1].x) * (line2[0].y - line2[1].y) - (line1[0].y - line1[1].y) * (line2[0].x * line2[1].y - line2[0].y * line2[1].x);
        double den = (line1[0].x - line1[1].x) * (line2[0].y - line2[1].y) - (line1[0].y - line1[1].y) * (line2[0].x - line2[1].x);
        return num / den;
    }

    public void clip(Vector2[] polyPoints, int x1, int y1, int x2, int y2) {
        Vector2[] newPoints = new Vector2[MAX_POINTS];
        int newPolySize = 0;

        for (int i = 0; i < polyPoints.length; i++) {
            int k = (i + 1) % polyPoints.length;
            int ix = (int) polyPoints[i].x, iy = (int) polyPoints[i].y;
            int kx = (int) polyPoints[k].y, ky = (int) polyPoints[k].y;

            int iPos = (x2 - x1) * (iy - y1) - (y2 - y1) * (ix - x1);
            int kPos = (x2 - x1) * (ky - y1) - (y2 - y1) * (kx - x1);

            if (iPos < 0 && kPos < 0) {
                newPoints[newPolySize].x = kx;
                newPoints[newPolySize].y = ky;
                newPolySize++;
            } else if (iPos >= 0 && kPos < 0) {
                newPoints[newPolySize].x = xIntersect(x1, y1, x2, y2, ix, iy, kx, ky);
                newPoints[newPolySize].y = yIntersect(x1, y1, x2, y2, ix, iy, kx, ky);
                newPolySize++;

                newPoints[newPolySize].x = kx;
                newPoints[newPolySize].y = ky;
                newPolySize++;
            } else if (iPos < 0 && kPos >= 0) {
                newPoints[newPolySize].x = xIntersect(x1, y1, x2, y2, ix, iy, kx, ky);
                newPoints[newPolySize].y = yIntersect(x1, y1, x2, y2, ix, iy, kx, ky);
                newPolySize++;
            }
        }

        for (int i = 0; i < newPolySize; i++) {
            polyPoints[i].x = newPoints[i].x;
            polyPoints[i].y = newPoints[i].y;
        }
    }

    public int xIntersect(int x1, int y1, int x2, int y2,
                          int ix, int iy, int kx, int ky) {
        return ((x1 * y2 - y1 * x2) * (ix - kx) - (x1 - x2) * (ix * ky - iy * kx)) /
                ((x1 - x2) * (iy - ky) - (y1 - y2) * (ix - kx));
    }

    public int yIntersect(int x1, int y1, int x2, int y2,
                          int ix, int iy, int kx, int ky) {
        return ((x1 * y2 - y1 * x2) * (iy - ky) - (y1 - y2) * (ix * ky - iy * kx)) /
                ((x1 - x2) * (iy - ky) - (y1 - y2) * (ix - kx));
    }

    public void suthHodgClip(Vector2[] polyPoints, Vector2[] clipperPoints) {
        for (int i = 0; i < clipperPoints.length; i++) {
            int k = (i + 1) % clipperPoints.length;

            clip(polyPoints, (int) clipperPoints[i].x,
                    (int) clipperPoints[i].y, (int) clipperPoints[k].x,
                    (int) clipperPoints[k].y);
        }
    }
}
