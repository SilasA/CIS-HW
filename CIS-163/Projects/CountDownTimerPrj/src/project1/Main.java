/**
 * The entry point for the application.
 *
 * @author Silas Agnew
 * @version January 13, 2018
 */

package project1;

import javax.swing.JFrame;

public class Main
{
    public static void main(String[] args)
    {
        //test();
        gui();
    }
    
    public static void test()
    {
        CountDownTimer s = new CountDownTimer("2:59:8");
        System.out.println("Time: " + s);
        s = new CountDownTimer("20:8");
        System.out.println("Time: " + s);
        s = new CountDownTimer("8");
        System.out.println("Time: " + s);
        CountDownTimer s1 = new CountDownTimer(25, 2, 20);
        System.out.println("Time: " + s1);
        s1.sub(1000);
        System.out.println("Time: " + s1);
        s1.add(1000);
        System.out.println("Time: " + s1);
        CountDownTimer s2 = new CountDownTimer(40, 10, 20);
        s2.sub(100);
        for (int i = 0; i < 4000; i++)
            s2.dec();
        System.out.println("Time: " + s2);
        s2.sub(100);
        for (int i = 0; i < 4000; i++)
            s2.inc();
        System.out.println("Time: " + s2);
        
        try
        {
            new CountDownTimer("-30");
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println("Exception thrown");
        }
    
        try
        {
            new CountDownTimer("-3:30");
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println("Exception thrown");
        }
    
        try
        {
            new CountDownTimer("-1:23:30");
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println("Exception thrown");
        }
        
        CountDownTimer.suspend(true);
        System.out.println(s1.toString());
        s1.add(100);
        System.out.println(s1.toString());
        CountDownTimer.suspend(false);
        
        s1 = new CountDownTimer("1:23:45");
        s2 = new CountDownTimer("1:23:45");
    
        System.out.println(s1.compareTo(s2));
        s2.inc();
        System.out.println(s1.compareTo(s2));
        s2.sub(2);
        System.out.println(s1.compareTo(s2));
    
        s1.dec();
        System.out.println(s1.equals(s2));
    }
    
    public static void gui()
    {
        CountDownTimerGUI gui = new CountDownTimerGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Countdown Timer");
        gui.pack();
        gui.setVisible(true);
    }
}
