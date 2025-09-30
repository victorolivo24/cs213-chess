package chess;

/**
 * Represents which side a piece belongs to or whose turn it is.
 */
public enum Color {
    WHITE,
    BLACK;

    /**
     * Convenience helper used all over the rules engine for flipping sides.
     */
    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}
