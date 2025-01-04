package main;

import java.awt.*;
import java.io.Serializable;

public class NotepadState implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String text;
    private final int x, y, width, height;
    private final boolean alwaysOnTop;

    public NotepadState(NotepadApp note) {
        this.text = note.getText();
        Rectangle bounds = note.getBounds();
        this.x = bounds.x;
        this.y = bounds.y;
        this.width = bounds.width;
        this.height = bounds.height;
        this.alwaysOnTop = note.isAlwaysOnTop();
    }

    public String getText() { return text; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isAlwaysOnTop() { return alwaysOnTop; }
}
