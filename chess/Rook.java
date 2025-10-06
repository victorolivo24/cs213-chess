package chess;

public class Rook extends Piece{
    
    public Rook (Color color)
    {
        super(color, PieceType.ROOK);
    }

    public boolean isValidMove(Board board, Square from, Square to)
    {
        //can't stay in place
        if (from.equals(to)) return false;

        //differences along file (x) and rank (y)
        int df= to.file() - from. file(); //horizontal
        int dr = to.rank() - from.rank(); // vertical

        //rook moves in straight lines only:
        boolean vertical = (df == 0) && (dr !=0);
        boolean horizontal = (dr == 0) && (df != 0);

        return vertical || horizontal;
    }
}
