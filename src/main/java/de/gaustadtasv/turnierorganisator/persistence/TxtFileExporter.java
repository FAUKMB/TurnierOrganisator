package de.gaustadtasv.turnierorganisator.persistence;

import de.gaustadtasv.turnierorganisator.execution.matchplan.Match;
import de.gaustadtasv.turnierorganisator.execution.matchplan.Matchplan;
import de.gaustadtasv.turnierorganisator.execution.table.Team;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TxtFileExporter {
    private static final Path EXPORT_PATH = Path.of("export");
    private static final Path MATCHPLAN_PATH = Path.of("Spielplan.txt");
    private static final Path TABLE_PATH = Path.of("Tabelle.txt");

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    private final Path turnierName;
    private LocalTime nextMatchTime;
    private final long gameTime;
    private final long pauseTime;

    public TxtFileExporter(String turnierName, long gameTime, long pauseTime, LocalTime startTime) {
        this.turnierName = Path.of(turnierName);
        createExportPathIfNotExists();
        nextMatchTime = startTime;
        this.gameTime = gameTime;
        this.pauseTime = pauseTime;
    }

    private void createExportPathIfNotExists() {
        try {
            Files.createDirectories(EXPORT_PATH.resolve(turnierName));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void exportMatchplanToTxt(Matchplan matchplan) {
        List<Match> matchList = matchplan.matchList();
        Path exportFilePath = EXPORT_PATH.resolve(turnierName).resolve(MATCHPLAN_PATH);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(exportFilePath.toFile()))) {
            bw.write("Spielplan " + turnierName.getFileName().toString());
            bw.newLine();
            for (Match match : matchList) {
                bw.write(exportMatchLine(match));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String exportMatchLine(Match match) {
        String exportLine = nextMatchTime.format(FORMATTER) + " " + match.exportToTxt();
        nextMatchTime = nextMatchTime.plusMinutes(gameTime + pauseTime);
        return exportLine;
    }

    public void exportTableWithPoints(List<Team> table, String tableName) {
        Path exportFilePath = EXPORT_PATH.resolve(turnierName).resolve(TABLE_PATH);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(exportFilePath.toFile(), true))) {
            bw.write(tableName);
            bw.newLine();
            bw.write("Rang Name Tore Punkte");
            bw.newLine();
            for (int i = 1; i <= table.size(); i++) {
                bw.write(tableLineWithPoints(table.get(i - 1), i));
                bw.newLine();
            }
            bw.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String tableLineWithPoints(Team team, int position) {
        return position + ". " + team.getName() + " " + team.getGoals() + ":" + team.getMinusgoals() + " " + team.getPoints();
    }

    public void exportTableWithoutPoints(List<String> teams) {
        Path exportFilePath = EXPORT_PATH.resolve(turnierName).resolve(TABLE_PATH);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(exportFilePath.toFile(), true))) {
            bw.write("Tabelle Gesamt");
            bw.newLine();
            bw.write("Rang Name");
            bw.newLine();
            for (int i = 1; i <= teams.size(); i++) {
                bw.write(i + ". " + teams.get(i - 1));
                bw.newLine();
            }
            bw.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void cleanupExportFiles() {
        Path exportTablePath = EXPORT_PATH.resolve(turnierName).resolve(TABLE_PATH);
        Path exportMatchplanPath = EXPORT_PATH.resolve(turnierName).resolve(MATCHPLAN_PATH);
        try {
            Files.deleteIfExists(exportMatchplanPath);
            Files.deleteIfExists(exportTablePath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


}
