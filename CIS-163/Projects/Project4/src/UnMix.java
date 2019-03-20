import java.io.*;
import java.util.Scanner;

public class UnMix
{
	private List < Character > message;

	public UnMix() {
		message = new List < > ();
	}

	public static void main(String[] args) {
		UnMix v = new UnMix();
		v.unMixture(args[0], args[1]);
	}

	/*******************************************************************
	 * Process the user input.
	 * @param command Command input
	 * @return The new, modified message
	 ******************************************************************/
	public String processCommand(String command)
	{
		Scanner scan = new Scanner(command);
		char charInput;

		try {
			command = scan.next();
			switch (command.charAt(0))
			{
			case 'b':
				insertbefore(scan.next(), scan.nextInt());
				break;
			case 'r':
				remove(scan.nextInt(), scan.nextInt());
				break;
			case 'o':
				offset(scan.nextInt(), scan.nextInt(), scan.nextInt());
				break;
			case 'i':
				invert(scan.nextInt(), scan.nextInt());
				break;
			case 'l':
				l33tify(scan.nextInt(), scan.nextInt());
				break;
			}
		} catch (Exception e) {
			System.out.println("Error in command!  Problem!!!! in undo commands");
			System.exit(0);
		}
		finally {
			scan.close();
		}

		return message.toString();
	}

	/*******************************************************************
	 * Remove the sequence between {@code start} and {@code stop}.
	 * @param start Start point of sequence
	 * @param stop Inclusive end point of the sequence
	 ******************************************************************/
	private void remove(int start, int stop)
	{
		for (int i = 0; i <= stop - start; i++)
		{
			message.remove(start);
		}
	}

	/*******************************************************************
	 * Insert clip before {@code index} point.
	 * @param clipBD Clip to insert
	 * @param index Start insertion point
	 ******************************************************************/
	private void insertbefore(String clipBD, int index)
	{
		if (message.size() == 0)  // special case
			index = -1;

		for (int i = clipBD.length() - 1; i >= 0; i--) {
			char rChar = clipBD.charAt(i);
			if (rChar == '~')
				rChar = ' ';  // to handle spaces in output.
			message.add (index, rChar);
		}
	}

	/*******************************************************************
	 * Offset the sequence by {@code offset} ASCII values like a caeser
	 * cipher.
	 * @param offset Value to offset sequence
	 * @param start Start point of the sequence
	 * @param stop Inclusive end point of the sequence
	 ******************************************************************/
	private void offset(int offset, int start, int stop)
	{
		for (int i = start; i <= stop; i++)
			if (message.get(i) != ' ')
				message.add(i, (char)(message.remove(i) + offset));
	}

	/*******************************************************************
	 * Reverse the sequence of characters such that 'abc' becomes 'cba'
	 * @param start Start point of the sequence
	 * @param stop Inclusive end point of the sequence
	 ******************************************************************/
	private void invert(int start, int stop)
	{
		List< Character > seq = message.subList(start, stop);
		for (int i = 0; i <= stop - start; i++)
			message.remove(start);
		seq.reverse();
		message.addAll(start, seq);
	}

	/*******************************************************************
	 * Change certain letters into numerical synonyms and vice versa.
	 * @param start Start point of the sequence
	 * @param stop Inclusive end point of the sequence
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
	}

	/*******************************************************************
	 * Unmix the mixed sequence of characters.
	 * @param filename Filename to get the decryption directions from
	 * @param userMessage Decrypted message
	 ******************************************************************/
	private void unMixture(String filename, String userMessage)
	{
		String original = UnMixUsingFile (filename, userMessage);
		System.out.println ("The Original message was: " + original);
	}

	/*******************************************************************
	 * Set the initial message
	 * @param userMessage initial message
	 ******************************************************************/
	private void setInitialMessage(String userMessage)
	{
		for (char c : userMessage.toCharArray())
			message.add(message.size(), c);
	}

	/***********************************************************II*I****
	 * Unmix the encrypted message with directions from a file.
	 * @param filename File with decryption directions
	 * @param userMessage Encrypted message
	 * @return The decrypted message
	 ******************************************************************/
	public String UnMixUsingFile(String filename, String userMessage)
	{
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		setInitialMessage(userMessage);

		while (scanner.hasNext()) {
			String command = scanner.nextLine();
			userMessage = processCommand(command);
		} 
		return userMessage;
	}
}
