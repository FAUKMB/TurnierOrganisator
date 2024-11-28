package de.gaustadtasv.turnierorganisator.execution;

import de.gaustadtasv.turnierorganisator.execution.matchplan.MatchLine;
import de.gaustadtasv.turnierorganisator.execution.table.GroupTable;
import de.gaustadtasv.turnierorganisator.execution.table.TableCalculator;
import de.gaustadtasv.turnierorganisator.execution.table.TableLineWithPoints;
import de.gaustadtasv.turnierorganisator.execution.table.TableLineWithoutPoints;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class ExecutionKnockoutController extends ExecutionController {

    public TableView<TableLineWithPoints> tableA;
    public TableView<TableLineWithPoints> tableB;
    public TableView<TableLineWithoutPoints> tableGesamt;

    @Override
    protected void doTabelleBerechnen() {
        TurnierExecutionState state = TurnierExecutionState.getInstance();
        List<GroupTable> groupTables = calculateGroupTables(state);
        tableA.setItems(FXCollections.observableArrayList(groupTables.get(0).tableLines()));
        tableB.setItems(FXCollections.observableArrayList(groupTables.get(1).tableLines()));
        state.getMatchplan().update(groupTables.get(0), groupTables.get(1));

        for (MatchLine matchLine : matchLines) {
            matchLine.updateMatchline();
        }

        List<String> allTeams = TableCalculator.calculateTableGesamtKnockout(state.getMatchplan());
        List<TableLineWithoutPoints> tableLinesGesamt = mapTeamlistToTableLinesWithoutPoints(allTeams);
        tableGesamt.setItems(FXCollections.observableArrayList(tableLinesGesamt));
    }

    private static List<TableLineWithoutPoints> mapTeamlistToTableLinesWithoutPoints(List<String> teamList) {
        List<TableLineWithoutPoints> tableLines = new ArrayList<>();

        for (int i = 0; i < teamList.size(); i++) {
            tableLines.add(new TableLineWithoutPoints(i + 1, teamList.get(i)));
        }
        return tableLines;
    }


}
