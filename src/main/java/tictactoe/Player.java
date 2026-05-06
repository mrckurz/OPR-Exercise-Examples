package tictactoe;

public enum Player {
    X, O, NONE;

    public Player opponent() {
        if (this == X) return O;
        if (this == O) return X;
        return NONE;
    }
}
