package de.gaustadtasv.turnierorganisator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HelloController {

    @FXML
    public void turnierErstellen(ActionEvent actionEvent) throws IOException {
        ViewLoader.getInstance().loadViewToScene(View.CONFIG);
    }

    @FXML
    public void turnierLadenOnClick(ActionEvent actionEvent) throws IOException {
        ViewLoader.getInstance().loadViewToScene(View.LOADING);
    }
}