package chess;

public abstract class Piece {
    protected final Color color;
    protected PieceType type;

    public Piece( Color color, PieceType type)
    {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getType()
    {
        return type;
    }

    /**
    *Checking if the move from one square to the other is valid
    *according to this piece's movement rules. 
    *@param board current board state
    *@param from the starting square
    *@param to the target square
    *@return true if the move is valid, false otherwise
    */


    public abstract boolean isValidMove(Board board, Square from, Square to);

    @Override
    public String toString() {
        return color + "_" + type;
        }
    }
