package de.gaustadtasv.turnierorganisator.execution;

import de.gaustadtasv.turnierorganisator.execution.matchplan.Match;
import de.gaustadtasv.turnierorganisator.execution.matchplan.MatchLine;
import de.gaustadtasv.turnierorganisator.execution.matchplan.Matchplan;
import de.gaustadtasv.turnierorganisator.execution.table.GroupTable;
import de.gaustadtasv.turnierorganisator.execution.table.TableCalculator;
import de.gaustadtasv.turnierorganisator.execution.table.TableLineWithPoints;
import de.gaustadtasv.turnierorganisator.execution.table.Team;
import de.gaustadtasv.turnierorganisator.persistence.StatePersistenceHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ExecutionController {
    public Label headline;
    public VBox matches;
    protected List<MatchLine> matchLines = new ArrayList<>();

    @FXML
    public void initialize() {
        TurnierExecutionState state = TurnierExecutionState.getInstance();
        headline.setText(state.getConfiguration().name());
        initMatches(state);
        loadResults(state);
    }

    private void loadResults(TurnierExecutionState state) {
        List<Match> loadedMatches = state.getLoadedMatches();
        if (loadedMatches == null) {
            return;
        }
        for (int i = 0; i < loadedMatches.size(); i++) {
            if (loadedMatches.get(i).isMatchPlayed()) {
                matchLines.get(i).setResultText(loadedMatches.get(i).getResultText());
            }
        }
        tabelleBerechnenOnClick(null);
    }

    private void initMatches(TurnierExecutionState state) {
        Matchplan matchplan = new Matchplan(state.getConfiguration());
        state.setMatchplan(matchplan);
        matchLines = matchplan.matchList().stream().map(MatchLine::new).toList();
        matches.getChildren().addAll(matchLines.stream().map(MatchLine::getLine).toList());
    }

    protected static List<TableLineWithPoints> mapTeamlistToTableLinesWithPoints(List<Team> teamList) {
        List<TableLineWithPoints> tableLines = new ArrayList<>();

        for (int i = 0; i < teamList.size(); i++) {
            Team team = teamList.get(i);
            tableLines.add(new TableLineWithPoints(i + 1, team.getName(), team.getGoals() + ":" + team.getMinusgoals(), team.getPoints()));
        }
        return tableLines;
    }

    public void tabelleBerechnenOnClick(ActionEvent actionEvent) {
        for (MatchLine matchLine : matchLines) {
            matchLine.parseResultToMatch();
        }
        doTabelleBerechnen();
    }

    protected abstract void doTabelleBerechnen();

    public void saveOnClick(ActionEvent actionEvent) {
        try {
            StatePersistenceHelper.storeStateToFile();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected static List<GroupTable> calculateGroupTables(TurnierExecutionState state) {

        List<String> teamnamesGroupA = state.getMatchplan().teamnamesGroupA();
        List<Team> groupA = TableCalculator.calculateTableForGroup(state.getMatchplan().getMatchListGroup(), teamnamesGroupA, true);
        List<TableLineWithPoints> tableLinesA = mapTeamlistToTableLinesWithPoints(groupA);

        List<String> teamnamesGroupB = state.getMatchplan().teamnamesGroupB();
        List<Team> groupB = TableCalculator.calculateTableForGroup(state.getMatchplan().getMatchListGroup(), teamnamesGroupB, true);
        List<TableLineWithPoints> tableLinesB = mapTeamlistToTableLinesWithPoints(groupB);

        return List.of(new GroupTable(tableLinesA), new GroupTable(tableLinesB));
    }
}
