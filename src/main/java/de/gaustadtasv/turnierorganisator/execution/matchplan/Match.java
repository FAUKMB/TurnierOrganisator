package de.gaustadtasv.turnierorganisator.execution.matchplan;

import lombok.Data;

@Data
public class Match {
    private String teamname1;
    private String teamname2;
    private int goals1;
    private int goals2;
    private boolean matchPlayed;
    private String title;

    public Match(String teamname1, String teamname2, String title) {
        this.teamname1 = teamname1;
        this.teamname2 = teamname2;
        matchPlayed = false;
        this.title = title;
    }

    public void parseResult(String result) {
        String[] goals = result.split(":");
        goals1 = Integer.parseInt(goals[0]);
        goals2 = Integer.parseInt(goals[1]);
        matchPlayed = true;
    }

    public String getWinner() {
        if (matchPlayed) {
            if (goals1 > goals2) {
                return teamname1;
            } else if (goals2 > goals1) {
                return teamname2;
            } else {
                return "TBD";
            }
        } else {
            return "TBD";
        }
    }

    public String getLooser() {
        if (matchPlayed) {
            if (goals1 < goals2) {
                return teamname1;
            } else if (goals2 < goals1) {
                return teamname2;
            } else {
                return "TBD";
            }
        } else {
            return "TBD";
        }
    }

    public String getResultText() {
        return goals1 + ":" + goals2;
    }

    public String exportToTxt() {
        String exportLine = title + " " + teamname1 + " - " + teamname2;
        if (matchPlayed) {
            exportLine += " " + goals1 + ":" + goals2;
        }
        return exportLine;
    }

}
