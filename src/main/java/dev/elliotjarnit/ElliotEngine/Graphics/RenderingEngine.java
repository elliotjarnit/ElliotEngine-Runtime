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

    public RenderingEngine(ElliotEngine engine) {
        super();
        this.engine = engine;

        // JPanel options here
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

    static double edgeFunction(Vector2 a, Vector2 b, Vector2 c) {
        return (c.x - a.x) * (b.y - a.y) - (c.y - a.y) * (b.x - a.x);
    }
}
