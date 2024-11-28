package de.gaustadtasv.turnierorganisator.execution.table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableLineWithoutPoints {
    private final IntegerProperty position;
    private final StringProperty teamName;

    public TableLineWithoutPoints(int position, String teamName) {
        this.position = new SimpleIntegerProperty(this, "position");
        this.position.set(position);
        this.teamName = new SimpleStringProperty(this, "teamName");
        this.teamName.set(teamName);
    }

    public String getTeamName() {
        return teamName.get();
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }

    public int getPosition() {
        return position.get();
    }

    public void setPosition(int position) {
        this.position.set(position);
    }
}
