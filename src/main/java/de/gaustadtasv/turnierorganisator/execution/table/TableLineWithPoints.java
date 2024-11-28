package de.gaustadtasv.turnierorganisator.execution.table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TableLineWithPoints {
    private final IntegerProperty position;
    private final StringProperty teamName;
    private final StringProperty goals;
    private final IntegerProperty points;

    public TableLineWithPoints(int position, String teamName, String goals, int points) {
        this.points = new SimpleIntegerProperty(this, "points");
        this.points.set(points);
        this.position = new SimpleIntegerProperty(this, "position");
        this.position.set(position);
        this.teamName = new SimpleStringProperty(this, "teamName");
        this.teamName.set(teamName);
        this.goals = new SimpleStringProperty(this, "goals");
        this.goals.set(goals);
    }

    public int getPosition() {
        return position.get();
    }

    public void setPosition(int position) {
        this.position.set(position);
    }

    public int getPoints() {
        return points.get();
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public String getTeamName() {
        return teamName.get();
    }

    public String getGoals() {
        return goals.get();
    }

    public void setGoals(String goals) {
        this.goals.set(goals);
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }


}
