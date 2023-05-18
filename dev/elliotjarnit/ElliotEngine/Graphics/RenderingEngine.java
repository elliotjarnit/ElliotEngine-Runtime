package dev.elliotjarnit.ElliotEngine.Graphics;
import dev.elliotjarnit.ElliotEngine.Game.EScene;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RenderingEngine extends JPanel {
    private Graphics2D g2d;

    public RenderingEngine() {
        super();
        // JPanel options here
        this.setBackground(java.awt.Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
    }

    public void clearRenderer() {
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void renderScene(EScene scene) {
        // This is the main object loop. Render everything in here.
        for (Object object : scene.getObjects()) {

        }
    }
}
