package chess;

import java.util.ArrayList;

/**
 * Central rules engine that will manage the evolving game:
 * <ul>
 *   <li>Initialize the board via {@link Board#clear()} and {@link Board#setPiece(Square, Piece)}.</li>
 *   <li>Track side to move, castling rights, and en passant availability.</li>
 *   <li>Apply parsed {@link Command}s using {@link Move}, {@link Square}, and the piece hierarchy.</li>
 *   <li>Detect special outcomes (check, checkmate, draw, resign).</li>
 *   <li>Export the position as {@link ReturnPlay} snapshots for the CLI/autograder.</li>
 * </ul>
 * The concrete move handling is still to be implemented.
 */
public class Game {

    public Game() {
        // TODO: set up initial board state when engine implementation begins.
    }

    /**
     * Applies the given command to the game state and returns the resulting board/message bundle.
     */
    public ReturnPlay apply(Command command) {
        throw new UnsupportedOperationException("Game logic not implemented yet");
    }

    /**
     * Helper used by Chess when parsing fails: report an illegal move without altering state.
     * Once board export exists, this will return the true piece layout.
     */
    public ReturnPlay snapshotWithMessage(ReturnPlay.Message message) {
        ReturnPlay result = new ReturnPlay();
        result.message = message;
        result.piecesOnBoard = new ArrayList<>();
        return result;
    }
}
