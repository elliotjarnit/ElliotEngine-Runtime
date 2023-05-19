package src.main.java.Graphics;

import src.main.java.ElliotEngine;
import src.main.java.Game.EFace;
import src.main.java.Game.EObject;
import src.main.java.Game.EScene;
import src.main.java.Utils.MathUtils.Matrix4;
import src.main.java.Utils.MathUtils.Matrix3;
import src.main.java.Utils.MathUtils.Vector3;
import src.main.java.Utils.MathUtils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.Path2D;
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
            drawCenteredString(g2d, "No camera in scene", new Rectangle(0, 0, this.getWidth(), this.getHeight()), new Font("Arial", Font.PLAIN, 20));

            return;
        }

        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        double[] zBuffer = new double[img.getWidth() * img.getHeight()];
        Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

        // This is the main object loop. Render everything in here.
        // Render all objects
        for (EObject object : scene.getObjects()) {
            // Render all faces

            double heading = Math.toRadians(object.getRotation().x);
            Matrix3 headingTransform = new Matrix3(new double[] {
                    Math.cos(heading), 0, -Math.sin(heading),
                    0, 1, 0,
                    Math.sin(heading), 0, Math.cos(heading)
            });
            double pitch = Math.toRadians(object.getRotation().y);
            Matrix3 pitchTransform = new Matrix3(new double[] {
                    1, 0, 0,
                    0, Math.cos(pitch), Math.sin(pitch),
                    0, -Math.sin(pitch), Math.cos(pitch)
            });
            Matrix3 transform = headingTransform.multiply(pitchTransform);

            for (EFace face : object.getFaces()) {
                Vector3 point1 = transform.transform(face.getV1());
                Vector3 point2 = transform.transform(face.getV2());
                Vector3 point3 = transform.transform(face.getV3());

                // Use world to screen function



                int minX = (int) Math.max(0, Math.ceil(Math.min(point1.x, Math.min(point2.x, point3.x))));
                int maxX = (int) Math.min(img.getWidth() - 1,
                        Math.floor(Math.max(point1.x, Math.max(point2.x, point3.x))));
                int minY = (int) Math.max(0, Math.ceil(Math.min(point1.y, Math.min(point2.y, point3.y))));
                int maxY = (int) Math.min(img.getHeight() - 1,
                        Math.floor(Math.max(point1.y, Math.max(point2.y, point3.y))));

                for (int y = minY; y <= maxY; y++) {
                    for (int x = minX; x <= maxX; x++) {
                        Vector3 p = new Vector3(x,y,0);

                        boolean V1 = sameSide(point1,point2,point3,p);
                        boolean V2 = sameSide(point2,point3,point1,p);
                        boolean V3 = sameSide(point3,point1,point2,p);
                        if (V3 && V2 && V1) {
                            double depth = point1.z + point2.z + point3.z;
                            int zIndex = y * img.getWidth() + x;
                            if (zBuffer[zIndex] < depth) {
                                img.setRGB(x, y, face.getColor().toAwtColor().getRGB());
                                zBuffer[zIndex] = depth;
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

    public boolean sameSide(Vector3 A, Vector3 B, Vector3 C, Vector3 p){
        Vector3 V1V2 = new Vector3(B.x - A.x,B.y - A.y,B.z - A.z);
        Vector3 V1V3 = new Vector3(C.x - A.x,C.y - A.y,C.z - A.z);
        Vector3 V1P = new Vector3(p.x - A.x,p.y - A.y,p.z - A.z);

        double V1V2CrossV1V3 = V1V2.x * V1V3.y - V1V3.x * V1V2.y;
        double V1V2CrossP = V1V2.x * V1P.y - V1P.x * V1V2.y;

        return V1V2CrossV1V3 * V1V2CrossP >= 0;
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect) {
        drawCenteredString(g, text, rect, g.getFont());
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font ) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    public boolean worldToScreen(Vector3 world, Vector2 screen) {
        Matrix4 viewMatrix = scene.getCamera().getViewMatrix();
        Matrix4 projectionMatrix = scene.getCamera().getProjectionMatrix((double) this.getWidth() / this.getHeight());

        Vector3 point = new Vector3(world.x, world.y, world.z);

        point = viewMatrix.transform(point);
        point = projectionMatrix.transform(point);

        if (point.z < 0) return false;

        screen.x = (point.x + 1) * this.getWidth() / 2;
        screen.y = (point.y + 1) * this.getHeight() / 2;

        return true;
    }
}
