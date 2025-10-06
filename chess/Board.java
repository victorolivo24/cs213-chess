package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Backing store for the 8x8 chess board. Provides both mutation helpers for the Game engine and a
 * read-only {@link Piece.PositionView} so pieces can generate moves.
 */
public final class Board implements Piece.PositionView {
    private final Piece[][] squares = new Piece[8][8];

    public Board() {
    }

    /**
     * Clears the board entirely.
     */
    public void clear() {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                squares[file][rank] = null;
            }
        }
    }

    /**
     * Places a piece on the given square.
     */
    public void setPiece(Square square, Piece piece) {
        squares[square.file()][square.rank()] = piece;
    }

    /**
     * Removes and returns the piece at the given square, or null if empty.
     */
    public Piece removePiece(Square square) {
        int file = square.file();
        int rank = square.rank();
        Piece removed = squares[file][rank];
        squares[file][rank] = null;
        return removed;
    }

    /**
     * Returns the piece at the given square, or null if empty.
     */
    public Piece getPiece(Square square) {
        return squares[square.file()][square.rank()];
    }

    @Override
    public Piece pieceAt(int file, int rank) {
        if (file < 0 || file >= 8 || rank < 0 || rank >= 8) {
            return null;
        }
        return squares[file][rank];
    }

    /**
     * Creates a shallow copy of the board state (pieces are not cloned) for simulation.
     */
    public Board copy() {
        Board clone = new Board();
        for (int file = 0; file < 8; file++) {
            System.arraycopy(squares[file], 0, clone.squares[file], 0, 8);
        }
        return clone;
    }

    /**
     * Lists all pieces currently on the board.
     */
    public List<PieceOnSquare> pieces() {
        List<PieceOnSquare> list = new ArrayList<>();
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Piece piece = squares[file][rank];
                if (piece != null) {
                    list.add(new PieceOnSquare(Square.of(file, rank), piece));
                }
            }
        }
        return list;
    }

    /**
     * Helper record bundling a piece with its location.
     */
    public record PieceOnSquare(Square square, Piece piece) { }
}
