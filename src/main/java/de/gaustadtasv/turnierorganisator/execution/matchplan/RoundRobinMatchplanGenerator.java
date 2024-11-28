package de.gaustadtasv.turnierorganisator.execution.matchplan;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinMatchplanGenerator extends MatchplanGenerator {
    protected RoundRobinMatchplanGenerator(List<String> teamnames) {
        super(teamnames);
    }

    @Override
    public List<Match> generateMatchesGroup() {
        return switch (teamnames.size()) {
            case 4 -> four();
            case 5 -> five();
            case 7 -> seven();
            default -> throw new IllegalStateException("Unexpected value: " + teamnames.size());
        };
    }

    private List<Match> seven() {
        List<Match> matches = new ArrayList<>();
        matches.add(new Match(teamnames.get(0), teamnames.get(1), ""));
        matches.add(new Match(teamnames.get(2), teamnames.get(3), ""));
        matches.add(new Match(teamnames.get(4), teamnames.get(5), ""));
        matches.add(new Match(teamnames.get(6), teamnames.get(0), ""));
        matches.add(new Match(teamnames.get(1), teamnames.get(2), ""));
        matches.add(new Match(teamnames.get(3), teamnames.get(4), ""));
        matches.add(new Match(teamnames.get(5), teamnames.get(0), ""));
        matches.add(new Match(teamnames.get(3), teamnames.get(6), ""));
        matches.add(new Match(teamnames.get(1), teamnames.get(5), ""));
        matches.add(new Match(teamnames.get(4), teamnames.get(2), ""));
        matches.add(new Match(teamnames.get(0), teamnames.get(3), ""));
        matches.add(new Match(teamnames.get(2), teamnames.get(6), ""));
        matches.add(new Match(teamnames.get(1), teamnames.get(4), ""));
        matches.add(new Match(teamnames.get(6), teamnames.get(5), ""));
        matches.add(new Match(teamnames.get(3), teamnames.get(1), ""));
        matches.add(new Match(teamnames.get(2), teamnames.get(0), ""));
        matches.add(new Match(teamnames.get(5), teamnames.get(3), ""));
        matches.add(new Match(teamnames.get(4), teamnames.get(6), ""));
        matches.add(new Match(teamnames.get(5), teamnames.get(2), ""));
        matches.add(new Match(teamnames.get(0), teamnames.get(4), ""));
        matches.add(new Match(teamnames.get(6), teamnames.get(1), ""));
        return matches;
    }


    private List<Match> five() {
        return group5(teamnames);
    }

    private List<Match> four() {
        return group4(teamnames);
    }
}
