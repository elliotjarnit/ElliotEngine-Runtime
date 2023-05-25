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
                Vector3[] points = new Vector3[3];

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

                        points[i - 1] = new Vector3(point4.x, point4.y, point4.z);
                    }
                }

                // Little optimization
                int minX = (int) Math.max(0, Math.ceil(Math.min(points[0].x, Math.min(points[1].x, points[2].x))));
                int maxX = (int) Math.min(img.getWidth() - 1,
                        Math.floor(Math.max(points[0].x, Math.max(points[1].x, points[2].x))));
                int minY = (int) Math.max(0, Math.ceil(Math.min(points[0].y, Math.min(points[1].y, points[2].y))));
                int maxY = (int) Math.min(img.getHeight() - 1,
                        Math.floor(Math.max(points[0].y, Math.max(points[1].y, points[2].y))));

                // Loop over pixels to check if it should be rasterized
                for (int y = minY; y <= maxY; y++) {
                    for (int x = minX; x <= maxX; x++) {
                        Vector3 p = new Vector3(x,y,0);
                        // Judge once for each vertex
                        boolean V1 = sameSide(points[0],points[1],points[2],p);
                        boolean V2 = sameSide(points[1],points[2],points[0],p);
                        boolean V3 = sameSide(points[2],points[0],points[1],p);
                        if (V3 && V2 && V1) {
                            img.setRGB(x, y, face.getColor().toAwtColor().getRGB());
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
}
