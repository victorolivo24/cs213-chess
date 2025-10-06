package chess;

public class Knight extends Piece {
    public Knight (Color color)
    {
        super(color, PieceType.KNIGHT);
    }
    

    public boolean isValidMove (Board board, Square from, Square to)
    {
        if (from.equals(to)) return false;

        int df = Math.abs(to.file() - from.file());
        int dr = Math.abs(to.rank() - from.rank());

        return (df == 2 && dr == 1) || (df == 1 && dr == 2);
        
    }
}
