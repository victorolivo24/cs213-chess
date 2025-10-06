package chess;

public class Pawn extends Piece {

    //defining pawn's color and type
    public Pawn (Color color)
    {
        super(color, PieceType.PAWN);
    }

    public boolean isValidMove (Board board, Square from, Square to)
    {
        //if the pawn doesn't move at all = invalid
        if (from.equals(to)) return false;

        //calculates how far the pawn moves
        int df= to.file() - from.file(); //file difference (horizonalzal)
        int dr= to.rank() - from.rank(); // rank difference (vertical)

        //determines which direction the pawn is supposed to move
        //white move up (+1), black moves down (-1)
        int dir= (color == Color.WHITE) ? +1 : -1;


        //pawn moves one step forward
        if (df == 0 && dr ==dir)
        {
            return true;
        }


        //pawn moves  two steps forward from its starting rank
        boolean onStart = (color == Color.WHITE) ? (from.rank() == 1) : (from.rank() == 6);
        if (df == 0 && dr  == 2 * dir && onStart)
        {
            return true;
        }


        //pawn moves diagonally by one square
        if (Math.abs(df) == 1 && dr == dir)
        {
            return true;
        }

        //anything else= moving backward, sideways, or too far is invalid
        return false;
    }
    
}
