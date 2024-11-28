package de.gaustadtasv.turnierorganisator.execution;

import de.gaustadtasv.turnierorganisator.configuration.TurnierConfiguration;
import de.gaustadtasv.turnierorganisator.execution.matchplan.Match;
import de.gaustadtasv.turnierorganisator.execution.matchplan.Matchplan;
import lombok.Data;

import java.util.List;

@Data
public class TurnierExecutionState {
    private static TurnierExecutionState state;

    private TurnierConfiguration configuration;
    private Matchplan matchplan;
    private List<Match> loadedMatches;

    public static TurnierExecutionState getInstance() {
        if (state == null) {
            state = new TurnierExecutionState();
        }
        return state;
    }

}
