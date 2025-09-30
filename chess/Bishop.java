package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Bishop slides diagonally across the board until it encounters an obstacle.
 */
public final class Bishop extends Piece {
    private static final int[][] DIRECTIONS = {
        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public List<Move> generatePseudoLegalMoves(PositionView position, Square from) {
        List<Move> moves = new ArrayList<>(13);
        for (int[] d : DIRECTIONS) {
            collectSlidingMoves(position, from, moves, d[0], d[1]);
        }
        return moves;
    }

    private void collectSlidingMoves(PositionView position, Square from, List<Move> moves, int df, int dr) {
        int file = from.file() + df;
        int rank = from.rank() + dr;
        while (inBounds(file, rank)) {
            Piece occupant = position.pieceAt(file, rank);
            if (isFriendly(occupant)) {
                return; // blocked by own piece
            }
            moves.add(Move.of(from, square(file, rank)));
            if (occupant != null) {
                return; // captured an enemy; cannot go farther
            }
            file += df;
            rank += dr;
        }
    }
}
