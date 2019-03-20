/***********************************************************************
 * @see chess.pieces.ChessPiece
 *
 * @author Silas Agnew
 * @version March 19, 2018
 **********************************************************************/

package chess.pieces;

import W18Project3.IChessPiece;
import W18Project3.Move;
import W18Project3.Player;

public class King extends ChessPiece
{
    /*******************************************************************
     *
     * @param player
     ******************************************************************/
    public King(Player player)
    {
        super(player, SCORE_KING);
    }

    /*******************************************************************
     * @return The type of piece
     * @see IChessPiece#type()
     ******************************************************************/
    @Override
    public String type()
    {
        return "King";
    }

    /*******************************************************************
     * Checks whether or not {@code move} on the current state {@code board}
     * is a valid move for the king piece.
     *
     * Movement:
     *  -Distance:  1 tile
     *  -Direction: all
     *  -No jumping pieces
     *  -Castle
     *
     * @param move The move to check if valid
     * @param board The current board to test the validity of the move
     * @return Validity
     * @see IChessPiece#isValidMove(Move, IChessPiece[][])
     ******************************************************************/
    @Override
    public boolean isValidMove(Move move, IChessPiece[][] board)
    {
        // Support castling
        return super.isValidMove(move, board) &&
               (Math.abs(move.toColumn - move.fromColumn) == 1 ||
                Math.abs(move.toRow - move.fromRow) == 1);
    }

    /*******************************************************************
     * Finds the king of {@code player}.
     * @param board The current game board
     * @param player The player whose king to search for
     * @return The coordinates of the found king
     ******************************************************************/
    public static int[] findKing(IChessPiece[][] board, Player player)
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] == null) continue;
                else if (board[i][j].type().equals("King") &&
                    board[i][j].player() == player)
                {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }
}
