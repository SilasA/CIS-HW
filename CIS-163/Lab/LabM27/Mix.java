
import java.io.*;
import java.util.Scanner;

public class Mix {

    private List< Character > message;
    private String undoCommands;
    

    private String userMessage;
    private Scanner scan;

    public Mix() {

        scan = new Scanner(System.in);
        message = new List< Character >();

        undoCommands = "";
    }

    public static void main(String[] args) {
        Mix mix = new Mix();

        List<Character> l = new List<Character>();
        for (int i = 0; i < 8; i++)
        {
            l.add(0, 'a');
        }
        mix.message = l;

        mix.userMessage = "r";
        mix.createMessage(mix.userMessage, mix.message);
        mix.mixture();

        System.out.println(mix.message);
    }

    public void createMessage(String userMessage, List< Character > message) {
        for (char c : userMessage.toCharArray())
            message.append(c);
    }

    private void mixture() {                
        do {
            DisplayMessage();
            System.out.print("Command: ");

            // save state
            List< Character > currMessage =  new List< Character >();
            createMessage(message.toString(), currMessage);
            String currUndoCommands = undoCommands;
            
            try {
                String command = scan.next("[Qbrpcxh]");

                switch (command) {
                case "Q":
                    save(scan.next());
                    System.out.println ("Final mixed up message: \'" + message+"\'");
                    System.exit(0);
                case "b":
                    insertbefore(scan.next(), scan.nextInt());
                    break;
                case "r":
                    remove(scan.nextInt(), scan.nextInt());
                    break;
                case "c":
                    copy(scan.nextInt(), scan.nextInt(), scan.nextInt());
                    break;
                case "x":
                    cut(scan.nextInt(), scan.nextInt(), scan.nextInt());
                    break;
                case "p":
                    paste(scan.nextInt(), scan.nextInt());
                    break;
                case "h":
                    helpPage();
                    break;
                }
                scan.nextLine();   // should flush the buffer
                System.out.println("For demostration purposes only:\n" + undoCommands);
            }
            catch (Exception e ) {
                System.out.println ("Error on input, previous state restored.");
                scan = new Scanner(System.in);  // should completely flush the buffer
                
                // restore state;
                undoCommands = currUndoCommands;
                message = currMessage ;
            }

        } while (true);
    }

    private void remove(int start, int stop)
    {
        for (int i = 0; i <= stop - start; i++) {
            char rem = message.remove(start);
            if (rem == ' ')
                rem = '~';

            undoCommands = "b " + rem + " " + start + "\n" + undoCommands;
        }
    }

    private void cut(int clipNum, int start, int stop) {
        
    }

    private void copy(int clipNum, int start, int stop) {
       
    }

    private void paste(int clipNum, int index) {
            
    }
         
    private void insertbefore(String clipBD, int index) {
        if (message.size() == 0)  // special case
            index = -1;
  
        for (int i = clipBD.length() - 1; i >= 0; i--) {
            char rChar = clipBD.charAt(i);  
            if (rChar == '~')
                rChar = ' ';  // to handle spaces in output.  
            message.add (index, rChar);
            undoCommands += "r " + index + " " + index  + "\n" +
            undoCommands;   // does the opposite
        }
    }

    private void DisplayMessage() {
        System.out.print ("Message:\n");
        userMessage = message.toString();

        for (int i = 0; i < userMessage.length(); i++) 
            System.out.format ("%3d", i);
        System.out.format ("\n");
        for (char c : userMessage.toCharArray()) 
            System.out.format("%3c",c);
        System.out.format ("\n");
    }

    public void save(String filename) {

        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(undoCommands);
        out.close();
    }

    private void helpPage() {
        System.out.println("Commands:");
        System.out.println("\tQ filename    means, quit! " + " save to filename" );         
        System.out.println("\t  ~ is used for a space character" );     
        System.out.println("\t .... etc" );     
        System.out.println("\th\tmeans to show this help page");
    }
}
