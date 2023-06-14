package dev.elliotjarnit.ElliotEngine.Overlay;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

import java.awt.*;

public class EOImage extends EOComponent {
    private String path;

    public EOImage(Vector2 position, String path) {
        super(position);
        this.path = path;
    }

    @Override
    public void render(Graphics2D g2d) {
        Image image = Toolkit.getDefaultToolkit().getImage(this.path);
        System.out.println(image.getHeight(null));
        // Center image on position
        int x = (int) this.getPosition().x - (image.getWidth(null) / 2);
        int y = (int) this.getPosition().y - (image.getHeight(null) / 2);
        g2d.drawImage(image, x, y, null);
    }

    @Override
    public void update(ElliotEngine engine) {

    }
}
