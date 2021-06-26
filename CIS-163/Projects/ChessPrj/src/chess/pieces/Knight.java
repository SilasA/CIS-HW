/***********************************************************************
 * @see chess.pieces.ChessPiece
 *
 * @author Silas Agnew
 * @version March 16, 2018
 **********************************************************************/

package chess.pieces;

import W18Project3.IChessPiece;
import W18Project3.Move;
import W18Project3.Player;

public class Knight extends ChessPiece
{
    /*******************************************************************
     *
     * @param player
     ******************************************************************/
    public Knight(Player player)
    {
        super(player, SCORE_KNIGHT);
    }

    /*******************************************************************
     * @return The type of piece
     * @see IChessPiece#type()
     ******************************************************************/
    @Override
    public String type() { return "Knight"; }

    /*******************************************************************
     * Checks whether or not {@code move} on the current state {@code board}
     * is a valid move for the knight piece.
     *
     * Movement:
     *  -Distance:  3 tile
     *  -Direction: 2 tile -> 1 tile
     *              1 tile -> 2 tile
     *  -Jumps pieces
     *
     * @param move The move to check if valid
     * @param board The current board to test the validity of the move
     * @return Validity
     * @see IChessPiece#isValidMove(Move, IChessPiece[][])
     ******************************************************************/
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board)
    {
        // Check general conditions
        if (!super.isValidMove(move, board)) return false;

        // Check slope
        int dy = move.toRow - move.fromRow;
        int dx = move.toColumn - move.fromColumn;

        if (dx == 0) return false;
        if (Math.abs(dy / dx) != 2 && Math.abs(dy / (double)dx) != 0.5)
            return false;

        // Check for distance
        // No line with slope 2 or 1/2 can go further than allowed without
        // exceeding +-3
        if (Math.abs(dy) + Math.abs(dx) > 3) return false;
        return true;
    }
}
