/***********************************************************************
 * Entry point for the Chess application.
 *
 * Sprites for chess pieces used under Creative Commons Attribution-Share
 * Alike 3.0 Unported.  Sprite was created by Cburnett adapted by
 * jurgenwesterhof. Link:
 * <a href="https://commons.wikimedia.org/wiki/File:Chess_Pieces_Sprite.svg>source</a>
 *
 * @author Silas Agnew
 * @version March 15, 2018
 **********************************************************************/

package chess;

import javax.swing.JFrame;

public class ChessGUI
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChessPanel());
        frame.pack();
        frame.setVisible(true);
    }
}