package chess;

import java.util.ArrayList;
import java.util.List;

public final class Knight extends Piece {
    private static final int[][] DELTAS = {
        {1, 2}, {2, 1}, {-1, 2}, {-2, 1},
        {1, -2}, {2, -1}, {-1, -2}, {-2, -1}
    };

    public Knight(Color color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public List<Move> generatePseudoLegalMoves(PositionView position, Square from) {
        List<Move> moves = new ArrayList<>(8);
        for (int[] d : DELTAS) {
            int file = from.file() + d[0];
            int rank = from.rank() + d[1];
            if (!inBounds(file, rank)) {
                continue;
            }
            Piece occupant = position.pieceAt(file, rank);
            if (isFriendly(occupant)) {
                continue;
            }
            moves.add(Move.of(from, square(file, rank)));
        }
        return moves;
    }
}
