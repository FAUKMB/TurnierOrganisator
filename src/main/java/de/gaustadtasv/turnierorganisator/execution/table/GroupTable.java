package de.gaustadtasv.turnierorganisator.execution.table;

import java.util.List;

public record GroupTable(List<TableLineWithPoints> tableLines) {
    public String teamFromPosition(int place) {
        return tableLines.get(place - 1).getTeamName();
    }
}
