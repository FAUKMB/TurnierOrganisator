package de.gaustadtasv.turnierorganisator.execution.matchplan;


import de.gaustadtasv.turnierorganisator.configuration.Modus;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class MatchplanGeneratorTest {

    private List<String> teamnames(int numberOfTeams) {
        List<String> teams = new ArrayList<>();
        for (char i = 'a'; i < numberOfTeams + 'a'; i++) {
            teams.add(String.valueOf(i));
        }
        return teams;
    }

    private int containsTeamnameCount(String teamname, List<Match> matches) {
        int count = 0;
        for (Match m : matches) {
            if (m.getTeamname1().equals(teamname) || m.getTeamname2().equals(teamname)) {
                count++;
            }
        }
        return count;
    }

    private boolean selfMatch(List<Match> matches) {
        for (Match m : matches) {
            if (m.getTeamname2().equals(m.getTeamname1())) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testMatchplanRoundRobin() {
        List<Integer> sizes = Modus.ROUND_ROBIN.getAllowedNubmerOfTeams();
        for (int size : sizes) {
            List<String> teams = teamnames(size);
            RoundRobinMatchplanGenerator generator = new RoundRobinMatchplanGenerator(teams);
            List<Match> matches = generator.generateMatchesGroup();
            assertFalse(selfMatch(matches));
            for (String team : teams) {
                assertEquals(size - 1, containsTeamnameCount(team, matches));
            }
            assertEquals(generator.generateMatchesKnockout().size(), 0);
            assertEquals(generator.generateSemiFinal().size(), 0);
        }
    }

    @Test
    public void testMatchplanGroupWithoutKnockout() {
        List<Integer> sizes = Modus.GROUP_WITHOUT_KO.getAllowedNubmerOfTeams();
        for (int size : sizes) {
            List<String> teams = teamnames(size);
            GroupWithoutKnockoutMatchplanGenerator generator = new GroupWithoutKnockoutMatchplanGenerator(teams);
            testGroupstage(generator, teams);
            assertEquals(generator.generateMatchesKnockout().size(), 0);
            assertEquals(generator.generateSemiFinal().size(), 0);
        }
    }

    private void testGroupstage(GroupWithoutKnockoutMatchplanGenerator generator, List<String> teams) {
        List<String> groupA = generator.teamnamesGroupA();
        List<String> groupB = generator.teamnamesGroupB();
        List<String> combinedGroups = new ArrayList<>();
        combinedGroups.addAll(groupA);
        combinedGroups.addAll(groupB);
        assertEquals(teams, combinedGroups);
        List<Match> matches = generator.generateMatchesGroup();
        for (Match m : matches) {
            assertTrue(noMatchAgainstTeamFromOtherGroup(m, groupA, groupB));
        }
        for (String team : groupA) {
            assertEquals(containsTeamnameCount(team, matches), groupA.size() - 1);
        }
        for (String team : groupB) {
            assertEquals(containsTeamnameCount(team, matches), groupB.size() - 1);
        }
    }

    @Test
    public void testMatchplanGroupWithKnockout() {
        List<Integer> sizes = Modus.GROUP_WITH_KO.getAllowedNubmerOfTeams();
        for (int size : sizes) {
            List<String> teams = teamnames(size);
            GroupWithoutKnockoutMatchplanGenerator generator = new GroupWithKnockoutMatchplanGenerator(teams);
            testGroupstage(generator, teams);
            assertEquals(generator.generateMatchesKnockout().size(), teams.size() / 2);
            assertEquals(generator.generateSemiFinal().size(), 0);
        }
    }

    @Test
    public void testMatchplanGroupWithKnockoutSemi() {
        List<Integer> sizes = Modus.GROUP_WITH_KO_SEMI_FINAL.getAllowedNubmerOfTeams();
        for (int size : sizes) {
            List<String> teams = teamnames(size);
            GroupWithoutKnockoutMatchplanGenerator generator = new GroupWithKnockoutSemiMatchplanGenerator(teams);
            testGroupstage(generator, teams);
            assertEquals(generator.generateMatchesKnockout().size(), teams.size() / 2);
            assertEquals(generator.generateSemiFinal().size(), 2);
        }
    }

    private boolean noMatchAgainstTeamFromOtherGroup(Match m, List<String> groupA, List<String> groupB) {
        if (groupA.contains(m.getTeamname2())) {
            return groupA.contains(m.getTeamname1());
        } else {
            return groupB.contains(m.getTeamname1());
        }
    }


}
