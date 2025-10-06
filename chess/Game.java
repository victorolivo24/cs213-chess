package chess;

import java.util.ArrayList;

/**
 * TODO: Implement the full rules engine.
 * <p>
 * Game will own the mutable board state, track castling rights, en passant targets, player to move,
 * and expose methods for applying commands (normal move, resign, draw). It also needs helpers for
 * detecting check, checkmate, and illegal moves.
 */
public class Game {

    public Game() {
        // Full initialization will happen when the rules engine is implemented.
    }

    /**
     * Applies the given command to the game state and returns the resulting board/message bundle.
     */
    public ReturnPlay apply(Command command) {
        throw new UnsupportedOperationException("Game logic not implemented yet");
    }

    /**
     * Helper used by Chess when parsing fails: simply report an illegal move while preserving board.
     * Once the board representation exists, this will return the actual pieces.
     */
    public ReturnPlay snapshotWithMessage(ReturnPlay.Message message) {
        ReturnPlay result = new ReturnPlay();
        result.message = message;
        result.piecesOnBoard = new ArrayList<>();
        return result;
    }
}
