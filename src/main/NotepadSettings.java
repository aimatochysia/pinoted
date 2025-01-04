package main;
import java.awt.*;

public class NotepadSettings {
    private Font font;
    private Color textColor;
    private Color backgroundColor;
    private boolean overrideMode;
    private boolean lightmode;
    public NotepadSettings() {
        this.font = new Font("Monospaced", Font.PLAIN, 12);
        applyLightMode();
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        if (!overrideMode) this.textColor = textColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        if (!overrideMode) this.backgroundColor = backgroundColor;
    }

    public void applyLightMode() {
        this.textColor = Color.BLACK;
        this.backgroundColor = Color.WHITE;
        this.lightmode = true;
    }

    public void applyNightMode() {
        this.textColor = Color.WHITE;
        this.backgroundColor = Color.DARK_GRAY;
        this.lightmode = false;
    }

    public void setOverrideMode(boolean overrideMode) {
        this.overrideMode = overrideMode;
    }
    public boolean isLightMode() {
        return lightmode;
    }
}
