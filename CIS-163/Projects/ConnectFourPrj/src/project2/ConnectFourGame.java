/***********************************************************************
 * Game Mechanics for Connect Four
 * <br/>
 * Manages a collection resembling the game board.
 * Allows for placement of pieces in columns, checking for a winner,
 * and AI player turns.
 *
 * @author Silas Agnew
 * @version February 26, 2018
 **********************************************************************/

package project2;

import com.sun.istack.internal.Nullable;

import java.util.Random;

public class ConnectFourGame
{
    /**
     * Integer representing the end user
     */
    public static final int USER = 0;

    /**
     * Integer representing the computer
     */
    public static final int COMPUTER = 1;

    /**
     * The game board in the format row x col
     */
    private int[][] board;
    private int     size;
    private int     player;
    private Random  random;

    /*******************************************************************
     * Constructs a new connect four game board of a certain size.
     * @param size The size of the game board {@code [size by size]}.
     ******************************************************************/
    public ConnectFourGame(int size)
    {
        this.size = size;
        board = new int[size][size];
        random = new Random();

        reset();
        player = USER;
    }

    /*******************************************************************
     * @return The size of the board
     ******************************************************************/
    public int getSize()
    {
        return size;
    }

    /*******************************************************************
     * @return The current player
     ******************************************************************/
    public int getPlayer()
    {
        return player;
    }

    /*******************************************************************
     * Selects a column to place a player number into.
     * Finds the lowest, unoccupied row in the column.
     * @param col column to look
     * @param board board to place into.  If null, default is the board
     * @return the row placed or -1 if all rows are occupied
     ******************************************************************/
    public int selectCol(int col, @Nullable int[][] board)
    {
        if (board == null)
        {
            board = this.board;
        }

        for (int i = size - 1; i >= 0; i--)
        {
            if (board[i][col] == -1)
            {
                board[i][col] = player;
                return i;
            }
        }
        return -1;
    }

    /*******************************************************************
     * Clears the game
     ******************************************************************/
    public void reset()
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = -1;
    }

    /*******************************************************************
     * Switches the player between 0 and 1. If the current player is 1
     * then after invocation the player will be 0.
     * @return The new current player
     ******************************************************************/
    public int switchPlayer() { return (player = player ^ 1); }

    /*******************************************************************
     * Checks if {@code player} has a winning board
     * @param player player to check win conditions for
     * @param board the board to check a win in.  If null it will
     *              default to the member game board in this.
     * @return whether or not {@code player} won
     ******************************************************************/
    @Nullable
    public boolean isWinner(int player, int[][] board)
    {
        if (board == null)
            board = this.board;

        return isVHWinner(player, board) || isDiagonalWinner(player, board);
    }

    /*******************************************************************
     * Computes the best column with 2 goals in order of importance:
     *  1) The play will win
     *  2) The play will prevent opponent from winning
     * If no moves meet these goals a random column is selected.
     * @return The column selected
     ******************************************************************/
    public int computerTurn()
    {
        int[][] futureBoard;

        int selection = -1;

        // Check for moves to block user
        for (int i = 0; i < size; i++)
        {
            futureBoard = deepCopyBoard();
            selectCol(i, futureBoard);
            if (isWinner(player ^ 1, futureBoard))
            {
                selection = i;
                break;
            }
        }

        // Check for moves to win
        // This is higher priority than blocking so placed after to
        // override any blocking selections
        for (int i = 0; i < size; i++)
        {
            futureBoard = deepCopyBoard();
            selectCol(i, futureBoard);
            if (isWinner(player, futureBoard))
            {
                selection = i;
                break;
            }

        }

        futureBoard = deepCopyBoard();

        // when there aren't blocking or win opportunities just
        // place a random column
        if (selection < 0)
        {
            do
            {
                selection = random.nextInt(10);
            } while (selectCol(selection, futureBoard) < 0);
        }

        return selection;
    }

    /*******************************************************************
     * Deep copies the game board
     * Complexity: size^2 + 1 in O(n^2)
     * @return The copied board
     ******************************************************************/
    private int[][] deepCopyBoard()
    {
        int[][] clone = new int[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                clone[i][j] = board[i][j];
        return clone;
    }

    // Win Checking Methods

    /*******************************************************************
     * Checks {@code board} for vertical and horizontal win cases.
     * Complexity: 4*size^2  in O(n^2)
     * @param player player to check win for
     * @param board board to check win within
     * @return whether or not {@code player} won the game on {@code board}
     ******************************************************************/
    private boolean isVHWinner(int player, int[][] board)
    {
        for (int c = 0; c < size; c++)
        {
            for (int r = 0; r < size - 3; r++)
            {
                // Vertical
                if (board[r][c] == player && board[r + 1][c] == player &&
                    board[r + 2][c] == player && board[r + 3][c] == player)
                    return true;

                // Horizontal (inverse of vertical)
                if (board[c][r] == player && board[c][r + 1] == player &&
                    board[c][r + 2] == player && board[c][r + 3] == player)
                    return true;
            }
        }
        return false;
    }

    /*******************************************************************
     * Checks {@code board} for all diagonal win cases
     * Complexity: 4*(size - 3)^2 + 1 in O(n^2)
     * @param player player to check win for
     * @param board board to check win within
     * @return whether or not {@code player} won the game on {@code board}
     ******************************************************************/
    private boolean isDiagonalWinner(int player, int[][] board)
    {
        int checkSize = size - 3;

        for (int c = 0; c < checkSize; c++)
        {
            for (int r = 0; r < checkSize; r++)
            {
                // Negative
                if (board[r][c] == player &&
                    board[r + 1][c + 1] == player &&
                    board[r + 2][c + 2] == player &&
                    board[r + 3][c + 3] == player)
                    return true;

                // Positive
                if (board[r + 3][c] == player &&
                    board[r + 2][c + 1] == player &&
                    board[r + 1][c + 2] == player &&
                    board[r][c + 3] == player)
                    return true;
            }
        }
        return false;
    }
}
