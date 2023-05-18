package src.main.java.Graphics;

import src.main.java.ElliotEngine;
import src.main.java.Game.EFace;
import src.main.java.Game.EObject;
import src.main.java.Game.EScene;
import src.main.java.Utils.MathUtils.Matrix3;
import src.main.java.Utils.MathUtils.Vector3;
import src.main.java.Utils.MathUtils.Vector2;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.Path2D;

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

            g2d.translate(100, 100);
            g2d.setColor(Color.white);
            for (EFace face : object.getFaces()) {
                Vector3 point1 = transform.transform(face.getV1());
                Vector3 point2 = transform.transform(face.getV2());
                Vector3 point3 = transform.transform(face.getV3());

                Path2D drawingPath = new Path2D.Double();

                drawingPath.moveTo(point1.x, point1.y);
                drawingPath.lineTo(point2.x, point2.y);
                drawingPath.lineTo(point3.x, point3.y);
                drawingPath.closePath();

                g2d.draw(drawingPath);
            }
        }
    }

    public void renderScene(EScene scene) {
        this.scene = scene;
        this.repaint();
    }
}
