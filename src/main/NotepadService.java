package main;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotepadService {
    private static NotepadService instance;
    private final NotepadSettings settings;
    private final FileDAO fileDAO;
    private final List<Runnable> settingsObservers;
    private Timer autosaveTimer;

    private NotepadService() {
        settings = new NotepadSettings();
        fileDAO = new FileDAO();
        settingsObservers = new ArrayList<>();
    }

    public static NotepadService getInstance() {
        if (instance == null) instance = new NotepadService();
        return instance;
    }

    public void saveFile(Component parent, String content) {
        fileDAO.saveFile(parent, content);
    }

    public String loadFile(Component parent) {
        return fileDAO.loadFile(parent);
    }

    public void startAutosave(ContentProvider contentProvider) {
        autosaveTimer = new Timer(true);
        autosaveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                fileDAO.autosave(contentProvider.getContent());
            }
        }, 5 * 60 * 1000, 5 * 60 * 1000);
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
