/***********************************************************************
 * Represents the visualization of a column in 3D space
 *
 * @author Silas Agnew
 * @version February 26, 2018
 **********************************************************************/

package project2.threeD;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class ColumnPanel extends JPanel
{
    private JLabel[] spaces;
    private int      size;
    private int      played;

    private ImageIcon blankIcon;
    private ImageIcon blueIcon;
    private ImageIcon redIcon;

    /*******************************************************************
     * Constructs column as a panel of {@code size} height.
     * @param size Size of the board and height of column
     ******************************************************************/
    public ColumnPanel(int size)
    {
        this.played = 0;
        this.size = size;
        setLayout(new GridLayout(1, size, 0, 0));

        blankIcon = new ImageIcon("img/3d/blank.png", "");
        blueIcon = new ImageIcon("img/3d/blue.png", "");
        redIcon = new ImageIcon("img/3d/red.png", "");

        spaces = new JLabel[size];

        for (int i = 0; i < size; i++)
        {
            spaces[i] = new JLabel("" + i, blankIcon, JLabel.CENTER);
            spaces[i].setHorizontalTextPosition(JLabel.CENTER);
            add(spaces[i]);
        }
    }

    /*******************************************************************
     * @return Whether or not there is an empty space in the column
     ******************************************************************/
    public boolean isPlayable() { return played < size; }

    /*******************************************************************
     * Places the {@code player}'s piece in the lowest available row
     * in the column
     * @param player the player whose piece is placed
     ******************************************************************/
    public void play(int player)
    {
        if (played >= size) return;
        spaces[played].setIcon((player == 0) ? redIcon : blueIcon);
        played++;
    }

    /*******************************************************************
     * Resets the spaces in this column
     ******************************************************************/
    public void reset()
    {
        for (JLabel space : spaces)
            space.setIcon(blankIcon);
        played = 0;
    }
}
