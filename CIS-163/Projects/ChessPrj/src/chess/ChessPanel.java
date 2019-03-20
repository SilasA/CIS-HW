/***********************************************************************
 * @author Silas Agnew
 * @version March 19, 2018
 **********************************************************************/

package chess;

import W18Project3.IChessPiece;
import W18Project3.Move;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Rook;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChessPanel extends JPanel implements ActionListener
{
    public static final int   SIZE         = 8;
    public static final Color DARK         = new Color(97, 63, 32);
    public static final Color LIGHT        = new Color(228, 203, 163);
    public static final Color DARK_SELECT  = new Color(73, 95, 87);
    public static final Color LIGHT_SELECT = new Color(171, 200, 185);

    private JButton[][] board;
    private ChessModel  model;
    //private GraveyardPanel lightGraveyard;
    //private GraveyardPanel darkGraveyard;

    private BufferedImage[] pieces;

    private JMenuBar  menuBar;
    private JMenu     fileMenu;
    private JMenuItem resetItem;
    private JMenuItem quitItem;
    private JMenu     aboutMenu;
    private JMenuItem learnItem;
    private JMenuItem aboutItem;

    private ActionListener buttonListener;

    /*******************************************************************
     * Handles all game related gui action
     ******************************************************************/
    private class ButtonListener implements ActionListener
    {
        private JButton first  = null;
        private JButton second = null;

        /***************************************************************
         * Finds the coordinates for a particular button by searching
         * {@code board} for a match.
         * @param button Button to look for
         * @return The (x,y) coordinates of the button
         **************************************************************/
        private int[] getButtonCoords(JButton button)
        {
            for (int i = 0; i < SIZE; i++)
            {
                for (int j = 0; j < SIZE; j++)
                {
                    if (button == board[i][j])
                    {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[]{-1, -1};
        }

        /***************************************************************
         * Handles the button presses that is moving pieces around
         * @param e Event
         **************************************************************/
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JButton btn = (JButton) e.getSource();
            if (first == null)
            {
                btn.setBackground(
                        btn.getBackground() == LIGHT ? LIGHT_SELECT : DARK_SELECT);
                first = btn;
            }
            else if (second == null)
            {
                if (btn.getBackground() != LIGHT_SELECT &&
                        btn.getBackground() != DARK_SELECT)
                {
                    btn.setBackground(
                            btn.getBackground() == LIGHT ? LIGHT_SELECT : DARK_SELECT);
                }
                second = btn;

                int[] tile1 = getButtonCoords(first);
                int[] tile2 = getButtonCoords(second);

                Move move = new Move(tile1[0], tile1[1], tile2[0], tile2[1]);

                if (model.isValidMove(move) && !model.inCheck(model.currentPlayer()))
                {
                    model.move(move);
                    btn.setIcon(board[tile1[0]][tile1[1]].getIcon());
                    board[tile1[0]][tile1[1]].setIcon(null);
                }

                if (tile1[0] == tile2[0] && tile1[1] == tile2[1])
                {
                    first.setBackground(
                            first.getBackground().getRGB() == LIGHT_SELECT.getRGB()
                                    ? LIGHT : DARK);
                }
                else
                {
                    first.setBackground(
                            first.getBackground().getRGB() == LIGHT_SELECT.getRGB()
                                    ? LIGHT : DARK);
                    second.setBackground(
                            second.getBackground().getRGB() == LIGHT_SELECT.getRGB()
                                    ? LIGHT : DARK);
                }

                first = null;
                second = null;
            }
        }
    }

    /*******************************************************************
     * Instantiates the Chess panel which serves as a gui for a game
     * of chess.
     ******************************************************************/
    public ChessPanel()
    {
        setLayout(new BorderLayout());

        // Menus
        fileMenu = new JMenu("File");

        resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(this);
        fileMenu.add(resetItem);

        quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);

        aboutMenu = new JMenu("About");

        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        aboutMenu.add(aboutItem);

        learnItem = new JMenuItem("Learn to Play");
        learnItem.addActionListener(this);
        aboutMenu.add(learnItem);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        add(menuBar, BorderLayout.NORTH);

        // Images
        try
        {
            pieces = new BufferedImage[12];
            pieces[0] = ImageIO.read(new File("img/pieces/light_pawn.png"));
            pieces[1] = ImageIO.read(new File("img/pieces/light_rook.png"));
            pieces[2] = ImageIO.read(new File("img/pieces/light_knight.png"));
            pieces[3] = ImageIO.read(new File("img/pieces/light_bishop.png"));
            pieces[4] = ImageIO.read(new File("img/pieces/light_queen.png"));
            pieces[5] = ImageIO.read(new File("img/pieces/light_king.png"));
            pieces[6] = ImageIO.read(new File("img/pieces/dark_pawn.png"));
            pieces[7] = ImageIO.read(new File("img/pieces/dark_rook.png"));
            pieces[8] = ImageIO.read(new File("img/pieces/dark_knight.png"));
            pieces[9] = ImageIO.read(new File("img/pieces/dark_bishop.png"));
            pieces[10] = ImageIO.read(new File("img/pieces/dark_queen.png"));
            pieces[11] = ImageIO.read(new File("img/pieces/dark_king.png"));
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        // Game board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE, SIZE));

        board = new JButton[SIZE][SIZE];
        model = new ChessModel();

        buttonListener = new ButtonListener();

        for (int i = 0; i < SIZE; i++)
        {
            Color color = (i % 2 == 0) ? DARK : LIGHT;
            for (int j = 0; j < SIZE; j++)
            {
                board[i][j] = new JButton();
                board[i][j].addActionListener(buttonListener);
                board[i][j].setBackground(color);
                board[i][j].setBorder(new EmptyBorder(0, 0, 0, 0));
                boardPanel.add(board[i][j]);
                color = (color == LIGHT) ? DARK : LIGHT;
            }
        }

        add(boardPanel);
        resetPieces();

        // Graveyards
        //lightGraveyard = new GraveyardPanel(0);
        //darkGraveyard = new GraveyardPanel(1);

        //add(lightGraveyard, BorderLayout.EAST);
        //add(darkGraveyard, BorderLayout.EAST);

    }

    /*******************************************************************
     * Sets up the pieces on the game board
     ******************************************************************/
    private void resetPieces()
    {
        // Clear
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                board[i][j].setIcon(null);
            }
        }

        // Pawns
        for (int i = 0; i < SIZE; i++)
        {
            board[1][i].setIcon(new ImageIcon(pieces[6]));
            board[6][i].setIcon(new ImageIcon(pieces[0]));
        }

        // a(1, 8) through e(1, 8) pieces
        for (int i = 0; i < SIZE / 2 + 1; i++)
        {
            board[7][i].setIcon(new ImageIcon(pieces[i + 1]));
            board[0][i].setIcon(new ImageIcon(pieces[i + 7]));
        }

        // f(1, 8) through h(1, 8) pieces
        for (int i = SIZE - 3; i < SIZE; i++)
        {
            board[7][i].setIcon(new ImageIcon(pieces[SIZE - i]));
            board[0][i].setIcon(new ImageIcon(pieces[SIZE - i + 6]));
        }
    }

    /*******************************************************************
     * @return The piece that was chosen in the dialog box
     ******************************************************************/
    private IChessPiece getPromotion()
    {
        String[] selection = new String[]
        {
                "Queen",
                "Knight",
                "Rook",
                "Bishop"
        };

        String s = JOptionPane.showInputDialog(this, selection);

        switch (s)
        {
            case "Queen":
                return new Queen(model.currentPlayer());
            case "Knight":
                return new Knight(model.currentPlayer());
            case "Rook":
                return new Rook(model.currentPlayer());
            case "Bishop":
                return new Bishop(model.currentPlayer());
            default:
                break;
        }
        return null;
    }

    /*******************************************************************
     * Handles all not game related gui action.
     * @param e Event
     ******************************************************************/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == quitItem)
        {
            System.exit(0);
        }
        else if (e.getSource() == resetItem)
        {
            model = new ChessModel();
            resetPieces();
        }
        else if (e.getSource() == aboutItem)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Created by Silas Agnew.\nPiece sprites used " +
                    "under CC and created by Cburnett.");
        }
        else if (e.getSource() == learnItem)
        {
            Desktop d = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (d != null && d.isSupported(Desktop.Action.BROWSE))
            {
                try
                {
                    d.browse(new URI("https://www.chess.com/lessons"));
                }
                catch (Exception ex)
                {
                    return;
                }
            }
        }
    }
}
