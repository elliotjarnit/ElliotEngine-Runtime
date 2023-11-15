package dev.elliotjarnit.ElliotEngine.Overlay;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class EOImage extends EOComponent {
    private String path;
    private BufferedImage image;

    public EOImage(Vector2 position, String path) {
        super(position);
        this.path = path;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(this.path);
        try {
            this.image = ImageIO.read(is);
        } catch (Exception e) {
            System.out.println("Failed to load image: " + this.path);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        // Center image on position
        int x = (int) this.getPosition().x - (image.getWidth(null) / 2);
        int y = (int) this.getPosition().y - (image.getHeight(null) / 2);
        g2d.drawImage(this.image, x, y, null);
    }

    @Override
    public void update(ElliotEngine engine) {

    }
}
