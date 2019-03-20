

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//import viewPackage.*;

public class xConnectFourEXAMPLE {

    //-----------------------------------------------------------------
    //  Creates and displays the main program frame.
    //-----------------------------------------------------------------
    public static void main (String[] args)
    {

        JMenuBar menus;
        JMenu fileMenu;
        JMenuItem quitItem;
        JMenuItem gameItem;   

        JFrame frame = new JFrame ("Connect Four");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("quit");
        gameItem = new JMenuItem("new game");

        fileMenu.add(gameItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        frame.setJMenuBar(menus);
        menus.add(fileMenu);

        xConnectFourPanelIconEXAMPLE panel = new xConnectFourPanelIconEXAMPLE(quitItem,gameItem);
        frame.getContentPane().add(panel);

        frame.setSize(800, 600);
        frame.setVisible(true);
    } 
}

