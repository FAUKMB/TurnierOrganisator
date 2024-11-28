package de.gaustadtasv.turnierorganisator.execution.table;

import de.gaustadtasv.turnierorganisator.execution.matchplan.Match;
import de.gaustadtasv.turnierorganisator.execution.matchplan.Matchplan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableCalculator {

    public static List<Team> calculateTableForGroup(List<Match> groupMatches, List<String> teams, boolean headToHead) {
        List<Team> teamlist = new ArrayList<>(teams.stream().map(Team::new).toList());
        doCalcTable(teamlist, groupMatches, headToHead);

        return teamlist;
    }

    private static void doCalcTable(List<Team> teams, List<Match> matches, boolean headToHead) {
        updateTeamScores(matches, teams);
        teams.sort(Comparator.comparing(Team::getPoints).thenComparing(Team::getGoalDifference).thenComparing(Team::getGoals).reversed());
        if (headToHead) {
            for (int i = 0; i < teams.size(); i++) {
                int points = teams.get(i).getPoints();
                ArrayList<Team> newTeams = new ArrayList<>();
                for (int j = i; j < teams.size(); j++) {
                    if (teams.get(j).getPoints() == points) {
                        newTeams.add(new Team(teams.get(j).getName()));
                    }
                }
                if (newTeams.size() > 1 && newTeams.size() < teams.size()) {
                    doCalcTable(newTeams, matches, true);
                    reorderTeams(teams, newTeams, i);
                }
                i += newTeams.size() - 1;
            }
        }
    }

    private static void reorderTeams(List<Team> fullTable, List<Team> headToHeadTable, int startIndex) {
        List<Team> copyFullTable = List.copyOf(fullTable);
        for (int index = startIndex; index < startIndex + headToHeadTable.size(); index++) {
            Team newTeam = correctTeamForIndex(copyFullTable, headToHeadTable.get(index - startIndex).getName());
            fullTable.set(index, newTeam);
        }
    }

    private static Team correctTeamForIndex(List<Team> fullTable, String teamName) {
        for (Team t : fullTable) {
            if (t.getName().equals(teamName)) {
                return t;
            }
        }
        throw new IllegalStateException("Team not found");
    }


    public static List<String> calculateTableGesamtKnockout(Matchplan matchplan) {
        List<Match> knockoutMatches = matchplan.getMatchListKnockout();
        List<String> table = new ArrayList<>();
        for (Match m : knockoutMatches) {
            table.add(m.getLooser());
            table.add(m.getWinner());
        }

        return table.reversed();
    }

    private static void updateTeamScores(List<Match> matches, List<Team> teamlist) {
        List<String> teams = teamlist.stream().map(Team::getName).toList();
        for (Team t : teamlist) {
            for (Match m : matches) {
                if (!m.isMatchPlayed() || !(teams.contains(m.getTeamname1()) && teams.contains(m.getTeamname2()))) {
                    continue;
                }
                if (m.getTeamname1().equals(t.getName())) {
                    if (m.getGoals1() > m.getGoals2()) {
                        t.setPoints(t.getPoints() + 3);
                    } else if (m.getGoals2() == m.getGoals1()) {
                        t.setPoints(t.getPoints() + 1);
                    }
                    t.setGoals(t.getGoals() + m.getGoals1());
                    t.setMinusgoals(t.getMinusgoals() + m.getGoals2());
                }
                if (m.getTeamname2().equals(t.getName())) {
                    if (m.getGoals1() < m.getGoals2()) {
                        t.setPoints(t.getPoints() + 3);
                    } else if (m.getGoals2() == m.getGoals1()) {
                        t.setPoints(t.getPoints() + 1);
                    }
                    t.setGoals(t.getGoals() + m.getGoals2());
                    t.setMinusgoals(t.getMinusgoals() + m.getGoals1());
                }

            }
        }
    }
}
