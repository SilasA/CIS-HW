/***********************************************************************
 * The basis for all chess pieces.
 *
 * Implements general overrides from {@link W18Project3.IChessPiece}.
 * Includes the type of piece and valid move checking.
 *
 * @see W18Project3.IChessPiece
 * @author Silas Agnew
 * @version March 20, 2018
 **********************************************************************/

package chess.pieces;

import W18Project3.IChessPiece;
import W18Project3.Move;
import W18Project3.Player;

import java.util.LinkedList;

public abstract class ChessPiece implements IChessPiece
{
    // Scores for pieces
    public static final int SCORE_KING   = 900;
    public static final int SCORE_QUEEN  = 90;
    public static final int SCORE_KNIGHT = 30;
    public static final int SCORE_BISHOP = 30;
    public static final int SCORE_ROOK   = 50;
    public static final int SCORE_PAWN   = 10;

    private static LinkedList<Move> moveHistory = new LinkedList<>();

    private Player owner;
    private int    valueScore;

    /*******************************************************************
     * @return The last completed move
     ******************************************************************/
    public static Move lastMove()
    {
        return moveHistory.getLast();
    }

    /*******************************************************************
     * Adds a {@code move} to the end of the move history
     * @param move The move to append
     ******************************************************************/
    public static void addMove(Move move)
    {
        moveHistory.addLast(move);
    }

    /*******************************************************************
     * Constructs the basis for a chess piece.
     * @param player Player that owns the piece
     * @param valueScore The value of the piece for AI
     ******************************************************************/
    protected ChessPiece(Player player, int valueScore)
    {
        this.owner = player;
        this.valueScore = valueScore;
    }

    /*******************************************************************
     * @return The owner of the piece
     ******************************************************************/
    public Player player() { return owner; }

    /*******************************************************************
     * @return Returns the value of the piece
     ******************************************************************/
    public int getValueScore() { return valueScore; }

    /*******************************************************************
     * @return The type of piece: Rook, King, etc...
     * @see IChessPiece#type()
     ******************************************************************/
    public abstract String type();

    /*******************************************************************
     * Checks whether or not {@code move} on the current state {@code board}
     * is a valid move for the piece.
     *
     * In the abstract checks:
     *  -If there is a piece on the target tile
     *  -If there is, is it of the opposing color
     *  -The target tile is not the origin tile
     *
     * @param move The move to check if valid
     * @param board The current board to test the validity of the move
     * @return Validity
     * @see IChessPiece#isValidMove(Move, IChessPiece[][])
     ******************************************************************/
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board)
    {
        if (board[move.fromRow][move.fromColumn] == null) return false;
        if (board[move.toRow][move.toColumn] == null)
        {
            return !(move.fromRow == move.toRow && move.fromColumn == move.toColumn);
        }
        else if (board[move.toRow][move.toColumn].player() !=
            board[move.fromRow][move.fromColumn].player())
        {
            return !(move.fromRow == move.toRow && move.fromColumn == move.toColumn);
        }
        return false;
    }
}
