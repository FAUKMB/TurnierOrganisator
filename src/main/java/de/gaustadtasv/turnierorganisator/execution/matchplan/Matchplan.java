package de.gaustadtasv.turnierorganisator.execution.matchplan;

import de.gaustadtasv.turnierorganisator.configuration.TurnierConfiguration;
import de.gaustadtasv.turnierorganisator.execution.table.GroupTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Matchplan {
    private List<Match> matchListGroup;
    private List<Match> matchListsemiFinal;
    private List<Match> matchListKnockout;
    private MatchplanGenerator generator;

    public Matchplan(TurnierConfiguration configuration) {
        generator = switch (configuration.modus()) {
            case GROUP_WITH_KO -> new GroupWithKnockoutMatchplanGenerator(configuration.teams());
            case GROUP_WITH_KO_SEMI_FINAL -> new GroupWithKnockoutSemiMatchplanGenerator(configuration.teams());
            case GROUP_WITHOUT_KO -> new MatchplanGenerator(configuration.teams());
            case ROUND_ROBIN -> new RoundRobinMatchplanGenerator(configuration.teams());
        };
        matchListGroup = generator.generateMatchesGroup();
        matchListsemiFinal = generator.generateSemiFinal();
        matchListKnockout = generator.generateMatchesKnockout();
    }

    public List<Match> matchList() {
        List<Match> combined = new ArrayList<>();
        combined.addAll(matchListGroup);
        combined.addAll(matchListsemiFinal);
        combined.addAll(matchListKnockout);
        return combined;
    }

    public void update(GroupTable groupATable, GroupTable groupBTable) {
        if (allGroupMatchesPlayed()) {
            updateMatchplanAfterGroup(groupATable, groupBTable);
        }
        if (semiFinalPlayed() && !matchListsemiFinal.isEmpty()) {
            updateMatchplanAfterSemi();
        }
    }

    private void updateMatchplanAfterGroup(GroupTable groupATable, GroupTable groupBTable) {
        int numberKnockoutMatches = matchListKnockout.size();
        for (int i = 0; i < numberKnockoutMatches - 2; i++) {
            matchListKnockout.get(i).setTeamname1(groupATable.teamFromPosition(numberKnockoutMatches - i));
            matchListKnockout.get(i).setTeamname2(groupBTable.teamFromPosition(numberKnockoutMatches - i));
        }
        if (matchListsemiFinal.isEmpty()) {
            for (int i = numberKnockoutMatches - 2; i < numberKnockoutMatches; i++) {
                matchListKnockout.get(i).setTeamname1(groupATable.teamFromPosition(numberKnockoutMatches - i));
                matchListKnockout.get(i).setTeamname2(groupBTable.teamFromPosition(numberKnockoutMatches - i));
            }
        } else {
            matchListsemiFinal.get(0).setTeamname1(groupATable.teamFromPosition(1));
            matchListsemiFinal.get(0).setTeamname2(groupBTable.teamFromPosition(2));
            matchListsemiFinal.get(1).setTeamname1(groupATable.teamFromPosition(2));
            matchListsemiFinal.get(1).setTeamname2(groupBTable.teamFromPosition(1));

        }
    }

    private void updateMatchplanAfterSemi() {
        String winnerS1 = matchListsemiFinal.get(0).getWinner();
        String winnerS2 = matchListsemiFinal.get(1).getWinner();
        String looserS1 = matchListsemiFinal.get(0).getLooser();
        String looserS2 = matchListsemiFinal.get(1).getLooser();
        matchListKnockout.getLast().setTeamname1(winnerS1);
        matchListKnockout.getLast().setTeamname2(winnerS2);
        matchListKnockout.get(matchListKnockout.size() - 2).setTeamname1(looserS1);
        matchListKnockout.get(matchListKnockout.size() - 2).setTeamname2(looserS2);
    }

    private boolean semiFinalPlayed() {
        for (Match match : matchListsemiFinal) {
            if (!match.isMatchPlayed()) {
                return false;
            }
        }
        return true;
    }

    private boolean allGroupMatchesPlayed() {
        for (Match match : matchListGroup) {
            if (!match.isMatchPlayed()) {
                return false;
            }
        }
        return true;
    }

    public List<String> teamnamesGroupA() {
        return generator.teamnamesGroupA();
    }

    public List<String> teamnamesGroupB() {
        return generator.teamnamesGroupB();
    }

}
