package chess;

/**
 * Enumerates the six chess piece types and exposes metadata useful during move handling.
 */
public enum PieceType {
    KING('K'),
    QUEEN('Q'),
    ROOK('R'),
    BISHOP('B'),
    KNIGHT('N'),
    PAWN('P');

    private final char algebraic;

    PieceType(char algebraic) {
        this.algebraic = algebraic;
    }

    public char algebraicSymbol() {
        return algebraic;
    }

    /**
     * Used by promotion parsing to restrict the allowable upgrade targets.
     */
    public boolean isPromotableTarget() {
        return this != KING && this != PAWN;
    }
}
