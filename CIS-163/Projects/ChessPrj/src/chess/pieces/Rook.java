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

public class Rook extends ChessPiece
{
    /*******************************************************************
     *
     * @param player
     ******************************************************************/
    public Rook(Player player)
    {
        super(player, SCORE_ROOK);
    }

    /*******************************************************************
     * @return The type of piece
     * @see IChessPiece#type()
     ******************************************************************/
    @Override
    public String type()
    {
        return "Rook";
    }

    /*******************************************************************
     * Checks whether or not {@code move} on the current state {@code board}
     * is a valid move for the rook piece.
     *
     * Movement:
     *  -Distance:  infinite
     *  -Direction: perpendicular
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

        // Check slope for perpendicular movement
        int dy = move.toRow - move.fromRow;
        int dx = move.toColumn - move.fromColumn;

        if (dx != 0 && dy / dx != 0) return false;

        // Check pathway for pieces
        int rowM;
        int colM;

        if (move.toRow == move.fromRow)
        {
            colM = 0;
            rowM = move.toRow > move.fromRow ? 1 : -1;
        }
        else
        {
            rowM = 0;
            colM = move.toColumn > move.fromColumn ? 1 : -1;
        }

        for (int i = 0; i < (rowM == 0 ? Math.abs(move.toColumn - move.fromColumn)
                : Math.abs(move.toRow - move.fromRow)); i++)
        {
            if (board[move.fromRow + i * rowM][move.fromColumn + i * colM] != null)
                return false;
        }

        return true;
    }
}
