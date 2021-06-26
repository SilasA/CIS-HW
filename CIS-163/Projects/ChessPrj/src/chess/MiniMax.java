/***********************************************************************
 * Implementation of MiniMax algorithm with alpha-beta pruning to
 * decide what move is best for the AI.
 *
 * @author Silas Agnew
 * @version March 19, 2018
 **********************************************************************/

package chess;

import W18Project3.IChessPiece;
import W18Project3.Move;

import chess.pieces.ChessPiece;

import java.util.HashMap;

public class MiniMax
{
    /*******************************************************************
     * Constructs the algorithm to check move tree.
     ******************************************************************/
    public MiniMax() {}

    /*******************************************************************
     *
     * @param board
     * @param point
     * @param depth
     * @return The optimal move to a certain depth
     ******************************************************************/
    public Move calculate(IChessPiece[][] board, int[] point, int depth)
    {
        IChessPiece piece = board[point[0]][point[1]];
        HashMap<Integer, Move> moves = new HashMap<>();

        for (int i = 0; i < ChessPanel.SIZE; i++)
        {
            for (int j = 0; j < ChessPanel.SIZE; j++)
            {
                Move move = new Move(point[0], point[1], i, j);
                if (piece.isValidMove(move, board))
                {
                    int score = maxi(deepCopyBoard(board), move, depth,
                         Integer.MIN_VALUE, Integer.MAX_VALUE);
                    moves.put(score, move);
                }
            }
        }

        int bestScore = 0;
        for (Integer score : moves.keySet())
        {
            if (score > bestScore)
                bestScore = score;
        }

        return moves.get(bestScore);
    }

    /*******************************************************************
     *
     * @param board
     * @param move
     * @param depth
     * @param alpha
     * @param beta
     * @return
     ******************************************************************/
    public int mini(IChessPiece[][] board, Move move, int depth,
                    int alpha, int beta)
    {
        ChessPiece p = (ChessPiece)board[move.toRow][move.toColumn];
        if (depth <= 0)
        {
            if (p == null) return 0;
            else return p.getValueScore();
        }

        // Execute move passed
        board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
        board[move.fromRow][move.fromColumn] = null;

        int minScore = Integer.MAX_VALUE;

        // For each available move, find the max score then the min of those
        for (int i = 0; i < ChessPanel.SIZE; i++)
        {
            for (int j = 0; j < ChessPanel.SIZE; j++)
            {
                Move newMove = new Move(move.toRow, move.toColumn, i, j);
                if (board[move.toRow][move.toColumn] != null &&
                    board[move.toRow][move.toColumn].isValidMove(newMove, board))
                {
                    int score = maxi(deepCopyBoard(board), newMove,
                            depth - 1, alpha, beta);
                    if (score < minScore)
                        minScore = score;
                }
            }
        }
        return minScore;
    }

    /*******************************************************************
     *
     * @param board
     * @param move
     * @param depth
     * @param alpha
     * @param beta
     * @return
     ******************************************************************/
    public int maxi(IChessPiece[][] board, Move move, int depth,
                    int alpha, int beta)
    {
        ChessPiece p = (ChessPiece)board[move.toRow][move.toColumn];
        if (depth <= 0)
        {
            if (p == null) return 0;
            else return p.getValueScore();
        }

        // Execute move passed
        board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
        board[move.fromRow][move.fromColumn] = null;

        int maxScore = Integer.MIN_VALUE;

        // For each available move, find the min score then the max of those
        for (int i = 0; i < ChessPanel.SIZE; i++)
        {
            for (int j = 0; j < ChessPanel.SIZE; j++)
            {
                Move newMove = new Move(move.toRow, move.toColumn, i, j);
                if (board[move.toRow][move.toColumn] != null &&
                    board[move.toRow][move.toColumn].isValidMove(newMove, board))
                {
                    int score = mini(deepCopyBoard(board), newMove,
                                     depth - 1, alpha, beta);
                    if (score > maxScore)
                        maxScore = score;
                }
            }
        }
        return maxScore;
    }

    /*******************************************************************
     * Deep copies the board array
     * @param board board to copy
     * @return an independent duplicate of {@code board}
     ******************************************************************/
    public static IChessPiece[][] deepCopyBoard(IChessPiece[][] board)
    {
        IChessPiece[][] newBoard = new IChessPiece[board.length][board.length];

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                newBoard[i][j] = board[i][j];
        return newBoard;
    }
}
