package chess;

import java.util.List;
import java.util.Objects;

/**
 * Base class for all chess pieces defining color, type, and move generation contract.
 */
public abstract class Piece {
    private final Color color;
    private final PieceType type;

    protected Piece(Color color, PieceType type) {
        this.color = Objects.requireNonNull(color, "color");
        this.type = Objects.requireNonNull(type, "type");
    }

    public Color color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public abstract List<Move> generatePseudoLegalMoves(PositionView position, Square from);

    protected static boolean inBounds(int file, int rank) {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }

    protected static Square square(int file, int rank) {
        return Square.of(file, rank);
    }

    protected boolean isOpponent(Piece other) {
        return other != null && other.color != color;
    }

    protected boolean isFriendly(Piece other) {
        return other != null && other.color == color;
    }

    public interface PositionView {
        Piece pieceAt(int file, int rank);

        default Piece pieceAt(Square square) {
            return pieceAt(square.file(), square.rank());
        }

        default boolean isEmpty(int file, int rank) {
            return pieceAt(file, rank) == null;
        }

        default boolean isOccupied(int file, int rank) {
            return !isEmpty(file, rank);
        }
    }
}
