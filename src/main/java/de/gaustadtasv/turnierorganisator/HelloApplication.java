package de.gaustadtasv.turnierorganisator;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Turnierverwaltung");
        ViewLoader viewLoader = ViewLoader.getInstance();
        stage.setScene(viewLoader.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}