package de.gaustadtasv.turnierorganisator.persistence;

import de.gaustadtasv.turnierorganisator.configuration.TurnierConfiguration;
import de.gaustadtasv.turnierorganisator.execution.TurnierExecutionState;
import de.gaustadtasv.turnierorganisator.execution.matchplan.Match;
import lombok.Data;

import java.util.List;

@Data
public class PersistenceState {
    private List<Match> matchList;
    private TurnierConfiguration configuration;

    public PersistenceState(TurnierExecutionState state) {
        matchList = state.getMatchplan().matchList();
        configuration = state.getConfiguration();
    }
}
