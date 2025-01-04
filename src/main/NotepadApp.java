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
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        notepadService = NotepadService.getInstance();
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        applySettings();

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        alwaysOnTopCheckbox = new JCheckBox("Always on Top");
        alwaysOnTopCheckbox.addActionListener(e -> setAlwaysOnTop(alwaysOnTopCheckbox.isSelected()));
        add(alwaysOnTopCheckbox, BorderLayout.NORTH);

        createMenuBar();

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
        textArea.setCaretColor(notepadService.getSettings().isLightMode() ? Color.BLACK : Color.WHITE);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveCurrentNote());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> loadCurrentNote());
        fileMenu.add(loadItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void saveCurrentNote() {
        notepadService.getFileDAO().saveFile(this, textArea.getText());
    }

    private void loadCurrentNote() {
        textArea.setText(notepadService.getFileDAO().loadFile(this));
    }

    public String getText() {
        return textArea.getText();
    }

    public boolean isNoteAlwaysOnTop() {
        return alwaysOnTopCheckbox.isSelected();
    }

    public void restoreFromState(NotepadState state) {
        textArea.setText(state.getText());
        setBounds(state.getX(), state.getY(), state.getWidth(), state.getHeight());
        setAlwaysOnTop(state.isAlwaysOnTop());
        alwaysOnTopCheckbox.setSelected(state.isAlwaysOnTop());
    }
}
