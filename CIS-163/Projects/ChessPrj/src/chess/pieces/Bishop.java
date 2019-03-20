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

public class Bishop extends ChessPiece
{
    /*******************************************************************
     *
     * @param player
     ******************************************************************/
    public Bishop(Player player)
    {
        super(player, SCORE_BISHOP);
    }

    /*******************************************************************
     * @return The type of piece
     * @see IChessPiece#type()
     ******************************************************************/
    @Override
    public String type()
    {
        return "Bishop";
    }

    /*******************************************************************
     * Checks whether or not {@code move} on the current state {@code board}
     * is a valid move for the bishop piece.
     *
     * Movement:
     *  -Distance:  infinite
     *  -Direction: Diagonal
     *  -No jumping pieces
     *
     * @param move The move to check if valid
     * @param board The current board to test the validity of the move
     * @return Validity
     ******************************************************************/
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board)
    {
        // Check general conditions
        if (!super.isValidMove(move, board)) return false;

        // Check if slope is abs(1:1)
        int dy = move.toRow - move.fromRow;
        int dx = move.toColumn - move.fromColumn;

        if (dx != 0 && Math.abs(dy / dx) != 1) return false;

        // Check if path is blocked
        int rowM = move.toRow > move.fromRow ? 1 : -1;
        int colM = move.toColumn > move.fromColumn ? 1 : -1;

        for (int i = 1; i < Math.abs(move.toColumn - move.fromColumn); i++)
        {
            if (board[move.fromRow + i * rowM][move.fromColumn + i * colM] != null)
                return false;
        }
        return true;
    }
}
