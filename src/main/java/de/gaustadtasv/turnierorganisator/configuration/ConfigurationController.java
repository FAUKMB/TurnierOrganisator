package de.gaustadtasv.turnierorganisator.configuration;

import de.gaustadtasv.turnierorganisator.View;
import de.gaustadtasv.turnierorganisator.ViewLoader;
import de.gaustadtasv.turnierorganisator.execution.TurnierExecutionState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class ConfigurationController {

    public VBox teamList;
    public TextField name;
    public Label errorText;
    public ComboBox<String> modus;
    public TextField spielzeit;
    public TextField pause;
    public TextField starttime;

    @FXML
    public void initialize() {
        TextField textField = generateTextfield();
        teamList.getChildren().add(textField);
        modus.getItems().addAll(Arrays.stream(Modus.values()).map(Modus::getPrintableOption).toList());
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        ViewLoader.getInstance().loadViewToScene(View.HELLO);
    }

    public void onErstellenButtonClick(ActionEvent actionEvent) throws IOException {
        if (checkConfigurationOk()) {
            ViewLoader.getInstance().loadViewToScene(TurnierExecutionState.getInstance().getConfiguration().modus().getExecutionView());
        }
    }

    private TextField generateTextfield() {
        TextField textField = new TextField();
        textField.setPromptText("Gib eine Mannschaft ein");

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                teamList.getChildren().remove(textField);
            } else if (noEmptyTextfieldExists()) {
                TextField newField = generateTextfield();
                teamList.getChildren().add(newField);
            }

        });

        return textField;
    }

    private boolean checkConfigurationOk() {
        Modus m;
        LocalTime start;
        String nameString = name.getText();
        long gametime;
        long pausetime;

        try {
            m = Modus.modusFromPrintableOption(modus.getValue());
        } catch (IllegalArgumentException e) {
            errorText.setText("Modus nicht ausgewaehlt");
            return false;
        }
        if (nameString.isEmpty()) {
            errorText.setText("Name fehlt");
            return false;
        }
        try {
            start = LocalTime.parse(starttime.getText());
        } catch (DateTimeParseException e) {
            errorText.setText("Startzeit ist keine Uhrzeit.");
            return false;
        }
        try {
            gametime = Long.parseLong(spielzeit.getText());
        } catch (NumberFormatException e) {
            errorText.setText("Spielzeit ist keine Zahl");
            return false;
        }
        try {
            pausetime = Long.parseLong(pause.getText());
        } catch (NumberFormatException e) {
            errorText.setText("Pause ist keine Zahl");
            return false;
        }
        List<String> teams = createTeamlist();
        if (!m.modusAllowedForNumberOfTeams(teams.size())) {
            errorText.setText("Modus nicht passend fuer die Anzahl an Teams:" + teams.size());
            return false;
        }
        TurnierConfiguration configuration = new TurnierConfiguration(m, teams, gametime, pausetime, nameString, start);
        TurnierExecutionState.getInstance().setConfiguration(configuration);
        return true;
    }

    private List<String> createTeamlist() {
        return teamList.getChildren().stream().map(n -> ((TextField) n).getText()).filter(text -> !text.isEmpty()).toList();
    }

    private boolean noEmptyTextfieldExists() {
        return teamList.getChildren().stream().map(node -> (TextField) node).noneMatch(tf -> tf.getText().isEmpty());
    }
}
