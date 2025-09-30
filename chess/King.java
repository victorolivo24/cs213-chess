package chess;

import java.util.ArrayList;
import java.util.List;

public final class King extends Piece {
    public King(Color color) {
        super(color, PieceType.KING);
    }

    @Override
    public List<Move> generatePseudoLegalMoves(PositionView position, Square from) {
        List<Move> moves = new ArrayList<>(8);
        for (int df = -1; df <= 1; df++) {
            for (int dr = -1; dr <= 1; dr++) {
                if (df == 0 && dr == 0) {
                    continue;
                }
                int file = from.file() + df;
                int rank = from.rank() + dr;
                if (!inBounds(file, rank)) {
                    continue;
                }
                Piece occupant = position.pieceAt(file, rank);
                if (isFriendly(occupant)) {
                    continue;
                }
                moves.add(Move.of(from, square(file, rank)));
            }
        }
        return moves;
    }
}
