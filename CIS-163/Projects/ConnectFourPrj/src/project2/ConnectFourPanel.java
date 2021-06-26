/***********************************************************************
 * GUI for Connect Four game.
 * <br/>
 *
 *
 * @author Silas Agnew
 * @version February 24, 2018
 **********************************************************************/

package project2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConnectFourPanel extends JFrame implements ActionListener
{
    public static int SIZE = 10;

    private JPanel gamePanel;

    private JLabel[][]      board;
    private JButton[]       selection;
    private ConnectFourGame game;

    private ImageIcon blankIcon;
    private ImageIcon blueIcon;
    private ImageIcon redIcon;

    private JMenuBar  menu;
    private JMenu     fileMenu;
    private JMenu     aboutMenu;
    private JMenuItem toggleModeItem;
    private JMenuItem resetItem;
    private JMenuItem quitItem;
    private JMenuItem aboutItem;

    /**
     * True: Human vs Human
     * False: Human vs CPU
     */
    private boolean gameMode = false;
    private boolean gameOver = false;

    /*******************************************************************
     * Creates a GUI for Connect Four
     * Visualizes a game board and basic program functionality
     * (closing, resetting, changing ai)
     ******************************************************************/
    public ConnectFourPanel()
    {
        game = new ConnectFourGame(SIZE);
        selection = new JButton[SIZE];

        setLayout(new BorderLayout());

        // Menu
        menu = new JMenuBar();
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");
        toggleModeItem = new JMenuItem("Toggle Opponent (CPU)");
        resetItem = new JMenuItem("Reset");
        quitItem = new JMenuItem("Quit");

        toggleModeItem.addActionListener(this);
        fileMenu.add(toggleModeItem);

        resetItem.addActionListener(this);
        fileMenu.add(resetItem);

        quitItem.addActionListener(this);
        fileMenu.add(quitItem);

        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);
        aboutMenu.add(aboutItem);

        menu.add(fileMenu);
        menu.add(aboutMenu);

        add(menu, BorderLayout.NORTH);

        // Board
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(SIZE + 1, SIZE, 0, 0));

        for (int i = 0; i < SIZE; i++)
        {
            selection[i] = new JButton("Select");
            selection[i].addActionListener(this);
            selection[i].setForeground(Color.RED);
            gamePanel.add(selection[i]);
        }

        board = new JLabel[SIZE][SIZE];

        blankIcon = new ImageIcon("img/2d/blank.png", "");
        blueIcon = new ImageIcon("img/2d/blue.png", "");
        redIcon = new ImageIcon("img/2d/red.png", "");

        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                board[i][j] = new JLabel("", blankIcon, JLabel.CENTER);
                gamePanel.add(board[i][j]);
            }
        }

        add(gamePanel, BorderLayout.SOUTH);
    }

    /*******************************************************************
     * Checks for 4 similar pieces in a line for the current player
     ******************************************************************/
    public void checkWinner()
    {
        if (game.isWinner(game.getPlayer(), null))
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Player " + (game.getPlayer() + 1) + " has won the game!");
            game.reset();
            for (JLabel[] jLabels : board)
                for (JLabel jLabel : jLabels)
                    jLabel.setIcon(blankIcon);
            setButtonColor(Color.RED);
            gameOver = true;
        }
    }

    /*******************************************************************
     * Handles any GUI interaction from the end user
     * @param e event
     ******************************************************************/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        gameOver = false;
        if (e.getSource() == toggleModeItem)
        {
            // Toggles opponent mode
            gameMode = !gameMode;
            toggleModeItem.setText(
                    "Toggle Opponent " + (gameMode ? "(Human)" : "(CPU)"));
        }
        else if (e.getSource() == resetItem)
        {
            // Reset game
            game.reset();
            for (JLabel[] jLabels : board)
                for (JLabel jLabel : jLabels)
                    jLabel.setIcon(blankIcon);
            setButtonColor(Color.RED);
        }
        else if (e.getSource() == quitItem)
        {
            // Close game
            System.exit(0);
        }
        else if (e.getSource() == aboutItem)
        {
            // About info
            JOptionPane.showMessageDialog(
                    this,
                    "Connect Four - Silas Agnew.");
        }
        else
        {
            // Check all column select buttons
            for (int i = 0; i < selection.length; i++)
            {
                if (selection[i] == e.getSource())
                {
                    int row = game.selectCol(i, null);
                    if (row != -1)
                    {
                        board[row][i].setIcon((game.getPlayer() == 0) ? redIcon : blueIcon);
                        checkWinner();
                        game.switchPlayer();
                        setButtonColor((game.getPlayer() == 0) ? Color.RED : Color.BLUE);
                        if (!gameMode && !gameOver)
                            computerTurn();
                        return;
                    }
                }
            }
        }
    }

    /*******************************************************************
     * Manages the computers turn
     ******************************************************************/
    private void computerTurn()
    {
        int col = game.computerTurn();

        int row = game.selectCol(col, null);
        board[row][col].setIcon((game.getPlayer() == 0) ? redIcon : blueIcon);

        game.switchPlayer();
        setButtonColor((game.getPlayer() == 0) ? Color.RED : Color.BLUE);
    }

    /*******************************************************************
     * Sets the color of all selection buttons
     * @param color Color to set buttons to
     ******************************************************************/
    private void setButtonColor(Color color)
    {
        for (JButton btn : selection)
        {
            btn.setForeground(color);
        }
    }
}
