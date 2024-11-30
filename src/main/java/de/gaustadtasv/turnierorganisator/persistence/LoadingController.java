package de.gaustadtasv.turnierorganisator.persistence;

import de.gaustadtasv.turnierorganisator.View;
import de.gaustadtasv.turnierorganisator.ViewLoader;
import de.gaustadtasv.turnierorganisator.execution.TurnierExecutionState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoadingController {
    public Button backButton;
    public Button start;
    public ListView<String> configList;

    @FXML
    public void initialize() {
        try {
            StatePersistenceUtils.createConfigFolderIfNotExist();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(StatePersistenceUtils.CONFIG_FOLDER)) {
            for (Path path : stream) {
                configList.getItems().add(path.getFileName().toString().split(".json")[0]);
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        configList.setOnMouseClicked(event -> {
            String selectedItem = configList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    StatePersistenceUtils.loadStateFromFile(selectedItem);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                TurnierExecutionState state = TurnierExecutionState.getInstance();
                ViewLoader.getInstance().loadViewToScene(state.getConfiguration().modus().getExecutionView());
            }
        });
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        ViewLoader.getInstance().loadViewToScene(View.HELLO);
    }

}
