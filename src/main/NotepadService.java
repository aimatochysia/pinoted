package main;

import java.util.ArrayList;
import java.util.List;

public class NotepadService {
    private static NotepadService instance;
    private final NotepadSettings settings;
    private final FileDAO fileDAO;
    private final List<Runnable> settingsObservers;

    private NotepadService() {
        settings = new NotepadSettings();
        fileDAO = new FileDAO();
        settingsObservers = new ArrayList<>();
    }

    public static NotepadService getInstance() {
        if (instance == null) instance = new NotepadService();
        return instance;
    }

    public FileDAO getFileDAO() {
        return fileDAO;
    }

    public NotepadSettings getSettings() {
        return settings;
    }

    public void updateSettings() {
        settingsObservers.forEach(Runnable::run);
    }

    public void addSettingsObserver(Runnable observer) {
        settingsObservers.add(observer);
    }
}
