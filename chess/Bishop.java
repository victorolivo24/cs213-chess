package chess;

public class Bishop extends Piece {
    public Bishop (Color color)
    {
        super(color, PieceType.BISHOP);
    }
    

    public boolean isValidMove (Board board, Square from, Square to)
    {
        if (from.equals(to)) return false;

        int df = Math.abs(to.file() - from.file());
        int dr = Math.abs(to.rank() - from.rank());

        return df == dr && df != 0;
        
    }
}
