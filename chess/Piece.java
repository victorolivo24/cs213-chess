package chess;

public abstract class Piece {
    protected Color color;
    protected PieceType type;

    public Piece( Color color, PieceType type)
    {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getType()
    {
        return type;
    }

    public abstract boolean isValidMove(Board board, Square from, Square to);

    @Override
    public String toString() {
        String prefix = color == Color.WHITE ? "w" : "b";
        switch (type) {
            case KING: return prefix + "K";
            case QUEEN: return prefix + "Q";
            case ROOK: return prefix + "R";
            case BISHOP: return prefix + "B";
            case KNIGHT: return prefix + "K";
            case PAWN: return prefix + "P";
            default: return prefix + "?";
        }
    }
}
