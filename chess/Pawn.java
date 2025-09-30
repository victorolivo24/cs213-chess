package chess;

import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {
    private static final int[] CAPTURE_DELTAS = {-1, 1};

    public Pawn(Color color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public List<Move> generatePseudoLegalMoves(PositionView position, Square from) {
        List<Move> moves = new ArrayList<>(4);
        int direction = color() == Color.WHITE ? 1 : -1;
        int file = from.file();
        int rank = from.rank();

        // single push
        int forwardRank = rank + direction;
        if (inBounds(file, forwardRank) && position.isEmpty(file, forwardRank)) {
            moves.add(Move.of(from, square(file, forwardRank)));

            int startingRank = color() == Color.WHITE ? 1 : 6;
            int doubleRank = rank + (2 * direction);
            if (rank == startingRank && inBounds(file, doubleRank) && position.isEmpty(file, doubleRank)) {
                moves.add(Move.of(from, square(file, doubleRank)));
            }
        }

        // captures
        for (int df : CAPTURE_DELTAS) {
            int targetFile = file + df;
            int targetRank = rank + direction;
            if (!inBounds(targetFile, targetRank)) {
                continue;
            }
            Piece occupant = position.pieceAt(targetFile, targetRank);
            if (isOpponent(occupant)) {
                moves.add(Move.of(from, square(targetFile, targetRank)));
            }
        }

        return moves;
    }
}
