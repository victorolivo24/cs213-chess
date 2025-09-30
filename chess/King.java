package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates all one-square king moves. Castling is handled separately inside Game because it
 * depends on board state beyond pure geometry.
 */
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
                    continue; // skip staying in place
                }
                int file = from.file() + df;
                int rank = from.rank() + dr;
                if (!inBounds(file, rank)) {
                    continue; // off board
                }
                Piece occupant = position.pieceAt(file, rank);
                if (isFriendly(occupant)) {
                    continue; // cannot capture own pieces
                }
                moves.add(Move.of(from, square(file, rank)));
            }
        }
        return moves;
    }
}
