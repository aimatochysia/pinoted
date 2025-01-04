package main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager extends JFrame {
    private static final long serialVersionUID = 1L;
    private final List<NotepadApp> notepadApps;

    public ProjectManager() {
        setTitle("Project Manager");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        notepadApps = new ArrayList<>();

        JButton newNoteButton = new JButton("New Notepad");
        newNoteButton.addActionListener(e -> addNotepadApp());

        JButton saveProjectButton = new JButton("Save Project");
        saveProjectButton.addActionListener(e -> saveProject());

        JButton loadProjectButton = new JButton("Load Project");
        loadProjectButton.addActionListener(e -> loadProject());

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.add(newNoteButton);
        buttonPanel.add(saveProjectButton);
        buttonPanel.add(loadProjectButton);

        JScrollPane scrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void addNotepadApp() {
        NotepadApp note = new NotepadApp();
        notepadApps.add(note);
        note.setVisible(true);
    }

    private void saveProject() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                for (NotepadApp note : notepadApps) {
                    NotepadState state = new NotepadState(note);
                    out.writeObject(state);
                }
                JOptionPane.showMessageDialog(this, "Project saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving project: " + ex.getMessage());
            }
        }
    }

    private void loadProject() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                notepadApps.forEach(JFrame::dispose);
                notepadApps.clear();

                while (true) {
                    try {
                        NotepadState state = (NotepadState) in.readObject();
                        NotepadApp note = new NotepadApp();
                        note.restoreFromState(state); // Restore from saved state
                        note.setVisible(true);
                        notepadApps.add(note);
                    } catch (EOFException e) {
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Project loaded successfully!");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading project: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProjectManager manager = new ProjectManager();
            manager.setVisible(true);
        });
    }
}
