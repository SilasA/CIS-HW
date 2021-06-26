
import java.awt.Point;
import java.util.*;

public class xConnectFourGameEXAMPLE {

    private int[][] board;
    private int size;
    private int player;
    private int playerCount;
    private int connections;

    public xConnectFourGameEXAMPLE (int pSize) {
        size = pSize;
        board = new int[pSize][pSize];
        this.playerCount = 2;
        this.connections = 4;
        this.player = 0;
        reset();
    }

    public void reset() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = -1;
    }

    public int selectCol (int pCol) {
        for (int row = size - 1; row >= 0; row--) 
            if (board[row][pCol] == -1) {
                board[row][pCol] = player;
                return row;
            }
        return -1;
    }

    public int nextPlayer() {
        player = (player + 1) % playerCount;
        return player;

    }

    public int getCurrentPlayer () {
        return player;
    }

    public boolean isWinner() {
        int count;
        // Vertical
        for (int c = 0; c < size; c++)
        {
            count = 0;
            for (int r = 0; r < size; r++)
            {
                if (board[r][c] == getCurrentPlayer())
                {
                    count++;
                }
                else if (board[r][c] != getCurrentPlayer())
                {
                    count = 0;
                }

                if (count >= 4)
                    return true;
            }
        }

        // Horizontal
        for (int r = size - 1; r >= 0; r--)
        {
            count = 0;
            for (int c = 0; c < size; c++)
            {
                if (board[r][c] == getCurrentPlayer())
                {
                    count++;
                }
                else if (board[r][c] != getCurrentPlayer())
                {
                    count = 0;
                }

                if (count >= 4)
                    return true;
            }
        }
        return false;
    }
}






