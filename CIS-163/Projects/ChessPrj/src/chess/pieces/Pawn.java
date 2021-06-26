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

public class Pawn extends ChessPiece
{
    private boolean enPassant = false;

    /*******************************************************************
     *
     * @param player
     ******************************************************************/
    public Pawn(Player player)
    {
        super(player, SCORE_PAWN);
    }

    /*******************************************************************
     * @return The type of piece
     * @see IChessPiece#type()
     ******************************************************************/
    @Override
    public String type() { return "Pawn"; }

    /*******************************************************************
     * Checks whether or not {@code move} on the current state {@code board}
     * is a valid move for the pawn piece.
     *
     * Movement:
     *  -Distance:  2 or 1 tile first move
     *              1 tile
     *  -Direction: straight forward move
     *              diagonal forward attacking
     *              en passant
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
        if (!super.isValidMove(move, board)) return false;

        // slope
        int dx = move.toColumn - move.fromColumn;
        int dy = move.toRow - move.fromRow;

        IChessPiece p = board[move.fromRow][move.fromColumn];

        if (dx == 0)
        {
            // displacement
            if (p.player() == Player.WHITE && (dy < -2 || dy > -1)) return false;
            if (p.player() == Player.BLACK && (dy < 1 || dy > 2)) return false;

            int m = p.player() == Player.WHITE ? -1 : 1;

            // collision
            for (int i = move.fromRow + 1; i < move.toRow; i++)
            {
                if (board[i * m][move.fromColumn] != null) return false;
            }
            return true;
        }
        else if (Math.abs(dy / dx) == 1)
        {
            // Left en passant
            if (dx < 0 && board[move.toRow][move.toColumn] == null)
            {
                Move m = lastMove();
                if (board[m.toRow][m.toColumn].type().equals("Pawn") &&
                    Math.abs(m.toRow - m.fromRow) == 2 &&
                    board[move.fromRow][move.fromColumn - 1] ==
                    board[m.toRow][m.toColumn])
                {
                    enPassant = true;
                    return true;
                }
            }
            // Right en passant
            else if (dx > 0 && board[move.toRow][move.toColumn] == null)
            {
                Move m = lastMove();
                if (board[m.toRow][m.toColumn].type().equals("Pawn") &&
                    Math.abs(m.toRow - m.fromRow) == 2 &&
                    board[move.fromRow][move.fromColumn + 1] ==
                    board[m.toRow][m.toColumn])
                {
                    enPassant = true;
                    return true;
                }

            }
            // Attack
            else if (board[move.toRow][move.toColumn] != null)
            {
                // displacement
                if (p.player() == Player.WHITE && dx != -1) return false;
                if (p.player() == Player.BLACK && dx != 1) return false;
                return true;
            }
        }

        return false;
    }

    /*******************************************************************
     *
     * @param enPassant
     ******************************************************************/
    public void setEnPassant(boolean enPassant)
    {
        this.enPassant = enPassant;
    }

    /*******************************************************************
     * @return
     ******************************************************************/
    public boolean getEnPassant()
    {
        return enPassant;
    }
}
