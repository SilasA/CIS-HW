/***********************************************************************
 * The main Game GUI for Connect Four 3D
 *
 * @author Silas Agnew
 * @version February 26, 2018
 **********************************************************************/

package project2.threeD;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourPanel3D extends JFrame implements ActionListener
{
    public final static int SIZE = 7;

    private ConnectFourGame3D game;

    private JPanel          gamePanel;
    private ButtonFrame     buttonFrame;
    private ColumnPanel[][] board;

    /**
     * True: Human vs Human
     * False: Human vs CPU
     */
    private boolean gameMode = false;
    private boolean gameOver = false;

    private JMenuBar  menu;
    private JMenu     fileMenu;
    private JMenu     aboutMenu;
    private JMenuItem toggleModeItem;
    private JMenuItem resetItem;
    private JMenuItem quitItem;
    private JMenuItem aboutItem;

    /*******************************************************************
     * Constructs all components of the game GUI
     ******************************************************************/
    public ConnectFourPanel3D()
    {
        game = new ConnectFourGame3D(SIZE);

        // Menus
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

        // Game
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(SIZE, SIZE, 3, 0));

        buttonFrame = new ButtonFrame(this, SIZE);
        buttonFrame.setColor(pickColor());

        board = new ColumnPanel[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                board[i][j] = new ColumnPanel(SIZE);
                gamePanel.add(board[i][j]);
            }
        }

        add(gamePanel, BorderLayout.SOUTH);
    }


    /*******************************************************************
     * Runs the Frame
     ******************************************************************/
    public void Run()
    {
        buttonFrame.Run();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Connect Four 3D");
        pack();
        setVisible(true);
    }

    /*******************************************************************
     * Handles any user interaction
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
            for (ColumnPanel[] panels : board)
            {
                for (ColumnPanel panel : panels)
                {
                    panel.reset();
                }
            }
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
                    "Connect Four 3D - Silas Agnew.");
        }
        else
        {
            // Check all column select buttons
            int[] coord = buttonFrame.findActiveButton(e);

            if (board[coord[0]][coord[1]].isPlayable())
            {
                if (game.selectCol(coord[0], coord[1], null) >= 0)
                {
                    board[coord[0]][coord[1]].play(game.getPlayer());
                    checkWinner();
                    game.switchPlayer();
                    buttonFrame.setColor(pickColor());
                    if (!gameMode && !gameOver)
                        computerTurn();
                    return;
                }
            }
        }
    }

    /*******************************************************************
     * Runs the computer's turn
     ******************************************************************/
    private void computerTurn()
    {
        int[] coord = game.computerTurn();

        game.selectCol(coord[0], coord[1], null);
        board[coord[0]][coord[1]].play(game.getPlayer());

        game.switchPlayer();
        buttonFrame.setColor(pickColor());
    }

    /*******************************************************************
     * Checks if the player has won
     ******************************************************************/
    private void checkWinner()
    {
        if (game.isWinner(game.getPlayer(), null))
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Player " + (game.getPlayer() + 1) + " has won the game!");
            game.reset();
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++)
                    board[i][j].reset();
            gameOver = true;
        }
    }

    /*******************************************************************
     * @return A color depending on which player's turn it is
     ******************************************************************/
    private Color pickColor()
    {
        return (game.getPlayer() == 0) ? Color.RED : Color.BLUE;
    }
}
