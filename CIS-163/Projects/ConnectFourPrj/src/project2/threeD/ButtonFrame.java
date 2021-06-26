/***********************************************************************
 * A Frame dedicated to the 2 dimensional button table that represents
 * column choices for 3D Connect Four.
 * The events triggered by buttons should be handled by the
 * {@link java.awt.event.ActionListener} passed to the constructor as
 * {@code parent}.
 *
 * @author Silas Agnew
 * @version February 26, 2018
 **********************************************************************/

package project2.threeD;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonFrame extends JFrame
{
    private JButton[] buttons;
    private int       size;

    /*******************************************************************
     * Constructs a table of buttons of dimensions {@code size * size}.
     * @param size height and width of table
     ******************************************************************/
    public ButtonFrame(ActionListener parent, int size)
    {
        this.size = size;
        buttons = new JButton[size * size];

        setLayout(new GridLayout(size, size, 0, 0));

        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton("Select");
            buttons[i].addActionListener(parent);
            add(buttons[i]);
        }
    }

    /*******************************************************************
     * Initializes and runs the JFrame
     ******************************************************************/
    public void Run()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("3D Button Map");
        pack();
        setVisible(true);
    }

    /*******************************************************************
     * Checks for a button that was clicked
     * @param e event
     * @return The x y coordinates of the column; -1 if none
     *         NOTE: if -1 is returned then there is a problem with the
     *         parent event handler
     ******************************************************************/
    public int[] findActiveButton(ActionEvent e)
    {
        for (int i = 0; i < size * size; i++)
            if (buttons[i] == e.getSource())
                return new int[] { i / size, i % size };
        return new int[] { -1, -1 };
    }

    /*******************************************************************
     * Sets the color of all the buttons
     * @param color
     ******************************************************************/
    public void setColor(Color color)
    {
        for (JButton button : buttons)
        {
            button.setForeground(color);
        }
    }
}
