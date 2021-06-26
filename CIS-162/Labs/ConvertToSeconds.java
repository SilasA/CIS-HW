/**
 * Lab #2
 * Converts elapsed time from format Hours:Minutes:Seconds to Seconds
 *
 * @author Silas A.
 */

import java.util.Scanner;

public class ConvertToSeconds
{
	public static void main(String[] args)
	{
		Scanner scnr = new Scanner(System.in);
		
		// Input
		System.out.println("Hours: ");
		int hours = scnr.nextInt();
		System.out.println("Minutes: ");
		int minutes = scnr.nextInt();
		System.out.println("Seconds: ");
		int seconds = scnr.nextInt();
		
		// Calculation
		int result = HoursToSec(hours) + MinutesToSec(minutes) + seconds;
		
		// Display
		System.out.println("Equivalence: " + result + " seconds");
	}
	
	public static int HoursToSec(int hours) { return 60 * 60 * hours; }
	public static int MinutesToSec(int mins) { return 60 * mins; }
}
