package de.gaustadtasv.turnierorganisator.configuration;

import de.gaustadtasv.turnierorganisator.View;
import lombok.Getter;

import java.util.List;

@Getter
public enum Modus {
    ROUND_ROBIN("Round Robin", List.of(4, 5, 7), View.EXECUTION_ROUND_ROBIN),
    GROUP_WITHOUT_KO("Gruppen ohne KO Phase", List.of(7, 8, 9, 10), View.EXECUTION_GROUPS),
    GROUP_WITH_KO("Gruppen mit KO Phase ohne Halbfinale", List.of(8, 10), View.EXECUTION_KNOCKOUT),
    GROUP_WITH_KO_SEMI_FINAL("Gruppen mit KO Phase mit Halbfinale", List.of(8, 10), View.EXECUTION_KNOCKOUT);

    private final String printableOption;
    private final List<Integer> allowedNubmerOfTeams;
    private final View executionView;

    Modus(String printableOption, List<Integer> allowedNubmerOfTeams, View executionView) {
        this.printableOption = printableOption;
        this.allowedNubmerOfTeams = allowedNubmerOfTeams;
        this.executionView = executionView;
    }

    public static Modus modusFromPrintableOption(String option) {
        for (Modus m : Modus.values()) {
            if (m.getPrintableOption().equals(option)) {
                return m;
            }
        }
        throw new IllegalArgumentException("No Modus found for value");
    }

    public boolean modusAllowedForNumberOfTeams(int numberOfTeams) {
        return allowedNubmerOfTeams.contains(numberOfTeams);
    }

}
