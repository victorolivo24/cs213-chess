package chess;

/**
 * Side to move / ownership of a piece.
 */
public enum Color {
    WHITE,
    BLACK;

    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}
