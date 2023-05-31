package dev.elliotjarnit.ElliotEngine.Graphics;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Objects.ECamera;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Objects.EScene;
import dev.elliotjarnit.ElliotEngine.Utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class RenderingEngine extends JPanel {
    private EScene scene;
    private final ElliotEngine engine;

    public RenderingEngine(ElliotEngine engine) {
        super();
        this.engine = engine;

        // JPanel options here
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (scene == null) return;
        if (scene.getCamera() == null) {
            g2d.setColor(Color.WHITE);
            drawCenteredString(g2d, "No camera in scene", new Rectangle(0, 0, this.getWidth(), this.getHeight()));

            return;
        }

        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        double aspectRatio = (double) getWidth() / (double) getHeight();

        Matrix4 perspectiveProjectionMatrix = scene.getCamera().getPerspectiveProjectionMatrix(aspectRatio);
        Matrix4 cameraToWorld = scene.getCamera().getCameraToWorld();
        Matrix4 worldToCamera = cameraToWorld.inverse();

        double[] zBuffer = new double[img.getWidth() * img.getHeight()];
        Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

        // This is the main object loop. Render everything in here.
        // Render all objects
        for (EObject object : scene.getObjects()) {
            Matrix4 rotationMatrix = object.getRotationMatrix();

            if (object.getFaces() == null) continue;
            if (object.getClass() == ECamera.class) continue;

            for (EFace face : object.getFaces()) {
                Vector3[] points = new Vector3[3];

                // Loop over all points in face
                // All transformations are applied here
                for (int i = 1; i <= 3; i++) {
                    // World space
                    Vector3 point = face.getVertices()[i - 1];

                    // Homogeneous world space
                    Vector4 point4 = new Vector4(point.x, point.y, point.z, 1.0);

                    // Camera space
                    point4 = worldToCamera.transform(point4);

                    point4.x /= point4.w;
                    point4.y /= point4.w;
                    point4.z /= point4.w;

                    if (point4.z < 0.0) {
                        // Point is behind camera
                        continue;
                    }

                    point4.w = 1.0;

                    // Homogeneous clip space
                    point4 = perspectiveProjectionMatrix.transform(point4);

                    // Normalized device coordinates (NDC) space
                    if (point4.w != 0.0) {
                        point4.x /= point4.w;
                        point4.y /= point4.w;
                        point4.z /= point4.w;
                    }

                    if (point4.x < -aspectRatio || point4.x > aspectRatio || point4.y < -1.0 || point4.y > 1.0) {
                        // Point is not in view
                        continue;
                    }

                    // Raster space
                    point4.x = (point4.x + 1.0) * 0.5 * getWidth();
                    point4.y = (1.0 - point4.y) * 0.5 * getHeight();

                    // Temp draw point
                    points[i - 1] = new Vector3(point4.x, point4.y, point4.z);
                }


                if (points[0] == null || points[1] == null || points[2] == null) {
                    continue;
                }

                Vector2 p1 = new Vector2(points[0].x, points[0].y);
                Vector2 p2 = new Vector2(points[1].x, points[1].y);
                Vector2 p3 = new Vector2(points[2].x, points[2].y);

                for (int y = 0; y < this.getHeight(); ++y) {
                    for (int x = 0; x < this.getWidth(); ++x) {
                        Vector2 pixel = new Vector2(x + 0.5, y + 0.5);

                        double w1 = edgeFunction(p2, p3, pixel);
                        double w2 = edgeFunction(p3, p1, pixel);
                        double w3 = edgeFunction(p1, p2, pixel);

                        if (w1 >= 0 && w2 >= 0 && w3 >= 0) {
                            double oneOverZ = points[0].z * w1 + points[1].z * w2 + points[2].z * w3;
                            double z = 1.0 / oneOverZ;

                            if (z > zBuffer[y * this.getWidth() + x]) {
                                zBuffer[y * this.getWidth() + x] = z;

                                img.setRGB(x, y, face.getColor().toAwtColor().getRGB());
                            }
                        }
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

    static double edgeFunction(Vector2 a, Vector2 b, Vector2 c)
    { return (c.x - a.x) * (b.y - a.y) - (c.y - a.y) * (b.x - a.x); }
}
