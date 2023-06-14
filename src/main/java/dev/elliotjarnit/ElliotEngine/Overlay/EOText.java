package dev.elliotjarnit.ElliotEngine.Overlay;

import dev.elliotjarnit.ElliotEngine.Graphics.Color;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

import java.awt.Graphics2D;
import java.awt.Font;

public class EOText extends EOComponent {
    private String text;
    private int fontSize;
    private Color color;

    public EOText(String text, Vector2 position) {
        super(position);
        this.text = text;
        this.fontSize = 12;
        this.color = Color.BLACK;
    }

    public EOText(String text, Vector2 position, int fontSize) {
        super(position);
        this.text = text;
        this.fontSize = fontSize;
        this.color = Color.BLACK;
    }

    public EOText(String text, Vector2 position, int fontSize, Color color) {
        super(position);
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(this.color.toAwtColor());
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, this.fontSize));
        g2d.drawString(this.text, (int) this.getPosition().x, (int) this.getPosition().y);
    }

    @Override
    public void update() {

    }
}
