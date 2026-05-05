package tictactoe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatusBarController {

    @FXML private Label currentPlayerLabel;
    @FXML private Label statusLabel;

    public void bindTo(Game game) {
        currentPlayerLabel.textProperty().bind(
            Bindings.createStringBinding(
                () -> "Am Zug: " + game.currentPlayerProperty().get(),
                game.currentPlayerProperty()
            )
        );

        statusLabel.textProperty().bind(
            Bindings.createStringBinding(
                () -> switch (game.stateProperty().get()) {
                    case RUNNING -> "läuft";
                    case WON     -> game.winnerProperty().get() + " gewinnt!";
                    case DRAW    -> "Unentschieden";
                },
                game.stateProperty(), game.winnerProperty()
            )
        );
    }
}
