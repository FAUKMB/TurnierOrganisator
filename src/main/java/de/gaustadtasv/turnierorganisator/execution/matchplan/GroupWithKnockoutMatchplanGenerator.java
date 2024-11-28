package de.gaustadtasv.turnierorganisator.execution.matchplan;

import java.util.ArrayList;
import java.util.List;

public class GroupWithKnockoutMatchplanGenerator extends MatchplanGenerator {
    protected GroupWithKnockoutMatchplanGenerator(List<String> teamnames) {
        super(teamnames);
    }

    @Override
    protected List<Match> generateMatchesKnockout() {
        int numberOfKnockoutMatches = teamnames.size() / 2 + teamnames.size() % 2;
        List<Match> knockoutMatches = new ArrayList<>();
        for (int i = 0; i < numberOfKnockoutMatches; i++) {
            knockoutMatches.add(new Match("TBD", "TBD", "Spiel um Platz " + (numberOfKnockoutMatches * 2 - 1 - i * 2)));
        }
        return knockoutMatches;
    }


}
