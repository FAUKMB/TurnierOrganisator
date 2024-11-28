package de.gaustadtasv.turnierorganisator.execution.table;

import lombok.Data;

@Data
public class Team {
    private int points;
    private int goals;
    private int minusgoals;
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public int getGoalDifference() {
        return goals - minusgoals;
    }
}
