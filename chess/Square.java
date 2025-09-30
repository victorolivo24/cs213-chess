package chess;

import java.util.Objects;

/**
 * Immutable representation of a board coordinate expressed as file (column) and rank (row).
 * <p>
 * Internally we keep zero-based indices (0..7) so math is easy, but helper methods expose the
 * algebraic chess notation (e.g., "e2"). This class is the single source of truth for translating
 * between indices and human-readable strings.
 */
public final class Square {
    private final int file; // 0..7 (a..h)
    private final int rank; // 0..7 (1..8)

    private Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    /**
     * Factory for zero-based indices used internally by the engine.
     */
    public static Square of(int fileIndex, int rankIndex) {
        validateRange(fileIndex, rankIndex);
        return new Square(fileIndex, rankIndex);
    }

    /**
     * Parses an algebraic coordinate such as "e2" into a {@link Square} instance.
     */
    public static Square fromAlgebraic(String notation) {
        Objects.requireNonNull(notation, "notation");
        if (notation.length() != 2) {
            throw new IllegalArgumentException("Square notation must be two characters: " + notation);
        }
        char fileChar = notation.charAt(0);
        char rankChar = notation.charAt(1);
        if (fileChar < 'a' || fileChar > 'h') {
            throw new IllegalArgumentException("File must be a-h: " + notation);
        }
        if (rankChar < '1' || rankChar > '8') {
            throw new IllegalArgumentException("Rank must be 1-8: " + notation);
        }
        int fileIndex = fileChar - 'a';
        int rankIndex = rankChar - '1';
        return new Square(fileIndex, rankIndex);
    }

    public int file() {
        return file;
    }

    public int rank() {
        return rank;
    }

    /**
     * Returns the algebraic file letter, e.g., 0 -> 'a'.
     */
    public char fileChar() {
        return (char) ('a' + file);
    }

    /**
     * Returns the human-facing rank number, e.g., 0 -> 1.
     */
    public int rankNumber() {
        return rank + 1;
    }

    /**
     * Converts the coordinate back into algebraic format (e.g., "e2").
     */
    public String toAlgebraic() {
        return "" + fileChar() + rankNumber();
    }

    /**
     * Guard utility shared by the factory methods to keep coordinates on the board.
     */
    private static void validateRange(int fileIndex, int rankIndex) {
        if (fileIndex < 0 || fileIndex > 7 || rankIndex < 0 || rankIndex > 7) {
            throw new IllegalArgumentException("File and rank must be in 0..7: " + fileIndex + "," + rankIndex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Square other)) {
            return false;
        }
        return file == other.file && rank == other.rank;
    }

    @Override
    public int hashCode() {
        return 31 * file + rank;
    }

    @Override
    public String toString() {
        return toAlgebraic();
    }
}
