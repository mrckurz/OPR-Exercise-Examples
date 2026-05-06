package tictactoe;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;

public class Game {

    public static final int SIZE = 3;

    private final Player[][] board = new Player[SIZE][SIZE];

    private final ObjectProperty<Player>           currentPlayer = new SimpleObjectProperty<>(Player.X);
    private final ReadOnlyObjectWrapper<Player>    winner        = new ReadOnlyObjectWrapper<>(Player.NONE);
    private final ReadOnlyObjectWrapper<GameState> state         = new ReadOnlyObjectWrapper<>(GameState.RUNNING);

    public Game() {
        reset();
    }

    public void reset() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c] = Player.NONE;
            }
        }
        currentPlayer.set(Player.X);
        winner.set(Player.NONE);
        state.set(GameState.RUNNING);
    }

    public boolean play(int row, int col) {
        if (state.get() != GameState.RUNNING)  return false;
        if (board[row][col] != Player.NONE)    return false;

        board[row][col] = currentPlayer.get();

        if (hasWon(currentPlayer.get())) {
            winner.set(currentPlayer.get());
            state.set(GameState.WON);
        } else if (isBoardFull()) {
            state.set(GameState.DRAW);
        } else {
            currentPlayer.set(currentPlayer.get().opponent());
        }
        return true;
    }

    public Player at(int row, int col) {
        return board[row][col];
    }

    public ObjectProperty<Player>           currentPlayerProperty() { return currentPlayer; }
    public ReadOnlyObjectProperty<Player>   winnerProperty()        { return winner.getReadOnlyProperty(); }
    public ReadOnlyObjectProperty<GameState> stateProperty()        { return state.getReadOnlyProperty(); }

    private boolean isBoardFull() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == Player.NONE) return false;
            }
        }
        return true;
    }

    private boolean hasWon(Player p) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) return true;
            if (board[0][i] == p && board[1][i] == p && board[2][i] == p) return true;
        }
        if (board[0][0] == p && board[1][1] == p && board[2][2] == p) return true;
        if (board[0][2] == p && board[1][1] == p && board[2][0] == p) return true;
        return false;
    }
}
