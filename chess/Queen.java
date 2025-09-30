package chess;

import java.util.ArrayList;
import java.util.List;

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
            addSlide(position, from, moves, d[0], d[1]);
        }
        return moves;
    }

    private void addSlide(PositionView position, Square from, List<Move> moves, int df, int dr) {
        int file = from.file() + df;
        int rank = from.rank() + dr;
        while (inBounds(file, rank)) {
            Piece occupant = position.pieceAt(file, rank);
            if (isFriendly(occupant)) {
                return;
            }
            moves.add(Move.of(from, square(file, rank)));
            if (occupant != null) {
                return;
            }
            file += df;
            rank += dr;
        }
    }
}
