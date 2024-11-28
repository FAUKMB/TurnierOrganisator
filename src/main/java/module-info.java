module de.gaustadtasv.turnierorganisator {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.google.gson;


    opens de.gaustadtasv.turnierorganisator to javafx.fxml;
    exports de.gaustadtasv.turnierorganisator;
    exports de.gaustadtasv.turnierorganisator.execution;
    exports de.gaustadtasv.turnierorganisator.persistence;
    exports de.gaustadtasv.turnierorganisator.configuration;
    opens de.gaustadtasv.turnierorganisator.execution to javafx.fxml;
    opens de.gaustadtasv.turnierorganisator.configuration to javafx.fxml;
    opens de.gaustadtasv.turnierorganisator.persistence to javafx.fxml, com.google.gson;
    exports de.gaustadtasv.turnierorganisator.execution.matchplan;
    opens de.gaustadtasv.turnierorganisator.execution.matchplan to javafx.fxml, com.google.gson;
    exports de.gaustadtasv.turnierorganisator.execution.table;
    opens de.gaustadtasv.turnierorganisator.execution.table to javafx.fxml;
}