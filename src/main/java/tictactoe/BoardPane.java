package tictactoe;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoardPane extends GridPane {

    private final Game       game;
    private final Button[][] cells = new Button[Game.SIZE][Game.SIZE];

    public BoardPane(Game game) {
        this.game = game;
        getStyleClass().add("board");

        for (int r = 0; r < Game.SIZE; r++) {
            for (int c = 0; c < Game.SIZE; c++) {
                final int row = r, col = c;
                Button cell = new Button();
                cell.getStyleClass().add("cell");
                cell.setPrefSize(110, 110);
                cell.setOnAction(e -> game.play(row, col));
                cells[r][c] = cell;
                add(cell, c, r);
            }
        }

        game.currentPlayerProperty().addListener((o, a, b) -> refresh());
        game.stateProperty().addListener((o, a, b) -> refresh());
        refresh();
    }

    public void refresh() {
        for (int r = 0; r < Game.SIZE; r++) {
            for (int c = 0; c < Game.SIZE; c++) {
                Player p   = game.at(r, c);
                Button btn = cells[r][c];

                btn.setText(p == Player.NONE ? "" : p.name());
                btn.getStyleClass().removeAll("cell-x", "cell-o");
                if (p == Player.X) btn.getStyleClass().add("cell-x");
                if (p == Player.O) btn.getStyleClass().add("cell-o");

                btn.setDisable(p != Player.NONE
                        || game.stateProperty().get() != GameState.RUNNING);
            }
        }
    }
}
