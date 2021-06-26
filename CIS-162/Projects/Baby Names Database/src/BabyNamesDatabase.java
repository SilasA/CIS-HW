/**
 * Class that manages a database of {@code BabyName}s.
 * Provides some sorting and querying functionality for the database.
 *
 * @author Silas Agnew
 * @version November 9, 2017
 */

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BabyNamesDatabase
{
	ArrayList<BabyName> babyNames = null;

	//-Constructor(s)----------------------------------------------//

	/**
	 * Constructs a empty database of baby names in memory.
	 * Call {@link BabyNamesDatabase#readBabyNameData(String)} to populate
	 * database.
	 */
	public BabyNamesDatabase()
	{
		babyNames = new ArrayList<>();
	}

	//-Accessors---------------------------------------------------//

	/**
	 * @return Total count of name entries in the database
	 */
	public int countAllNames() { return babyNames.size(); }

	/**
	 * @return Total count of boy babies in the database
	 */
	public int countAllBoys()
	{
		int count = 0;
		for (BabyName b : babyNames)
			if (!b.isFemale()) count += b.getCount();
		return count;
	}

	/**
	 * @return Total count of girl babies in the database
	 */
	public int countAllGirls()
	{
		int count = 0;
		for (BabyName b : babyNames)
			if (b.isFemale()) count += b.getCount();
		return count;
	}

	/**
	 * @param year Year to search in
	 * @return The most popular girl name of {@code year}
	 */
	public BabyName mostPopularGirl(int year)
	{
		BabyName top = new BabyName("null", true, -1, year);
		for (BabyName b : babyNames)
		{
			if (b.isFemale() && b.getYear() == year && top.compareTo(b) > 0)
				top = b;
		}
		return top;
	}
	
	/**
	 * @param year Year to search in
	 * @return The most popular boy name of {@code year}
	 */
	public BabyName mostPopularBoy(int year)
	{
		BabyName top = new BabyName("null", true, 0, year);
		for (BabyName b : babyNames)
		{
			if (!b.isFemale() && b.getYear() == year && top.compareTo(b) > 0)
				top = b;
		}
		return top;
	}

	/**
	 * @param name Name to search for
	 * @return A list of all entries of {@code name}
	 */
	public ArrayList<BabyName> searchForName(String name)
	{
		ArrayList<BabyName> names = new ArrayList<>();
		for (BabyName b : babyNames)
			if (b.getName().equalsIgnoreCase(name))
				names.add(b);
		return names;
	}

	/**
	 * @param year The year to search for
	 * @return A list of all entries of {@code year}
	 */
	public ArrayList<BabyName> searchForYear(int year)
	{
		ArrayList<BabyName> names = new ArrayList<>();
		for (BabyName b : babyNames)
			if (b.getYear() == year)
				names.add(b);
		return names;
	}

	/**
	 * Sorts the list of baby names in a year for the top ten
	 * @param year Year to search for
	 * @return Top ten popular names. Returns an empty array if no names
	 */
	public ArrayList<BabyName> topTenNames(int year)
	{
		ArrayList<BabyName> topTen = searchForYear(year);
		topTen.sort(null);
		return new ArrayList<>(
				(topTen.size() > 10) ? topTen.subList(0, 10) : topTen);
	}

	//-Mutator(s)--------------------------------------------------//

	/**
	 * Populates {@code babyNames} with CSV data from the file: {@code filename}
	 * This method will terminate if there isn't a file found with name:
	 * 	{@code filename}
	 * @param  filename The name of the file to read data from
	 */
	public void readBabyNameData(String filename)
	{
		FileInputStream file;
		Scanner scnr;
		int errorCount = 0;
		int lineCount = 0;

		// Included in case specific line numbers were needed for debugging
		ArrayList<Integer> errorLines = new ArrayList<>();

		// Open CSV file
		try {
			file = new FileInputStream(filename);
		}
		catch (FileNotFoundException ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage());
			return;
		}
		scnr = new Scanner(file);

		// Scan line per line
		while (scnr.hasNextLine())
		{
			try {
				babyNames.add(BabyName.BabyNameBuilder(scnr.nextLine(), lineCount));
			}
			catch (IllegalArgumentException ex)
			{
				errorCount++;
				errorLines.add(lineCount);
			}
			lineCount++;
		}

		// Warn of errors (if any)
		if (errorCount > 0)
		{
			JOptionPane.showMessageDialog(
					null, errorCount +
							" entries were not included due to formatting errors.");
		}
		
		try
		{
			file.close();
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(null,
					"If you're reading this, you probably deleted the file " +
							"before this program was finished with it. Congrats!");
		}
	}
	
	//-Test Methods------------------------------------------------//

	/**
	 * A test method that prints out general tests to the console.
	 */
	public void printNames()
	{
		System.out.println("Total Counts\n");
		System.out.println("Total Girls: " + countAllGirls());
		System.out.println("Total Boys: " + countAllBoys());
		System.out.println("Total Names: " + countAllNames());
		
		System.out.println("Most Pop. Girl: " + mostPopularGirl(1999));
		System.out.println("Most Pop. Boy: " + mostPopularBoy(1999));
		System.out.println();
		
		ArrayList<BabyName> l = searchForName("silas");
		for (BabyName b : l)
			System.out.println(b);
		System.out.println();
		l.clear();
		
		l = topTenNames(1999);
		for (BabyName b : l)
			System.out.println(b);
	}
}
