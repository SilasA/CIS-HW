
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//import modelPackage.*;

public class xConnectFourPanelIconEXAMPLE extends JPanel {

    private final int SIZE = 10;    
    private JLabel[][] matrix;
    private JButton[] selection;

    private Boolean person = true;
    private JMenuItem gameItem;
    private JMenuItem quitItem;
    private ImageIcon iconBlank;
    private ImageIcon iconPlayer1;
    private ImageIcon iconPlayer2;

    private xConnectFourGameEXAMPLE game;

    public xConnectFourPanelIconEXAMPLE(JMenuItem pquitItem, JMenuItem pgameItem){

        game = new xConnectFourGameEXAMPLE(10);   

        gameItem = pgameItem;
        quitItem = pquitItem;

        setLayout(new GridLayout(SIZE+1,SIZE));  // room for top row

        iconBlank = new ImageIcon ("blank.png");
        iconPlayer1 = new ImageIcon ("blue.png");
        iconPlayer2 = new ImageIcon ("red.png");

        ButtonListener listener = new ButtonListener();
        quitItem.addActionListener(listener);
        gameItem.addActionListener(listener);       

        selection = new JButton[SIZE];
        for (int col = 0; col < SIZE; col++) {
            selection[col] = new JButton ("Select");
            selection[col].addActionListener(listener);
            add(selection[col]);
        }

        matrix = new JLabel[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                matrix[row][col] = new JLabel("",iconBlank,SwingConstants.CENTER);
                add(matrix[row][col]);					
            }
        }
    }

	
    //*****************************************************************
    //  Represents a listener for button push (action) events.
    //*****************************************************************
    private class ButtonListener implements ActionListener
    {
        //--------------------------------------------------------------
        //  Updates the counter and label when the button is pushed.
        //--------------------------------------------------------------
        public void actionPerformed (ActionEvent event)
        {

            JComponent comp = (JComponent) event.getSource();

            for (int col = 0; col < SIZE; col++) {
                if (selection[col] == comp) {

                    int row = game.selectCol(col);
                    if (row == -1)
                        JOptionPane.showMessageDialog(null, "Col is full!");
                    else 
                        matrix[row][col].setIcon((game.getCurrentPlayer() == 1) ? iconPlayer1 : iconPlayer2);

                    if (game.isWinner()) {
                        JOptionPane.showMessageDialog(null, "Player" + game.getCurrentPlayer() + " Wins!");
                    }

                    game.nextPlayer();
                }
            }

            if (comp == gameItem) {    
                game.reset();

                for (int row = 1; row < SIZE; row++) 
                    for (int col = 0; col < SIZE; col++) 
                        matrix[row][col].setIcon( iconBlank);
            }

            if (comp == quitItem)
                System.exit(1);
        }

    }

}
