/**
 * Main entry point for application.
 *
 * @author Silas Agnew
 * @version 1.0.0
 */

package com.company;

import javax.swing.JFrame;

/**
 * Main entry point for application which initializes the window and view
 */
public class Main
{
    public static void main(String[] args)
	{
	    // Main card window
		JFrame mainWindow = new JFrame();
		ViewWindow vw = new ViewWindow();
        mainWindow.setContentPane(vw);
        mainWindow.setSize(1280, 720);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
}
