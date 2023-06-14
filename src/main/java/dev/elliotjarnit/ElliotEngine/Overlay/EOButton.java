package dev.elliotjarnit.ElliotEngine.Overlay;

import dev.elliotjarnit.ElliotEngine.ElliotEngine;
import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;
import dev.elliotjarnit.ElliotEngine.Window.InputManager;

import java.awt.*;
import java.util.ArrayList;

public class EOButton extends EOComponent {
    private String text;
    private int length;
    private int height;
    private int size;
    private int padding;
    private EColor color;
    private EColor textColor;
    private ArrayList<ClickListener> listeners;

    public EOButton(Vector2 position, String text) {
        super(position);
        this.text = text;
        this.size = 12;
        this.padding = 5;
        this.color = EColor.BLACK;
        this.textColor = EColor.WHITE;
        this.listeners = new ArrayList<>();
    }

    public EOButton(Vector2 position, String text, int size, int padding) {
        super(position);
        this.text = text;
        this.size = size;
        this.padding = padding;
        this.color = EColor.BLACK;
        this.textColor = EColor.WHITE;
        this.listeners = new ArrayList<>();
    }

    public EOButton(Vector2 position, String text, int size, int padding, EColor color, EColor textColor) {
        super(position);
        this.text = text;
        this.size = size;
        this.padding = padding;
        this.color = color;
        this.textColor = textColor;
        this.listeners = new ArrayList<>();
    }

    public void addListener(ClickListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void render(Graphics2D g2d) {
        Font font = new Font("TimesRoman", Font.PLAIN, this.size);

        // Get length of text
        length = g2d.getFontMetrics(font).stringWidth(this.text);
        height = g2d.getFontMetrics(font).getHeight();

        // Padding
        length += this.padding * 2;
        height += this.padding * 2;

        // Background
        g2d.setColor(this.color.toAwtColor());
        g2d.fillRect((int) this.getPosition().x, (int) this.getPosition().y, this.length, this.height);

        // Text
        g2d.setColor(this.textColor.toAwtColor());
        g2d.setFont(font);
        g2d.drawString(this.text, (int) this.getPosition().x + this.padding, (int) this.getPosition().y + this.height - this.padding);
    }

    @Override
    public void update(ElliotEngine engine) {
        if (engine.inputManager.isMouseDown(InputManager.MouseButton.LEFT)) {
            Vector2 mousePos = engine.inputManager.getMousePos();
            if (mousePos.x > this.getPosition().x && mousePos.x < this.getPosition().x + this.length) {
                if (mousePos.y > this.getPosition().y && mousePos.y < this.getPosition().y + this.height) {
                    for (ClickListener listener : this.listeners) {
                        listener.onClick();
                    }
                }
            }
        }
    }

    public static class ClickListener {
        public void onClick() {

        }
    }
}
