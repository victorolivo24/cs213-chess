package chess;

public class Chess {

    enum Player { white, black }

    private static Game currentGame = new Game();

    /**
     * Plays the next move for whichever player has the turn.
     *
     * @param move String for next move, e.g. "a2 a3"
     *
     * @return A ReturnPlay instance that contains the result of the move.
     */
    public static ReturnPlay play(String move) {
        ensureGame();
        try {
            Command command = MoveParser.parse(move);
            return currentGame.apply(command);
        } catch (IllegalArgumentException ex) {
            return currentGame.snapshotWithMessage(ReturnPlay.Message.ILLEGAL_MOVE);
        }
    }

    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        currentGame = new Game();
    }

    private static void ensureGame() {
        if (currentGame == null) {
            currentGame = new Game();
        }
    }
}
