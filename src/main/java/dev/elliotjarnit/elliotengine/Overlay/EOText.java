package dev.elliotjarnit.elliotengine.Overlay;

import dev.elliotjarnit.elliotengine.ElliotEngine;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Utils.Vector2;

import java.awt.Graphics2D;
import java.awt.Font;

public class EOText extends EOComponent {
    private String text;
    private int fontSize;
    private EColor color;
    private EColor backgroundColor;

    public EOText(Vector2 position, String text) {
        super(position);
        this.text = text;
        this.fontSize = 12;
        this.color = color.BLACK;
    }

    public EOText(Vector2 position, String text, int fontSize, EColor color) {
        super(position);
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
    }

    public EOText(Vector2 position, String text, int fontSize, EColor color, EColor backgroundColor) {
        super(position);
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, this.fontSize));

        int textWidth = g2d.getFontMetrics().stringWidth(this.text);
        int x = (int) this.getPosition().x - (textWidth / 2);
        int y = (int) this.getPosition().y;

        if (this.backgroundColor != null) {
            g2d.setColor(this.backgroundColor.toAwtColor());
            g2d.fillRect(x - 4, y - this.fontSize - 4, textWidth + 8, this.fontSize + 8);
        }

        g2d.setColor(this.color.toAwtColor());
        g2d.drawString(this.text, x, y);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void update(ElliotEngine engine) {

    }
}
