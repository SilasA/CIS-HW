/***********************************************************************
 * Entry point for the connect four application.  Creates a launcher for
 * the user to decide whether they want to play 2d or 3d connect four or
 * if they really want to play at all.
 * @author Silas Agnew
 * @version February 26, 2018
 **********************************************************************/

package project2;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import project2.threeDEngine.ConnectFourPanel3D;

public class ConnectFour
{
    public static void main(String[] args)
    {
        String[] options = new String[]
        {
                "2D", "3D", "Close"
        };

        int response = JOptionPane.showOptionDialog(
                null, "2D or 3D", "Launcher",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (response == 0)
        {
            twoD();
        }
        else if (response == 1)
        {
            threeD();
        }
        //threeDEngine();
    }

    public static void twoD()
    {
        ConnectFourPanel panel = new ConnectFourPanel();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setTitle("Connect Four");
        panel.pack();
        panel.setVisible(true);
    }

    public static void threeDEngine()
    {
        ConnectFourPanel3D panel = new ConnectFourPanel3D(1);
        panel.Run();
    }

    public static void threeD()
    {
        project2.threeD.ConnectFourPanel3D panel =
                new project2.threeD.ConnectFourPanel3D();
        panel.Run();
    }
}
