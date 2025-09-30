package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Queen combines rook and bishop movement, sliding any distance until blocked.
 */
public final class Queen extends Piece {
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1},
        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public List<Move> generatePseudoLegalMoves(PositionView position, Square from) {
        List<Move> moves = new ArrayList<>(27);
        for (int[] d : DIRECTIONS) {
            collectSlidingMoves(position, from, moves, d[0], d[1]);
        }
        return moves;
    }

    /**
     * Walks step-by-step in the supplied direction until we run off the board or get blocked.
     */
    private void collectSlidingMoves(PositionView position, Square from, List<Move> moves, int df, int dr) {
        int file = from.file() + df;
        int rank = from.rank() + dr;
        while (inBounds(file, rank)) {
            Piece occupant = position.pieceAt(file, rank);
            if (isFriendly(occupant)) {
                return; // friendly piece stops the ray without producing a move
            }
            moves.add(Move.of(from, square(file, rank)));
            if (occupant != null) {
                return; // enemy piece captured; nothing beyond it
            }
            file += df;
            rank += dr;
        }
    }
}
