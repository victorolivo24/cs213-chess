package chess;

import java.util.Objects;

/**
 * Immutable chess move from one square to another with an optional promotion target.
 * <p>
 * Pseudo-legal move generation will create these objects, and the Game layer will later decide
 * whether a move is legal (e.g., king safety) or whether a promotion is valid.
 */
public final class Move {
    private final Square from;
    private final Square to;
    private final PieceType promotion;

    private Move(Square from, Square to, PieceType promotion) {
        this.from = Objects.requireNonNull(from, "from");
        this.to = Objects.requireNonNull(to, "to");
        this.promotion = promotion;
    }

    /**
     * Basic move without promotion.
     */
    public static Move of(Square from, Square to) {
        return new Move(from, to, null);
    }

    /**
     * Promotion move constructor, enforcing that only non-king, non-pawn targets are allowed.
     */
    public static Move promotion(Square from, Square to, PieceType promotion) {
        if (promotion == null) {
            throw new IllegalArgumentException("Promotion piece type is required for promotion moves");
        }
        if (!promotion.isPromotableTarget()) {
            throw new IllegalArgumentException("Invalid promotion target: " + promotion);
        }
        return new Move(from, to, promotion);
    }

    public Square from() {
        return from;
    }

    public Square to() {
        return to;
    }

    public PieceType promotion() {
        return promotion;
    }

    public boolean isPromotion() {
        return promotion != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Move other)) {
            return false;
        }
        return from.equals(other.from) && to.equals(other.to) && promotion == other.promotion;
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + (promotion != null ? promotion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return from + "->" + to + (promotion != null ? "=" + promotion : "");
    }
}
