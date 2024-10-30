package main;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class NotepadApp extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private JCheckBox alwaysOnTopCheckbox;
    private NotepadService notepadService;

    public NotepadApp() {
        setTitle("Pinoted");
        setAppIcon("icon.png");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        notepadService = NotepadService.getInstance();
        textArea = new JTextArea();
        applySettings();

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        alwaysOnTopCheckbox = new JCheckBox("Always on Top");
        alwaysOnTopCheckbox.addActionListener(e -> setAlwaysOnTop(alwaysOnTopCheckbox.isSelected()));
        add(alwaysOnTopCheckbox, BorderLayout.NORTH);

        createMenuBar();
        
        notepadService.startAutosave(() -> textArea.getText());
        notepadService.addSettingsObserver(this::applySettings);
    }
    
    private void setAppIcon(String iconPath) {
        Image icon = Toolkit.getDefaultToolkit().getImage(iconPath);
        setIconImage(icon);
    }

    private void applySettings() {
        textArea.setFont(notepadService.getSettings().getFont());
        textArea.setForeground(notepadService.getSettings().getTextColor());
        textArea.setBackground(notepadService.getSettings().getBackgroundColor());
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> notepadService.saveFile(this, textArea.getText()));
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> textArea.setText(notepadService.loadFile(this)));
        fileMenu.add(loadItem);

        menuBar.add(fileMenu);
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem settingsItem = new JMenuItem("Font & Color Settings");
        settingsItem.addActionListener(e -> new SettingsDialog(this, notepadService.getSettings(), notepadService::updateSettings));
        settingsMenu.add(settingsItem);

        menuBar.add(settingsMenu);
        JToggleButton modeToggle = new JToggleButton("Light Mode");
        modeToggle.addActionListener(e -> {
            if (modeToggle.isSelected()) {
                notepadService.getSettings().applyLightMode();
                modeToggle.setText("Light Mode");
            } else {
                notepadService.getSettings().applyNightMode();
                modeToggle.setText("Night Mode");
            }
            notepadService.getSettings().setOverrideMode(true);
            applySettings();
        });
        menuBar.add(modeToggle);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NotepadApp app = new NotepadApp();
            app.setVisible(true);
        });
    }
}
