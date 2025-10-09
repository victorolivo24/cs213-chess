package chess;

import java.util.ArrayList;
import java.util.List;;

/**
 * Central rules engine:
 *  - Holds board state and side-to-move
 *  - Generates legal moves (pseudo-legal filtered by king safety)
 *  - Applies moves (with promotion), resign, and draw?
 *  - Builds ReturnPlay snapshots
 *
 * Castling and en passant are not included in this version.
 */
public class Game {

    private final Board board = new Board();
    private Color toMove = Color.WHITE;
    private boolean gameOver = false;

    public Game() {
        setupInitial();
    }

    /** Set up the initial chess position. */
    private void setupInitial() {
        board.clear();

        // Pawns
        for (int f = 0; f < 8; f++) {
            board.setPiece(Square.of(f, 1), new Pawn(Color.WHITE));
            board.setPiece(Square.of(f, 6), new Pawn(Color.BLACK));
        }

        // Rooks
        board.setPiece(Square.of(0, 0), new Rook(Color.WHITE));
        board.setPiece(Square.of(7, 0), new Rook(Color.WHITE));
        board.setPiece(Square.of(0, 7), new Rook(Color.BLACK));
        board.setPiece(Square.of(7, 7), new Rook(Color.BLACK));

        // Knights
        board.setPiece(Square.of(1, 0), new Knight(Color.WHITE));
        board.setPiece(Square.of(6, 0), new Knight(Color.WHITE));
        board.setPiece(Square.of(1, 7), new Knight(Color.BLACK));
        board.setPiece(Square.of(6, 7), new Knight(Color.BLACK));

        // Bishops
        board.setPiece(Square.of(2, 0), new Bishop(Color.WHITE));
        board.setPiece(Square.of(5, 0), new Bishop(Color.WHITE));
        board.setPiece(Square.of(2, 7), new Bishop(Color.BLACK));
        board.setPiece(Square.of(5, 7), new Bishop(Color.BLACK));

        // Queens on d-file (file = 3)
        board.setPiece(Square.of(3, 0), new Queen(Color.WHITE));
        board.setPiece(Square.of(3, 7), new Queen(Color.BLACK));

        // Kings on e-file (file = 4)
        board.setPiece(Square.of(4, 0), new King(Color.WHITE));
        board.setPiece(Square.of(4, 7), new King(Color.BLACK));

        toMove = Color.WHITE;
        gameOver = false;
    }

    /** Applies a parsed command to the current position and returns the board snapshot + message. */
    public ReturnPlay apply(Command command) {
        if (gameOver) {
            return snapshotWithMessage(ReturnPlay.Message.ILLEGAL_MOVE);
        }

        // Resign immediately ends the game; opponent wins.
        if (command instanceof Command.ResignCommand) {
            gameOver = true;
            return snapshotWithMessage(
                toMove == Color.WHITE ? ReturnPlay.Message.RESIGN_BLACK_WINS
                                      : ReturnPlay.Message.RESIGN_WHITE_WINS
            );
        }

        // Extract move + draw-offer flag
        final Move move;
        final boolean isDrawOffer;
        if (command instanceof Command.MoveWithDrawOffer m) {
            move = m.move();
            isDrawOffer = true;
        } else if (command instanceof Command.MoveCommand m) {
            move = m.move();
            isDrawOffer = false;
        } else {
            return snapshotWithMessage(ReturnPlay.Message.ILLEGAL_MOVE);
        }

        // Validate and apply (with auto-queen on promotion if no piece specified).
        Move legalMove = normalizePromotionIfNeeded(move);
        if (!isLegal(legalMove)) {
            return snapshotWithMessage(ReturnPlay.Message.ILLEGAL_MOVE);
        }
        applyMove(legalMove);

        // Draw offer is auto-accepted after the move (per assignment).
        if (isDrawOffer) {
            gameOver = true;
            return snapshotWithMessage(ReturnPlay.Message.DRAW);
        }

        // Check/checkmate state for the side that is now to move (opponent of the mover).
        Color opp = toMove.opposite();
        boolean oppInCheck = inCheck(opp);
        boolean oppHasLegal = !allLegalMoves(opp).isEmpty();

        ReturnPlay.Message msg = null;
        if (oppInCheck && !oppHasLegal) {
            gameOver = true;
            msg = (opp == Color.WHITE) ? ReturnPlay.Message.CHECKMATE_BLACK_WINS
                                       : ReturnPlay.Message.CHECKMATE_WHITE_WINS;
        } else if (oppInCheck) {
            msg = ReturnPlay.Message.CHECK;
        }

        if (!gameOver) {
            toMove = opp; // switch turns
        }
        return snapshotWithMessage(msg);
    }

