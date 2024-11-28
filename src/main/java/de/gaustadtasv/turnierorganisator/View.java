package de.gaustadtasv.turnierorganisator;

public enum View {
    HELLO("hello-view.fxml"),
    CONFIG("turnier-erstellen-view.fxml"),
    LOADING("turnier-loading-view.fxml"),
    EXECUTION_KNOCKOUT("turnier-execution-view.fxml"),
    EXECUTION_GROUPS("turnier-execution-no-knockout-view.fxml"),
    EXECUTION_ROUND_ROBIN("turnier-execution-view-round-robin.fxml");

    private final String filename;

    View(String filename) {
        this.filename = filename;
    }

    String getFilename() {
        return filename;
    }

}
