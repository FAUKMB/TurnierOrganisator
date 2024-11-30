package de.gaustadtasv.turnierorganisator.execution;

import de.gaustadtasv.turnierorganisator.execution.table.GroupTable;
import de.gaustadtasv.turnierorganisator.execution.table.TableLineWithPoints;
import de.gaustadtasv.turnierorganisator.persistence.TxtFileExporter;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

import java.util.List;

public class ExecutionWithoutKnockoutController extends ExecutionController {
    public TableView<TableLineWithPoints> tableA;
    public TableView<TableLineWithPoints> tableB;


    @Override
    protected void doTabelleBerechnen() {
        TurnierExecutionState state = TurnierExecutionState.getInstance();
        List<GroupTable> groupTables = calculateGroupTables(state);
        tableA.setItems(FXCollections.observableArrayList(groupTables.get(0).tableLines()));
        tableB.setItems(FXCollections.observableArrayList(groupTables.get(1).tableLines()));
    }

    @Override
    protected void exportTablesToTxt(TurnierExecutionState state, TxtFileExporter fileExporter) {
        exportGroupTables(state, fileExporter);
    }
}
