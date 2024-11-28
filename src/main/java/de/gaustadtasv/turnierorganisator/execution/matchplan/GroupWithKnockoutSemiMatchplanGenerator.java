package de.gaustadtasv.turnierorganisator.execution.matchplan;

import java.util.List;

public class GroupWithKnockoutSemiMatchplanGenerator extends GroupWithKnockoutMatchplanGenerator {
    protected GroupWithKnockoutSemiMatchplanGenerator(List<String> teamnames) {
        super(teamnames);
    }

    @Override
    protected List<Match> generateSemiFinal() {
        return List.of(new Match("TBD", "TBD", "Halbfinale 1"), new Match("TBD", "TBD", "Halbfinale 2"));
    }

}
