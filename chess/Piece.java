package chess;

import java.util.List;
import java.util.Objects;

/**
 * Base class for every chess piece. Subclasses provide their geometry via
 * {@link #generatePseudoLegalMoves(PositionView, Square)} while this class keeps track of color/type
 * and exposes helpers commonly needed by all pieces.
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

    /**
     * Generates every move allowed by the piece's geometry without considering king safety.
     * The Game class will filter these pseudo-legal moves to remove ones that leave the king in check.
     */
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

    /**
     * Read-only view of a board position. Board and its snapshots will implement this so pieces can
     * ask what occupies a square while remaining decoupled from the concrete storage.
     */
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
