package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawn handles forward pushes and diagonal captures. En passant and promotion will be layered on
 * by the Game once it knows the surrounding context.
 */
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

        // Single push straight ahead if empty.
        int forwardRank = rank + direction;
        if (inBounds(file, forwardRank) && position.isEmpty(file, forwardRank)) {
            moves.add(Move.of(from, square(file, forwardRank)));

            // Double push from starting rank if both squares are empty.
            int startingRank = color() == Color.WHITE ? 1 : 6;
            int doubleRank = rank + (2 * direction);
            if (rank == startingRank && inBounds(file, doubleRank) && position.isEmpty(file, doubleRank)) {
                moves.add(Move.of(from, square(file, doubleRank)));
            }
        }

        // Captures diagonally forward.
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