    /** Helper for Chess when parsing fails: return ILLEGAL_MOVE with current board snapshot. */
    public ReturnPlay snapshotWithMessage(ReturnPlay.Message message) {
        ArrayList<ReturnPiece> pieces = new ArrayList<>();
        for (Board.PieceOnSquare pos : board.pieces()) {
            ReturnPiece rp = new ReturnPiece();
            rp.pieceFile = ReturnPiece.PieceFile.values()[pos.square().file()];
            rp.pieceRank = pos.square().rank() + 1;
            rp.pieceType = mapType(pos.piece());
            pieces.add(rp);
        }
        ReturnPlay out = new ReturnPlay();
        out.piecesOnBoard = pieces;
        out.message = message;
        return out;
    }

    // ------------------- legality, application, and helpers -------------------

    private Move normalizePromotionIfNeeded(Move move) {
        Piece mover = board.getPiece(move.from());
        if (mover == null || mover.type() != PieceType.PAWN) return move;

        int toRank = move.to().rank();
        boolean reachesLast = (mover.color() == Color.WHITE && toRank == 7)
                           || (mover.color() == Color.BLACK && toRank == 0);

        if (reachesLast && !move.isPromotion()) {
            // Assignment: if no promotion piece indicated, assume queen.
            return Move.promotion(move.from(), move.to(), PieceType.QUEEN);
        }
        return move;
    }

    private boolean isLegal(Move move) {
        // Must move own piece.
        Piece mover = board.getPiece(move.from());
        if (mover == null || mover.color() != toMove) return false;

        // Must match a pseudo-legal move of the mover (by squares; promotion handled above).
        boolean found = false;
        for (Move m : mover.generatePseudoLegalMoves(board, move.from())) {
            if (sameSquares(m, move)) { found = true; break; }
        }
        if (!found) return false;

        // Simulate on a snapshot and verify our own king is not in check after the move.
        Board snap = board.copy();
        // remove destination (capture) then move piece
        snap.removePiece(move.to());
        Piece moved = snap.removePiece(move.from());
        if (moved.type() == PieceType.PAWN && move.isPromotion()) {
            moved = promoted(moved.color(), move.promotion());
        }
        snap.setPiece(move.to(), moved);

        return !inCheck(toMove, snap);
    }

    private void applyMove(Move move) {
        Piece mover = board.getPiece(move.from());
        board.removePiece(move.from());
        if (mover.type() == PieceType.PAWN && move.isPromotion()) {
            mover = promoted(mover.color(), move.promotion());
        }
        board.setPiece(move.to(), mover);
    }

    private static boolean sameSquares(Move a, Move b) {
        return a.from().equals(b.from()) && a.to().equals(b.to());
    }

    private static Piece promoted(Color c, PieceType t) {
        return switch (t) {
            case QUEEN  -> new Queen(c);
            case ROOK   -> new Rook(c);
            case BISHOP -> new Bishop(c);
            case KNIGHT -> new Knight(c);
            default     -> new Queen(c); // fallback
        };
    }

    private boolean inCheck(Color side) {
        return inCheck(side, this.board);
    }

    private boolean inCheck(Color side, Board view) {
        Square k = findKing(side, view);
        if (k == null) return true; // king missing -> treat as in check
        Color opp = side.opposite();

        // If any opponent pseudo-legal move hits the king square, it's check.
        for (Board.PieceOnSquare pos : view.pieces()) {
            Piece p = pos.piece();
            if (p.color() != opp) continue;
            for (Move m : p.generatePseudoLegalMoves(view, pos.square())) {
                if (m.to().equals(k)) return true;
            }
        }
        return false;
    }

    private Square findKing(Color side, Board view) {
        for (Board.PieceOnSquare pos : view.pieces()) {
            Piece p = pos.piece();
            if (p.type() == PieceType.KING && p.color() == side) {
                return pos.square();
            }
        }
        return null;
    }

    private List<Move> allLegalMoves(Color side) {
        ArrayList<Move> out = new ArrayList<>();
        for (Board.PieceOnSquare pos : board.pieces()) {
            Piece p = pos.piece();
            if (p.color() != side) continue;
            for (Move m : p.generatePseudoLegalMoves(board, pos.square())) {
                if (isLegal(m)) out.add(m);
            }
        }
        return out;
    }

    private static ReturnPiece.PieceType mapType(Piece p) {
        boolean white = p.color() == Color.WHITE;
        return switch (p.type()) {
            case KING   -> white ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;
            case QUEEN  -> white ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
            case ROOK   -> white ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
            case BISHOP -> white ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
            case KNIGHT -> white ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
            case PAWN   -> white ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP;
        };
    }
}