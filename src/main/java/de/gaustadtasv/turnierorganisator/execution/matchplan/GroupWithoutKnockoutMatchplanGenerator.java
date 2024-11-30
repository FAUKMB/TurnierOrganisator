package de.gaustadtasv.turnierorganisator.execution.matchplan;

import java.util.ArrayList;
import java.util.List;

public class GroupWithoutKnockoutMatchplanGenerator {
    protected List<String> teamnames;

    protected GroupWithoutKnockoutMatchplanGenerator(List<String> teamnames) {
        this.teamnames = teamnames;
    }

    protected List<Match> group(List<String> teamnames, String groupName) {
        return switch (teamnames.size()) {
            case 3 -> group3(teamnames, groupName);
            case 4 -> group4(teamnames, groupName);
            case 5 -> group5(teamnames, groupName);
            default -> throw new IllegalStateException("Unexpected value: " + teamnames.size());
        };
    }

    private List<Match> group3(List<String> teamnames, String groupName) {
        return List.of(
                new Match(teamnames.get(0), teamnames.get(1), groupName),
                new Match(teamnames.get(1), teamnames.get(2), groupName),
                new Match(teamnames.get(2), teamnames.get(0), groupName)
        );
    }

    private List<Match> group4(List<String> teamnames, String groupName) {
        return List.of(
                new Match(teamnames.get(0), teamnames.get(1), groupName),
                new Match(teamnames.get(2), teamnames.get(3), groupName),
                new Match(teamnames.get(3), teamnames.get(0), groupName),
                new Match(teamnames.get(1), teamnames.get(2), groupName),
                new Match(teamnames.get(2), teamnames.get(0), groupName),
                new Match(teamnames.get(1), teamnames.get(3), groupName)
        );
    }

    public List<String> teamnamesGroupA() {
        return teamnames.subList(0, teamnames.size() / 2 + teamnames.size() % 2);
    }

    public List<String> teamnamesGroupB() {
        return teamnames.subList(teamnames.size() / 2 + teamnames.size() % 2, teamnames.size());
    }

    private List<Match> group5(List<String> teamnames, String groupName) {
        return List.of(
                new Match(teamnames.get(0), teamnames.get(1), groupName),
                new Match(teamnames.get(3), teamnames.get(2), groupName),
                new Match(teamnames.get(4), teamnames.get(0), groupName),
                new Match(teamnames.get(2), teamnames.get(1), groupName),
                new Match(teamnames.get(3), teamnames.get(4), groupName),
                new Match(teamnames.get(2), teamnames.get(0), groupName),
                new Match(teamnames.get(1), teamnames.get(3), groupName),
                new Match(teamnames.get(4), teamnames.get(2), groupName),
                new Match(teamnames.get(0), teamnames.get(3), groupName),
                new Match(teamnames.get(1), teamnames.get(4), groupName)
        );
    }

    private List<Match> reverseGroup(List<Match> matches) {
        return matches.stream().map(m -> new Match(m.getTeamname2(), m.getTeamname1(), m.getTitle())).toList();
    }

    protected List<Match> generateMatchesGroup() {
        return switch (teamnames.size()) {
            case 7 -> seven();
            case 8, 10 -> equalGroups();
            case 9 -> nine();
            default -> throw new IllegalStateException("Unexpected value: " + teamnames.size());
        };
    }

    private List<Match> nine() {
        List<Match> groupMatches = new ArrayList<>();
        List<Match> groupA = group(teamnamesGroupA(), "Gruppe A");
        List<Match> groupB = group(teamnamesGroupB(), "Gruppe B");
        groupMatches.add(groupA.get(0));
        groupMatches.add(groupA.get(1));
        groupMatches.add(groupB.get(0));
        groupMatches.add(groupB.get(1));
        groupMatches.add(groupA.get(2));
        groupMatches.add(groupA.get(3));
        groupMatches.add(groupB.get(2));
        groupMatches.add(groupB.get(3));
        groupMatches.add(groupA.get(4));
        groupMatches.add(groupA.get(5));
        groupMatches.add(groupA.get(6));
        groupMatches.add(groupA.get(7));
        groupMatches.add(groupB.get(4));
        groupMatches.add(groupB.get(5));
        groupMatches.add(groupA.get(8));
        groupMatches.add(groupA.get(9));
        return groupMatches;
    }

    private List<Match> equalGroups() {
        List<Match> groupA = group(teamnamesGroupA(), "Gruppe A");
        List<Match> groupB = group(teamnamesGroupB(), "Gruppe B");
        return zipInPairs(groupA, groupB);
    }

    private List<Match> zipInPairs(List<Match> list1, List<Match> list2) {
        List<Match> resultList = new ArrayList<>();
        for (int i = 0; i < list1.size(); i += 2) {
            resultList.add(list1.get(i));
            resultList.add(list1.get(i + 1));
            resultList.add(list2.get(i));
            resultList.add(list2.get(i + 1));
        }
        return resultList;
    }

    private List<Match> seven() {
        List<Match> groupMatches = new ArrayList<>();
        List<Match> groupA = group(teamnamesGroupA(), "Gruppe A");
        List<Match> groupB = group(teamnamesGroupB(), "Gruppe B");
        for (int i = 0; i < groupB.size(); i++) {
            groupMatches.add(groupA.get(i * 2));
            groupMatches.add(groupA.get(i * 2 + 1));
            groupMatches.add(groupB.get(i));
        }
        return groupMatches;
    }


    protected List<Match> generateSemiFinal() {
        return List.of();
    }

    protected List<Match> generateMatchesKnockout() {
        return List.of();
    }

}
