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
    private final boolean meshMap = true;
    private final int MAX_POINTS;

    public RenderingEngine(ElliotEngine engine) {
        super();
        this.engine = engine;
        MAX_POINTS = Integer.parseInt(this.engine.getOption(ElliotEngine.AdvancedOptions.MAX_CLIPPING_VERTEXES));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK.toAwtColor());
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
                ArrayList<Vector2> pointsToRender = new ArrayList<>();

                // Loop over all points in face
                // All transformations are applied here
                for (int i = 1; i <= 3; i++) {
                    // Object space
                    Vector3 point = face.getVertices()[i - 1];

                    // World Space
                    point = objectToWorld.transform(point);

                    // Camera space
                    point = worldToCameraMatrix.transform(point);

                    if (point.z < 0.0) {
                        // Point is behind camera
                        continue;
                    }

                    Vector4 point4 = new Vector4(point.x, point.y, point.z, 1.0);

                    // Clip space
                    point4 = perspectiveProjectionMatrix.transform(point4);

                    // Screen space
                    if (point4.w != 0) {
                        point.x /= point4.w;
                        point.y /= point4.w;
                        point.z /= point4.w;
                    }

                    if (point.x < -aspectRatio || point.x > aspectRatio || point.y < -1.0 || point.y > 1.0) {
                        // Point is not visible
                        continue;
                    }

                    // Raster space
                    point.x = (point.x + 1.0) * 0.5 * getWidth();
                    point.y = (1.0 - point.y) * 0.5 * getHeight();

                    // Add point to list
                    pointsToRender.add(new Vector2(point.x, point.y));
                }

                Vector2[] clipperPoints = new Vector2[4];
                clipperPoints[0] = new Vector2(0, 0);
                clipperPoints[1] = new Vector2(getWidth(), 0);
                clipperPoints[2] = new Vector2(getWidth(), getHeight());
                clipperPoints[3] = new Vector2(0, getHeight());

                Vector2[] pointsToRenderArray = new Vector2[pointsToRender.size()];
                pointsToRenderArray = pointsToRender.toArray(pointsToRenderArray);

//                this.suthHodgClip(pointsToRenderArray, clipperPoints);

                if (!meshMap) {
//                    for (int y = 0; y < this.getHeight(); ++y) {
//                        for (int x = 0; x < this.getWidth(); ++x) {
//                            Vector2 pixel = new Vector2(x + 0.5, y + 0.5);
//
//                            double w1 = edgeFunction(pointsToRender[1], pointsToRender[2], pixel);
//                            double w2 = edgeFunction(pointsToRender[2], pointsToRender[0], pixel);
//                            double w3 = edgeFunction(pointsToRender[0], pointsToRender[1], pixel);
//
//                            if (w1 >= 0 && w2 >= 0 && w3 >= 0) {
//                                img.setRGB(x, y, face.getColor().toAwtColor().getRGB());
//                            }
//                        }
//                    }
                } else {
                    g2d.setColor(Color.WHITE.toAwtColor());

                    for (int i = 0; i < pointsToRender.size(); i++) {
                        Vector2 point = pointsToRender.get(i);
                        Vector2 nextPoint = pointsToRender.get((i + 1) % pointsToRender.size());

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

    public double edgeFunction(Vector2 a, Vector2 b, Vector2 c) {
        return (c.x - a.x) * (b.y - a.y) - (c.y - a.y) * (b.x - a.x);
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
