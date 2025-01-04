package main;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsDialog extends JDialog implements Serializable {
    private static final long serialVersionUID = 1L;

    public SettingsDialog(JFrame parent, NotepadSettings settings, Runnable onSave) {
        super(parent, "Settings", true);
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));
        List<String> monoFonts = Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames())
                .filter(fontName -> new Font(fontName, Font.PLAIN, 12).canDisplay('i') && fontName.toLowerCase().contains("mono"))
                .collect(Collectors.toList());

        JComboBox<String> fontTypeCombo = new JComboBox<>(monoFonts.toArray(new String[0]));
        fontTypeCombo.setSelectedItem(settings.getFont().getFontName());

        JTextField fontSizeField = new JTextField(String.valueOf(settings.getFont().getSize()));

        JButton textColorButton = new JButton("Choose Text Color");
        textColorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose Text Color", settings.getTextColor());
            if (color != null) {
                settings.setTextColor(color);
                settings.setOverrideMode(false);
            }
        });

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> {
            settings.setFont(new Font((String) fontTypeCombo.getSelectedItem(), Font.PLAIN, Integer.parseInt(fontSizeField.getText())));
            onSave.run();
            dispose();
        });

        add(new JLabel("Font Type:"));
        add(fontTypeCombo);
        add(new JLabel("Font Size:"));
        add(fontSizeField);
        add(new JLabel("Text Color:"));
        add(textColorButton);
        add(applyButton);

        setVisible(true);
    }
}
