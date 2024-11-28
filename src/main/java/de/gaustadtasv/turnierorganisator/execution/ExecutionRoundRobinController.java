package de.gaustadtasv.turnierorganisator.execution;

import de.gaustadtasv.turnierorganisator.execution.table.TableCalculator;
import de.gaustadtasv.turnierorganisator.execution.table.TableLineWithPoints;
import de.gaustadtasv.turnierorganisator.execution.table.Team;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

import java.util.List;

public class ExecutionRoundRobinController extends ExecutionController {
    public TableView<TableLineWithPoints> tableGesamt;

    @Override
    protected void doTabelleBerechnen() {
        TurnierExecutionState state = TurnierExecutionState.getInstance();
        List<Team> teams = TableCalculator.calculateTableForGroup(state.getMatchplan().getMatchListGroup(), state.getConfiguration().teams(), true);
        List<TableLineWithPoints> tableLines = mapTeamlistToTableLinesWithPoints(teams);
        tableGesamt.setItems(FXCollections.observableArrayList(tableLines));
    }

}
