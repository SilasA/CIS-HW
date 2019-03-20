/***********************************************************************
 * @author Silas Agnew
 * @version February 26, 2018
 **********************************************************************/

package project2.threeD;

import com.sun.istack.internal.Nullable;

import java.util.Random;

public class ConnectFourGame3D
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
     * The game board in the format col x row x row
     * where col x row is a top-view 2d plane of the board
     * and row is the row of the [row x col] column
     */
    private int[][][] board;
    private int       size;
    private int       player;
    private Random    random;

    /*******************************************************************
     * Constructs a new connect four 3d game board of a certain size.
     * @param size The size of the game board {@code [size by size by size]}.
     ******************************************************************/
    public ConnectFourGame3D(int size)
    {
        this.size = size;
        random = new Random();
        board = new int[size][size][size];
        reset();
        player = USER;
    }

    /*******************************************************************
     * @return The current player
     ******************************************************************/
    public int getPlayer() { return player; }

    /*******************************************************************
     *
     * @param x X Coordinate (col) of the column from a top-view 2d plane
     * @param y Y Coordinate (row) of the column from a top-view 2d plane
     * @param board The game board to insert piece if null, defaults to
     *              current game board
     * @return The row the piece was placed into
     ******************************************************************/
    public int selectCol(int x, int y, @Nullable int[][][] board)
    {
        if (board == null)
            board = this.board;

        for (int i = 0; i < size; i++)
        {
            if (board[x][y][i] == -1)
            {
                board[x][y][i] = player;
                return i;
            }
        }
        return -1;
    }

    /*******************************************************************
     * Resets the current game board
     ******************************************************************/
    public void reset()
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size; k++)
                    board[i][j][k] = -1;
    }

    /*******************************************************************
     * Switches the player between 0 and 1. If the current player is 1
     * then after invocation the player will be 0.
     * @return The new current player
     ******************************************************************/
    public int switchPlayer() { return (player = player ^ 1); }

    /*******************************************************************
     * Checks for following sequences:
     *  - Vertical
     *  - Horizontal x 2d
     *  - Horizontal y 2d
     *  - Diagonal Positive x 2d
     *  - Diagonal Negative x 2d
     *  - Diagonal Positive y 2d
     *  - Diagonal Negative y 2d
     *  - Diagonal Positive 3d (2)
     *  - Diagonal Negative 3d (2)
     *
     * @param player The player to check win for
     * @param board The board to check the win sequences on
     *              if null, defaults to current game board
     * @return Whether or not {@code player} has won
     ******************************************************************/
    public boolean isWinner(int player, @Nullable int[][][] board)
    {
        if (board == null)
            board = this.board;

        return isVHWinner(player, board) ||
               isDiagonal2DWinner(player, board) ||
               isDiagonal3DWinner(player, board);
    }

    /*******************************************************************
     * Decides where the cpu should play
     * TESTING
     ******************************************************************/
    public int[] computerTurn()
    {
        int[][][] futureBoard;

        int[] selection = new int[] { -1, -1 };

        for (int x = 0; x < size; x++)
        {

            // Check for moves to block user
            for (int i = 0; i < size; i++)
            {
                futureBoard = deepCopyBoard();
                selectCol(x, i, futureBoard);
                if (isWinner(player ^ 1, futureBoard))
                {
                    selection = new int[] { x, i };
                    break;
                }
            }

            // Check for moves to win
            // This is higher priority than blocking so placed after to
            // override any blocking selections
            for (int i = 0; i < size; i++)
            {
                futureBoard = deepCopyBoard();
                selectCol(x, i, futureBoard);
                if (isWinner(player, futureBoard))
                {
                    selection = new int[] { x, i };
                    break;
                }

            }

            futureBoard = deepCopyBoard();

            // when there aren't blocking or win opportunities just
            // place a random column
            if (selection[0] < 0)
            {
                do
                {
                    selection = new int[]
                            { random.nextInt(size), random.nextInt(size) };
                } while (selectCol(selection[0], selection[1], futureBoard) < 0);
            }
        }
        return selection;
    }

    /*******************************************************************
     * Deep copies the game board
     * Complexity: size^3 + 1 in O(n^3)
     * @return The copied board
     ******************************************************************/
    private int[][][] deepCopyBoard()
    {
        int[][][] clone = new int[size][size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size; k++)
                    clone[i][j][k] = board[i][j][k];
        return clone;
    }

    /*******************************************************************
     * Checks all vertical and horizontal win cases
     * Complexity: 6*size^3 in O(n^3)
     * @param player The player to check win for
     * @param board The board to check win in
     * @return Whether or not the win cases can be found
     ******************************************************************/
    private boolean isVHWinner(int player, int[][][] board)
    {
        for (int c = 0; c < size; c++)
        {
            for (int r = 0; r < size; r++)
            {
                for (int i = 0; i < size - 3; i++)
                {
                    // Vertical
                    if (board[c][r][i] == player &&
                        board[c][r][i + 1] == player &&
                        board[c][r][i + 2] == player &&
                        board[c][r][i + 3] == player)
                        return true;

                    // Horizontal x
                    if (board[c][i][r] == player &&
                        board[c][i + 1][r] == player &&
                        board[c][i + 2][r] == player &&
                        board[c][i + 3][r] == player)
                        return true;

                    // Horizontal y
                    if (board[i][r][c] == player &&
                        board[i + 1][r][c] == player &&
                        board[i + 2][r][c] == player &&
                        board[i + 3][r][c] == player)
                        return true;
                }
            }
        }
        return false;
    }

    /*******************************************************************
     * Checks all diagonal win cases that exist in 2D space
     * @param player The player to check win for
     * @param board The board to check win in
     * @return Whether or not the win cases can be found
     ******************************************************************/
    private boolean isDiagonal2DWinner(int player, int[][][] board)
    {
        int checkSize = size - 3;

        // X
        for (int c = 0; c < checkSize; c++)
        {
            for (int r = 0; r < checkSize; r++)
            {
                for (int i = 0; i < size; i++)
                {
                    // Negative
                    if (board[c][r][i] == player &&
                        board[c + 1][r + 1][i] == player &&
                        board[c + 2][r + 2][i] == player &&
                        board[c + 3][r + 3][i] == player)
                        return true;

                    // Positive
                    if (board[c + 3][r][i] == player &&
                        board[c + 2][r + 1][i] == player &&
                        board[c + 1][r + 2][i] == player &&
                        board[c][r + 3][i] == player)
                        return true;
                }
            }
        }

        // Y
        for (int c = 0; c < size; c++)
        {
            for (int r = 0; r < checkSize; r++)
            {
                for (int i = 0; i < checkSize; i++)
                {
                    // Positive
                    if (board[c][r][i] == player &&
                        board[c][r + 1][i + 1] == player &&
                        board[c][r + 2][i + 2] == player &&
                        board[c][r + 3][i + 3] == player)
                        return true;

                    // Negative
                    if (board[c][r][i + 3] == player &&
                        board[c][r + 1][i + 2] == player &&
                        board[c][r + 2][i + 1] == player &&
                        board[c][r + 3][i] == player)
                        return true;
                }
            }
        }

        // Z
        for (int c = 0; c < checkSize; c++)
        {
            for (int r = 0; r < size; r++)
            {
                for (int i = 0; i < checkSize; i++)
                {
                    // Negative
                    if (board[c][r][i] == player &&
                        board[c + 1][r][i + 1] == player &&
                        board[c + 2][r][i + 2] == player &&
                        board[c + 3][r][i + 3] == player)
                        return true;

                    // Positive
                    if (board[c][r][i + 3] == player &&
                        board[c + 1][r][i + 2] == player &&
                        board[c + 2][r][i + 1] == player &&
                        board[c + 3][r][i] == player)
                        return true;
                }
            }
        }

        return false;
    }

    /*******************************************************************
     * Checks all Diagonal win cases that exist in a 3D space
     * @param player The player to check win for
     * @param board The board to check win in
     * @return Whether or not the win cases can be found
     ******************************************************************/
    private boolean isDiagonal3DWinner(int player, int[][][] board)
    {
        int checkSize = size - 3;

        for (int c = 0; c < checkSize; c++)
        {
            for (int r = 0; r < checkSize; r++)
            {
                for (int i = 0; i < checkSize; i++)
                {
                    // Negative
                    if (board[c][r][i] == player &&
                        board[c + 1][r + 1][i + 1] == player &&
                        board[c + 2][r + 2][i + 2] == player &&
                        board[c + 3][r + 3][i + 3] == player)
                        return true;

                    if (board[c][r][i + 3] == player &&
                        board[c + 1][r + 1][i + 2] == player &&
                        board[c + 2][r + 2][i + 1] == player &&
                        board[c + 3][r + 3][i] == player)
                        return true;

                    // Positive
                    if (board[c + 3][r][i] == player &&
                        board[c + 2][r + 1][i + 1] == player &&
                        board[c + 1][r + 2][i + 2] == player &&
                        board[c][r + 3][i + 3] == player)
                        return true;

                    if (board[c + 3][r][i + 3] == player &&
                        board[c + 2][r + 1][i + 2] == player &&
                        board[c + 1][r + 2][i + 1] == player &&
                        board[c][r + 3][i] == player)
                        return true;
                }
            }
        }
        return false;
    }
}
