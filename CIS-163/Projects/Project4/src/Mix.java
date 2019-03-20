/***********************************************************************
 * Class for encrypting and saving decryption directions.
 *
 * @author Silas Agnew and a CIS professor
 * @version April 17, 2018
 **********************************************************************/

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Mix
{
    private HashMap< Integer, List< Character > > clipboards;

    private List< Character > message;
    private String undoCommands;

    private String userMessage;
    private Scanner scan;

    public static void main(String[] args)
    {
        Mix mix = new Mix();
        mix.userMessage = args[0];
        mix.createMessage(mix.userMessage, mix.message);
        mix.mixture();

        System.out.println(mix.message);
    }

    /*******************************************************************
     * Constructs a mixing object
     ******************************************************************/
    public Mix() {

        scan = new Scanner(System.in);
        message = new List< >();
        clipboards = new HashMap< >();

        undoCommands = "";
    }

    /*******************************************************************
     * Puts the user message into a linked list.
     * @param userMessage message to insert into linked list
     * @param message target list to insert message into
     ******************************************************************/
    public void createMessage(String userMessage, List< Character > message) {
        for (char c : userMessage.toCharArray())
            message.append(c);
    }

    /*******************************************************************
     * Main loop that allows the user to manipulate the message.
     ******************************************************************/
    private void mixture() {                
        do {
            DisplayMessage();
            System.out.print("Command: ");

            // save state
            List< Character > currMessage =  new List< Character >();
            createMessage(message.toString(), currMessage);
            String currUndoCommands = undoCommands;
            
            try {
                String command = scan.next("[Qbrpcxhiol][a]?");

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
                case "i":
                    invert(scan.nextInt(), scan.nextInt());
                    break;
                case "ia":
                    invert(0, userMessage.length() - 1);
                    break;
                case "o":
                    offset(scan.nextInt(), scan.nextInt(), scan.nextInt());
                    break;
                case "oa":
                    offset(scan.nextInt(), 0, userMessage.length() - 1);
                    break;
                case "l":
                    l33tify(scan.nextInt(), scan.nextInt());
                    break;
                case "la":
                    l33tify(0, userMessage.length() - 1);
                    break;
                case "h":
                    helpPage();
                    break;
                }
                scan.nextLine();   // should flush the buffer
                System.out.println("For demostration purposes only:\n" + undoCommands);
            }
            catch (Exception e) {
                System.out.println ("Error on input, previous state restored.");
                scan = new Scanner(System.in);  // should completely flush the buffer
                
                // restore state;
                undoCommands = currUndoCommands;
                message = currMessage ;
            }

        } while (true);
    }

    /*******************************************************************
     * Removes a sequence of characters from the message
     * @param start start index of sequence
     * @param stop inclusive stop index of sequence
     ******************************************************************/
    private void remove(int start, int stop)
    {
        for (int i = 0; i <= stop - start; i++)
        {
            char rem = message.remove(start);
            if (rem == ' ')
                rem = '~';

            undoCommands = "b " + rem + " " + start + "\n" + undoCommands;
        }
    }

    /*******************************************************************
     * Copies an sequence of characters to a clipboard and cuts them out
     * of the original.
     * @param clipNum clipboard to store sequence in
     * @param start start index of sequence
     * @param stop inclusive stop index of sequence
     ******************************************************************/
    private void cut(int clipNum, int start, int stop)
    {
        copy(clipNum, start, stop);
        remove(start, stop);
    }

    /*******************************************************************
     * Copies a sequence of characters to a clipboard.
     * @param clipNum clipboard to store sequence in
     * @param start start index of sequence
     * @param stop inclusive stop index of sequence
     ******************************************************************/
    private void copy(int clipNum, int start, int stop)
    {
        clipboards.put(clipNum, message.subList(start, stop));
    }

    /*******************************************************************
     * Pastes the contents of a clipboard into the message.
     * @param clipNum clipboard to paste sequence from
     * @param index where to start paste
     ******************************************************************/
    private void paste(int clipNum, int index)
    {
        List< Character > clip = clipboards.get(clipNum);
        int size = clip.size();
        if (clip == null)
            throw new IllegalArgumentException("Clipboard does not exist");
        message.addAll(index, clip);

        undoCommands = "r " + index + " " + (index + size - 1) + "\n" + undoCommands;
    }

    /*******************************************************************
     * Inserts the clip before the specified index
     * @param clipBD clip to insert
     * @param index start index to insert clip
     ******************************************************************/
    private void insertbefore(String clipBD, int index) {
        if (message.size() == 0)  // special case
            index = -1;
  
        for (int i = clipBD.length() - 1; i >= 0; i--) {
            char rChar = clipBD.charAt(i);  
            if (rChar == '~')
                rChar = ' ';  // to handle spaces in output.  
            message.add (index, rChar);
            undoCommands = "r " + index + " " + index  + "\n" + undoCommands;
        }
    }

    /*******************************************************************
     * Inverts the sequence of the list between start and stop.
     *  'abc' becomes 'cba'
     * @param start start index of sequence
     * @param stop inclusive stop index of sequence
     ******************************************************************/
    private void invert(int start, int stop)
    {
        List< Character > seq = message.subList(start, stop);
        for (int i = 0; i <= stop - start; i++)
            message.remove(start);
        seq.reverse();
        message.addAll(start, seq);
        undoCommands = "i " + start + " " + stop + "\n" + undoCommands;
    }

    /*******************************************************************
     * Offsets the ASCII value of each character in the sequence by
     * {@code offset}.  Acts as a caeser cipher.
     * @param start start index of sequence
     * @param stop inclusive stop index of sequence
     ******************************************************************/
    private void offset(int offset, int start, int stop)
    {
        for (int i = start; i <= stop; i++)
            if (message.get(i) != ' ')
                message.add(i, (char)(message.remove(i) + offset));

        undoCommands = "o " + -offset + " " + start + " " + stop + "\n" + undoCommands;
    }

    /*******************************************************************
     * L33tifies the sequence of characters
     * replaces e, a, l, o, s to their l33tsp34k numerical equivalence
     * @param start start index of sequence
     * @param stop inclusive stop index of sequence
     ******************************************************************/
    private void l33tify(int start, int stop)
    {
        for (int i = start; i < stop; i++)
        {
            char c = message.remove(i);
            char newChar = c;

            switch (c)
            {
                case 'a':
                    newChar = '4';
                    break;
                case 'e':
                    newChar = '3';
                    break;
                case 'l':
                    newChar = '1';
                    break;
                case 'o':
                    newChar = '0';
                    break;
                case 's':
                    newChar = '5';
                    break;
                case '4':
                    newChar = 'a';
                    break;
                case '3':
                    newChar = 'e';
                    break;
                case '1':
                    newChar = 'l';
                    break;
                case '0':
                    newChar = 'o';
                    break;
                case '5':
                    newChar = 's';
                    break;
                default:
                    break;
            }

            message.add(i, newChar);
        }
        undoCommands = "l " + start + " " + stop + "\n" + undoCommands;
    }

    /*******************************************************************
     * Displays the current state of the message and undo commands
     ******************************************************************/
    private void DisplayMessage()
    {
        System.out.print ("Message:\n");
        userMessage = message.toString();

        for (int i = 0; i < userMessage.length(); i++) 
            System.out.format ("%3d", i);
        System.out.format ("\n");
        for (char c : userMessage.toCharArray()) 
            System.out.format("%3c",c);
        System.out.format ("\n");
    }

    /*******************************************************************
     * Save the decryption directions to a file
     * @param filename name of file to save to
     ******************************************************************/
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

    /*******************************************************************
     * Explanations of all the commands available
     ******************************************************************/
    private void helpPage()
    {
        System.out.println("Commands:");
        System.out.println("\t~ is used for a space character");
        System.out.println("\tQ filename\t\t\t\t means, quit! " + " save to filename");
        System.out.println("\tb clip index\t\t\t insert clip before index");
        System.out.println("\tr start stop\t\t\t remove sequence from start to stop");
        System.out.println("\tc clipboard start stop\t copy the sequence from start to stop to clipboard number");
        System.out.println("\tx clipboard start stop\t cut the sequence from start to stop to clipboard number");
        System.out.println("\tp clipboard index\t\t paste contents of clipboard number to the index");
        System.out.println("\ti start stop\t\t\t invert the sequence between start and stop ex: 'abc' becomes 'cba'");
        System.out.println("\to offset start stop\t\t offset the sequence between start and stop as a caeser cipher");
        System.out.println("\tl start stop\t\t\t paste contents of clipboard number to the index");
        System.out.println("\tadd 'a' to to the end of a command as shorthand for modifying the whole message"
                           + "\n\t example: 'ia' with no indexing arguments will invert the entire message");
        System.out.println("\th\tshow this help page");
    }
}
