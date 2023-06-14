package dev.elliotjarnit.ElliotEngine.Overlay;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;

import java.awt.Graphics2D;
import java.awt.Font;

public class EOText extends EOComponent {
    private String text;
    private int fontSize;
    private EColor EColor;

    public EOText(Vector2 position, String text) {
        super(position);
        this.text = text;
        this.fontSize = 12;
        this.EColor = EColor.BLACK;
    }

    public EOText(Vector2 position, String text, int fontSize) {
        super(position);
        this.text = text;
        this.fontSize = fontSize;
        this.EColor = EColor.BLACK;
    }

    public EOText(Vector2 position, String text, int fontSize, EColor EColor) {
        super(position);
        this.text = text;
        this.fontSize = fontSize;
        this.EColor = EColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(this.EColor.toAwtColor());
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, this.fontSize));
        g2d.drawString(this.text, (int) this.getPosition().x, (int) this.getPosition().y);
    }

    @Override
    public void update(ElliotEngine engine) {

    }
}
