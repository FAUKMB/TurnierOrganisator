package de.gaustadtasv.turnierorganisator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;

@Getter
public class ViewLoader {
    private static ViewLoader viewLoader;

    private final Scene scene;

    private ViewLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewLoader.class.getResource("hello-view.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to find view.", e);
        }
    }

    public static ViewLoader getInstance() {
        if (viewLoader == null) {
            viewLoader = new ViewLoader();
        }
        return viewLoader;
    }

    public void loadViewToScene(View view) {
        FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(view.getFilename()));
        try {
            scene.setRoot(loader.load());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to find view.", e);
        }
    }

}
