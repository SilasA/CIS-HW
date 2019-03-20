/***********************************************************************
 * @see chess.pieces.ChessPiece
 *
 * @author Silas Agnew
 * @version March 15, 2018
 **********************************************************************/

package chess.pieces;

import W18Project3.IChessPiece;
import W18Project3.Move;
import W18Project3.Player;

public class Queen extends ChessPiece
{
    /*******************************************************************
     *
     * @param player
     ******************************************************************/
    public Queen(Player player)
    {
        super(player, SCORE_QUEEN);
    }

    /*******************************************************************
     * @return The type of piece
     * @see IChessPiece#type()
     ******************************************************************/
    @Override
    public String type()
    {
        return "Queen";
    }

    /*******************************************************************
     * Checks whether or not {@code move} on the current state {@code board}
     * is a valid move for the queen piece.
     *
     * Movement:
     *  -Distance:  infinite
     *  -Direction: all
     *  -No jumping pieces
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

        // accepted slopes: inf, 0, and abs(1)
        if (dx != 0 && dy / dx != 0 && Math.abs(dy / (double)dx) != 1) return false;

        // Check if path is blocked
        int rowM = move.toRow > move.fromRow ? 1 : -1;
        int colM = move.toColumn > move.fromColumn ? 1 : -1;

        if (move.toRow == move.fromRow)
            rowM = 0;
        else if (move.toColumn == move.fromColumn)
            colM = 0;

        for (int i = 1; i < Math.abs(move.toRow - move.fromRow); i++)
        {
            if (board[move.fromRow + i * rowM][move.fromColumn + i * colM] != null)
                return false;
        }
        return true;
    }
}