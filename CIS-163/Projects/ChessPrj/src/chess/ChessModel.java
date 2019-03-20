/***********************************************************************
 * The model for functionality for the chess game.
 * @author Silas Agnew
 * @version March 20, 2018
 **********************************************************************/

package chess;

import W18Project3.IChessModel;
import W18Project3.IChessPiece;
import W18Project3.Move;
import W18Project3.Player;
import chess.pieces.*;

import javax.swing.JOptionPane;

public class ChessModel implements IChessModel
{
    private IChessPiece[][] board;
    private Player          player;
    private MiniMax         minimax;

    /*******************************************************************
     * Constructs the model of the chess game.
     ******************************************************************/
    public ChessModel()
    {
        int size = ChessPanel.SIZE;
        minimax = new MiniMax();

        player = Player.WHITE;

        board = new IChessPiece[size][size];
        // Pawns
        for (int i = 0; i < size; i++)
        {
            board[1][i] = new Pawn(Player.BLACK);
            board[6][i] = new Pawn(Player.WHITE);
        }

        // Black pieces
        board[0][0] = new Rook(Player.BLACK);
        board[0][1] = new Knight(Player.BLACK);
        board[0][2] = new Bishop(Player.BLACK);
        board[0][3] = new Queen(Player.BLACK);
        board[0][4] = new King(Player.BLACK);
        board[0][5] = new Bishop(Player.BLACK);
        board[0][6] = new Knight(Player.BLACK);
        board[0][7] = new Rook(Player.BLACK);

        // White pieces
        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);
    }

    /*******************************************************************
     *
     ******************************************************************/
    public boolean isComplete()
    {
        return false;
    }

    /*******************************************************************
     * Checks if the {@code move} is valid or not
     * @param move The move to check
     * @return Whether the move is valid or not
     ******************************************************************/
    public boolean isValidMove(Move move)
    {
        IChessPiece p = pieceAt(move.fromRow, move.fromColumn);
        if (p == null)
        {
            return false;
        }
        return p.isValidMove(move, board) && p.player() == currentPlayer();
    }

    /*******************************************************************
     * Executes the {@code move} passed.
     * @param move The move to execute
     ******************************************************************/
    public void move(Move move)
    {
        IChessPiece removed;
        int[]       removeCo;
        if (board[move.fromRow][move.fromColumn] != null &&
            board[move.fromRow][move.fromColumn].getClass() == Pawn.class &&
            ((Pawn) board[move.fromRow][move.fromColumn]).getEnPassant())
        {
            if (board[move.fromRow][move.fromColumn].player() == Player.WHITE)
            {
                removed = board[move.toRow][move.toColumn + 1];
                removeCo = new int[]{move.toRow, move.toColumn + 1};
            }
            else
            {
                removed = board[move.toRow][move.toColumn - 1];
                removeCo = new int[]{move.toRow, move.toColumn - 1};
            }
            ((Pawn) board[move.fromRow][move.fromColumn]).setEnPassant(false);
            board[removeCo[0]][removeCo[1]] = null;
        }
        else
        {
            removed = board[move.toRow][move.toColumn];
        }
        board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
        board[move.fromRow][move.fromColumn] = null;
        ChessPiece.addMove(move);
        player = player.next();
    }

    /*******************************************************************
     * Checks if the {@code player}'s king is in check.
     * @param player The player to check.
     * @return Whether or not the king of {@code player} is in check
     ******************************************************************/
    public boolean inCheck(Player player)
    {
        IChessPiece[][] newBoard = MiniMax.deepCopyBoard(board);

        int[] king = King.findKing(board, player);
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (pieceAt(i, j) == null)
                {
                    continue;
                }
                if (pieceAt(i, j).player() ==
                    (player == Player.BLACK ? Player.WHITE : Player.BLACK))
                {
                    Move move = minimax.calculate(board, new int[]{i, j}, 0);
                    if (move == null)
                    {
                        return false;
                    }
                    else if (move.toRow == king[0] && move.toColumn == king[1])
                    {
                        String p = player == Player.BLACK ? "White" : "Black";
                        JOptionPane.showMessageDialog(null, p + " is in Check!");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /*******************************************************************
     * Searches the first or last rows of the board for a promotable
     * piece.
     * @param player The owner of the pieces to look for
     * @return The coordinates of the promotable piece or, if none, null
     ******************************************************************/
    public int[] promotable(Player player)
    {
        int searchRow = 0;
        if (player == Player.BLACK)
        {
            searchRow = 7;
        }

        for (int i = 0; i < board[searchRow].length; i++)
        {
            if (board[searchRow][i].type().equals("Pawn") &&
                board[searchRow][i].player() == player)
            {
                return new int[]{searchRow, i};
            }
        }

        return null;
    }

    /*******************************************************************
     * @return The current players
     ******************************************************************/
    public Player currentPlayer()
    {
        return player;
    }

    /*******************************************************************
     *
     ******************************************************************/
    public int numRows()
    {
        return 8;
    }

    /*******************************************************************
     *
     ******************************************************************/
    public int numColumns()
    {
        return 8;
    }

    /*******************************************************************
     * Gets the piece at the specified indices
     * @param row row index
     * @param col column index
     * @return The {@code (x,y)} piece
     ******************************************************************/
    public IChessPiece pieceAt(int row, int col)
    {
        return board[row][col];
    }
}
