package de.gaustadtasv.turnierorganisator.execution.matchplan;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.Data;

@Data
public class MatchLine {
    private HBox line = new HBox();
    private TextField result;
    private Match match;

    public MatchLine(Match match) {
        this.match = match;
        Label description = new Label();
        description.setText(printableMatchlineString());
        result = new TextField();
        line.getChildren().addAll(description, result);
    }

    public void updateMatchline() {
        Label description = (Label) line.getChildren().getFirst();
        description.setText(printableMatchlineString());
    }

    public void setResultText(String resultText) {
        result.setText(resultText);
    }

    private String printableMatchlineString() {
        return match.getTitle() + "\t" + match.getTeamname1() + " - " + match.getTeamname2();
    }

    public void parseResultToMatch() {
        String resultString = result.getText();
        if (!resultString.isEmpty()) {
            match.parseResult(resultString);
        } else {
            match.setMatchPlayed(false);
        }
    }

}
