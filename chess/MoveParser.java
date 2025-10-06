package chess;

import java.util.Locale;

/**
 * Translates raw user input (e.g., "e2 e4", "g7 g8 Q", "g1 f3 draw?", "resign") into a
 * {@link Command} understood by the Game engine. Any malformed input triggers an
 * {@link IllegalArgumentException}, allowing the caller to convert it into an illegal move response.
 */
public final class MoveParser {

    private MoveParser() {
        // utility
    }

    /**
     * Parses the supplied input into a {@link Command}.
     *
     * @throws IllegalArgumentException if the input is null, blank, or syntactically invalid.
     */
    public static Command parse(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("input");
        }
        String trimmed = rawInput.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Empty move input");
        }

        if (trimmed.equalsIgnoreCase("resign")) {
            return new Command.ResignCommand();
        }

        boolean drawOffered = false;
        if (trimmed.toLowerCase(Locale.ROOT).endsWith(" draw?")) {
            drawOffered = true;
            trimmed = trimmed.substring(0, trimmed.length() - " draw?".length()).trim();
        }

        String[] tokens = trimmed.split("\s+");
        if (tokens.length < 2 || tokens.length > 3) {
            throw new IllegalArgumentException("Move must be <from> <to> [promo]");
        }

        Square from = Square.fromAlgebraic(tokens[0].toLowerCase(Locale.ROOT));
        Square to = Square.fromAlgebraic(tokens[1].toLowerCase(Locale.ROOT));

        Move move;
        if (tokens.length == 3) {
            PieceType promotionType = parsePromotionToken(tokens[2]);
            move = Move.promotion(from, to, promotionType);
        } else {
            move = Move.of(from, to);
        }

        return drawOffered ? new Command.MoveWithDrawOffer(move) : new Command.MoveCommand(move);
    }

    private static PieceType parsePromotionToken(String token) {
        if (token.length() != 1) {
            throw new IllegalArgumentException("Promotion piece must be one letter: " + token);
        }
        char symbol = Character.toUpperCase(token.charAt(0));
        return switch (symbol) {
            case 'Q' -> PieceType.QUEEN;
            case 'R' -> PieceType.ROOK;
            case 'B' -> PieceType.BISHOP;
            case 'N' -> PieceType.KNIGHT;
            default -> throw new IllegalArgumentException("Invalid promotion piece: " + token);
        };
    }
}
