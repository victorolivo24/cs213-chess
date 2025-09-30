package chess;

/**
 * Types of chess pieces tracked throughout the engine.
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

    public boolean isPromotableTarget() {
        return this != KING && this != PAWN;
    }
}
