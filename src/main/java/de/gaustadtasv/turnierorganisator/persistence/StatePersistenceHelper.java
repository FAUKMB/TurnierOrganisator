package de.gaustadtasv.turnierorganisator.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.gaustadtasv.turnierorganisator.execution.TurnierExecutionState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;

public class StatePersistenceHelper {
    public static final Path CONFIG_FOLDER = Path.of("configurations");

    public static void storeStateToFile() throws IOException {
        TurnierExecutionState state = TurnierExecutionState.getInstance();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();
        createConfigFolderIfNotExist();
        Path configFile = CONFIG_FOLDER.resolve(Path.of(state.getConfiguration().name() + ".json"));
        try (FileWriter fileWriter = new FileWriter(configFile.toFile())) {
            gson.toJson(new PersistenceState(state), fileWriter);
        }
    }

    public static void createConfigFolderIfNotExist() throws IOException {
        if (!Files.exists(CONFIG_FOLDER)) {
            Files.createDirectory(CONFIG_FOLDER);
        }
    }

    public static void loadStateFromFile(String name) throws FileNotFoundException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();
        TurnierExecutionState state = TurnierExecutionState.getInstance();
        Path configFile = CONFIG_FOLDER.resolve(Path.of(name + ".json"));
        PersistenceState loadedState = gson.fromJson(new FileReader(configFile.toFile()), PersistenceState.class);
        state.setConfiguration(loadedState.getConfiguration());
        state.setLoadedMatches(loadedState.getMatchList());
    }
}
