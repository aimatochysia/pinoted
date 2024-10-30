package main;
import javax.swing.*;

import java.awt.Component;
import java.io.*;
import java.nio.file.*;

public class FileDAO {
    private int autosaveIndex = 1;

    public void saveFile(Component parent, String content) {
        String fileName = JOptionPane.showInputDialog(parent, "Enter file name to save:");
        if (fileName != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
                writer.write(content);
                JOptionPane.showMessageDialog(parent, "File saved as " + fileName + ".txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error saving file: " + ex.getMessage());
            }
        }
    }

    public String loadFile(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                return new String(Files.readAllBytes(fileChooser.getSelectedFile().toPath()));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error loading file: " + ex.getMessage());
            }
        }
        return "";
    }

    public void autosave(String content) {
        try {
            String fileName = "autosave-" + autosaveIndex + ".txt";
            Files.write(Paths.get(fileName), content.getBytes());
            autosaveIndex = (autosaveIndex % 5) + 1;
        } catch (IOException ex) {
            System.err.println("Error during autosave: " + ex.getMessage());
        }
    }
}
