package src.dev.elliotjarnit.ElliotEngine.Graphics;

import src.dev.elliotjarnit.ElliotEngine.ElliotEngine;
import src.dev.elliotjarnit.ElliotEngine.Game.EFace;
import src.dev.elliotjarnit.ElliotEngine.Game.EObject;
import src.dev.elliotjarnit.ElliotEngine.Game.EScene;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Matrix3;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Matrix4;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Vector3;
import src.dev.elliotjarnit.ElliotEngine.Utils.MathUtils.Vector4;

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

        // Perspective projection matrix
        Matrix4 perspectiveProjectionMatrix = scene.getCamera().getPerspectiveProjectionMatrix((double) getHeight() / getWidth());
        Matrix4 cameraMatrix = scene.getCamera().getCameraMatrix();

        // This is the main object loop. Render everything in here.
        // Render all objects
        for (EObject object : scene.getObjects()) {
            for (EFace face : object.getFaces()) {
                // Loop over all points in face
                for (int i = 1; i <= 3; i++) {
                    Vector3 point = face.getVertices()[i - 1];

                    Vector4 point4 = new Vector4(point.x, point.y, point.z, 1.0);
                    point4 = cameraMatrix.transform(point4);
                    point4 = perspectiveProjectionMatrix.transform(point4);

                    if (point4.w != 0) {
                        point4.x /= point4.w;
                        point4.y /= point4.w;
                        point4.z /= point4.w;

                        // Convert to screen coordinates
                        point4.x += 1.0;
                        point4.y += 1.0;
                        point4.x *= 0.5 * getWidth();
                        point4.y *= 0.5 * getHeight();

                        // Draw the point using buffered image
                        if (point4.x >= 0 && point4.x < getWidth() && point4.y >= 0 && point4.y < getHeight()) {
                            System.out.println("Drawing point at " + point4.x + ", " + point4.y);
                            img.setRGB((int) point4.x, (int) point4.y, face.getColor().toAwtColor().getRGB());
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
}
