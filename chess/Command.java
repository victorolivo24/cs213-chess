package chess;

/**
 * Sealed hierarchy describing what the player requested on a turn.
 * <p>
 *  - {@link MoveCommand}: a normal move with no attached draw offer.
 *  - {@link MoveWithDrawOffer}: same as above, but the mover also offers a draw.
 *  - {@link ResignCommand}: the active player resigns the game immediately.
 */
public sealed interface Command permits Command.MoveCommand, Command.MoveWithDrawOffer, Command.ResignCommand {

    /**
     * Normal move command (no draw offer).
     */
    record MoveCommand(Move move) implements Command {
        public MoveCommand {
            if (move == null) {
                throw new IllegalArgumentException("move");
            }
        }
    }

    /**
     * Move command where the player also offers a draw.
     */
    record MoveWithDrawOffer(Move move) implements Command {
        public MoveWithDrawOffer {
            if (move == null) {
                throw new IllegalArgumentException("move");
            }
        }
    }

    /**
     * Command indicating the mover resigns immediately.
     */
    record ResignCommand() implements Command { }
}
