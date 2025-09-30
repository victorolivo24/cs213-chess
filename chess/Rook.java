package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Rook slides horizontally and vertically until it hits the edge or a blocking piece.
 */
public final class Rook extends Piece {
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public Rook(Color color) {
        super(color, PieceType.ROOK);
    }

    @Override
    public List<Move> generatePseudoLegalMoves(PositionView position, Square from) {
        List<Move> moves = new ArrayList<>(14);
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
                return; // rook cannot capture its own piece and cannot move past it
            }
            moves.add(Move.of(from, square(file, rank)));
            if (occupant != null) {
                return; // capture terminates the ray
            }
            file += df;
            rank += dr;
        }
    }
}
