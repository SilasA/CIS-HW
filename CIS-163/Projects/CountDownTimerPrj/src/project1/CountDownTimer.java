/**
 * A Timer that counts down from the time it is constructed with
 * Counting down is done with {@link project1.CountDownTimer#dec()}
 * or {@link project1.CountDownTimer#sub(int)}
 *
 * @author Silas Agnew
 * @version January 18, 2018
 */

package project1;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CountDownTimer implements
		Comparable<CountDownTimer>, ActionListener

{
	/**
	 * Controls external access to timer manipulation
	 * When true: all timer addition is suspended
	 * When false: addition allowed
	 */
	private static boolean susFlag = false;
	
	/**
	 * Hours to start countdown at
	 */
	private int hours;
	
	/**
	 * Minutes to start countdown at
	 */
	private int minutes;
	
	/**
	 * Seconds to start countdown at
	 */
	private int seconds;
	
	/*******************************************************************
	 * Constructs a CountDownTimer with the provided start time
	 * Implied time format is "h:mm:ss"
	 *
	 * @param hours   Hours from 0 to start from
	 * @param minutes Minutes from 0 to start from
	 * @param seconds Seconds from 0 to start from
	 * @throws IllegalArgumentException when minutes and/or seconds are
	 * 	not within range [0, 59]
	 ******************************************************************/
	public CountDownTimer(int hours, int minutes, int seconds)
			throws IllegalArgumentException
	{
		if (minutes > 59 || seconds > 59)
			throw new IllegalArgumentException(
					"Time values for minutes and seconds must be no more than 59");

		if (hours < 0 || minutes < 0 || seconds < 0)
			throw new IllegalArgumentException(
					"Timer values must be non-negative");
		
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	/*******************************************************************
	 * Constructs a CountDownTimer with the provided start time
	 * Implied time format is "mm:ss"
	 *
	 * @param minutes Minutes from 0 to start from
	 * @param seconds Seconds from 0 to start from
	 ******************************************************************/
	public CountDownTimer(int minutes, int seconds)
	{
		this(0, minutes, seconds);
	}
	
	/*******************************************************************
	 * Constructs a CountDownTimer with the provided start time
	 * Implied time format is "s"
	 *
	 * @param seconds Seconds from 0 to start from
	 ******************************************************************/
	public CountDownTimer(int seconds)
	{
		this(0, 0, seconds);
	}
	
	/*******************************************************************
	 * Constructs a CountDownTimer with a start time of 0
	 ******************************************************************/
	public CountDownTimer()
	{
		this(0, 0, 0);
	}
	
	/*******************************************************************
	 * Constructs a CountDownTimer with the start time provided in
	 * string format
	 * Accepted formats are as follows:
	 * h:mm:ss - calls {@link CountDownTimer#CountDownTimer(int, int, int)}
	 * mm:ss   - calls {@link CountDownTimer#CountDownTimer(int, int)}
	 * s       - calls {@link CountDownTimer#CountDownTimer(int)}
	 * [blank] - calls {@link CountDownTimer#CountDownTimer()}
	 *
	 * @param startTime Formatted string of the start time
	 * @throws IllegalArgumentException When a string value is not properly
	 * 		formatted to be parsed to a number or the number is out of bounds.
	 * 		Will also throw {@link NumberFormatException}.
	 ******************************************************************/
	public CountDownTimer(String startTime) throws IllegalArgumentException
	{
		this(); // Default all values to 0
		String[] values = startTime.split("[:]+");

		// Based off length, parse the needed values
		// Assumes the strings are properly formatted
		if (values.length >= 3)
		{
			setHours(Integer.parseInt(values[0]));
			setMinutes(Integer.parseInt(values[1]));
			setSeconds(Integer.parseInt(values[2]));
		}
		else if (values.length >= 2)
		{
			setMinutes(Integer.parseInt(values[0]));
			setSeconds(Integer.parseInt(values[1]));
		}
		else if (values.length >= 1)
		{
			setSeconds(Integer.parseInt(values[0]));
		}
	}
	
	/*******************************************************************
	 * Suspends all manipulation of timers when true
	 * @param flag Whether or not to suspend actions
	 ******************************************************************/
	public static void suspend(boolean flag)
	{
		susFlag = flag;
	}
	
	/*******************************************************************
	 * Checks 2 timer values for equivalence
	 *
	 * @param t1 First timer
	 * @param t2 Second timer
	 * @return equivalence
	 ******************************************************************/
	public static boolean equals(CountDownTimer t1, CountDownTimer t2)
	{
		return t1.toSeconds() == t2.toSeconds();
	}
	
	/*******************************************************************
	 * @param t1 First {@code CountDownTimer} to compare
	 * @param t2 Second {@code CountDownTimer} to compare
	 * @return {@code > 0} if {@code t1} is greater than {@code t2}
	 * 		{@code < 0} if {@code t2} is less than {@code t2}
	 * 	    and zero if they are equal
	 ******************************************************************/
	public static int compareTo(CountDownTimer t1, CountDownTimer t2)
	{
		return t1.toSeconds() - t2.toSeconds();
	}
	
	/*******************************************************************
	 * @return Hour value of this timer
	 ******************************************************************/
	public int getHours()
	{
		return hours;
	}
	
	/*******************************************************************
	 * Sets the hour value of the timer
	 * @param hours value to set to
	 * @return Whether or not the input was valid and was set
	 ******************************************************************/
	private void setHours(int hours)
	{
		if (hours < 0)
			throw new IllegalArgumentException(
					"hour value must be non-negative number");
		this.hours = hours;
	}
	
	/*******************************************************************
	 * @return Minute value of this timer
	 ******************************************************************/
	public int getMinutes()
	{
		return minutes;
	}
	
	/*******************************************************************
	 * Sets the minute value of the timer
	 * @param minutes value to set to
	 * @return Whether or not the input was valid and was set
	 ******************************************************************/
	private void setMinutes(int minutes)
	{
		if (minutes > 59 || minutes < 0)
			throw new IllegalArgumentException(
					"minute value must be a non-negative number less than 60");
		this.minutes = minutes;
	}
	
	/*******************************************************************
	 * @return Second value of this timer
	 ******************************************************************/
	public int getSeconds()
	{
		return seconds;
	}
	
	/*******************************************************************
	 * Sets the second value of the timer
	 * @param seconds value to set to
	 * @return Whether or not the input was valid and was set
	 ******************************************************************/
	private void setSeconds(int seconds)
	{
		if (seconds > 59 || seconds < 0)
			throw new IllegalArgumentException(
					"second value must be a non-negative number less than 60");
		this.seconds = seconds;
	}
	
	/*******************************************************************
	 * Subtracts the provided amount of seconds from the timers current value
	 *
	 * @param seconds Amount to subtract
	 ******************************************************************/
	public void sub(int seconds)
	{
		// Check if mutation is allowed
		if (susFlag) return;

		// Check if subtracting non-negative
		if (seconds <= 0) return;
		
		this.seconds -= seconds;
		
		while (this.seconds < 0)
		{
			minutes--;
			this.seconds += 60;
		}
		
		while (this.minutes < 0)
		{
			hours--;
			this.minutes += 60;
		}
		
		if (this.hours < 0)
		{
			setSeconds(0);
			setMinutes(0);
			setHours(0);
		}
	}
	
	/*******************************************************************
	 * Subtracts the value of another timer, {@code other}, from this
	 * timer
	 *
	 * @param other Timer that will be subtracted
	 ******************************************************************/
	public void sub(CountDownTimer other)
	{
		sub(other.toSeconds());
	}
	
	/*******************************************************************
	 * Decrements the timer by 1 second
	 ******************************************************************/
	public void dec()
	{
		sub(1);
	}
	
	/*******************************************************************
	 * Adds the provided amount of seconds to the timers current value
	 *
	 * @param seconds Amount to add
	 ******************************************************************/
	public void add(int seconds)
	{
		// Check if mutation is allowed
		if (susFlag) return;

		// Check if adding non-negative
		if (seconds <= 0) return;
		
		this.seconds += seconds;
		
		while (this.seconds > 59)
		{
			minutes++;
			this.seconds -= 60;
		}
		
		while (getMinutes() > 59)
		{
			hours++;
			this.minutes -= 60;
		}
	}
	
	/*******************************************************************
	 * Add the value of another timer, {@code other}, to this timer
	 *
	 * @param other Timer that will be added
	 ******************************************************************/
	public void add(CountDownTimer other)
	{
		add(other.toSeconds());
	}
	
	/*******************************************************************
	 * Increments the timer by 1 second
	 ******************************************************************/
	public void inc()
	{
		add(1);
	}
	
	/*******************************************************************
	 * Saves the timer value to a flat text file
	 * Saves timer value in seconds
	 *
	 * @param filename Name of the file to save to
	 ******************************************************************/
	public void save(String filename)
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		} catch (IOException ex)
		{
			JOptionPane.showMessageDialog(
					null, "This filename is a directory: " + filename);
		}
		// Have to use seconds because I want to avoid calling the constructor
		// that parses the "h:mm:ss" string
		out.print(toSeconds());
		out.close();
	}
	
	/*******************************************************************
	 * Loads a timer value from a flat text file
	 *
	 * @param filename Name of the file to load from
	 ******************************************************************/
	public void load(String filename)
	{
		try
		{
			Scanner scnr = new Scanner(new File(filename));

			// Activate add function just for "setting" the time from load
			boolean prevSus = susFlag;
			susFlag = false;
			add(scnr.nextInt());
			susFlag = prevSus;

			scnr.close();
		} catch (IOException ex)
		{
			JOptionPane.showMessageDialog(
					null, "File not found: " + filename);
		}
	}
	
	/*******************************************************************
	 * @return The current value of the timer in the format h:mm:ss
	 ******************************************************************/
	@Override
	public String toString()
	{
		return getHours() + ":" + String.format("%02d", getMinutes())
				+ ":" + String.format("%02d", getSeconds());
	}
	
	/*******************************************************************
	 * Checks whether the compared timer has the same value as this timer.
	 *
	 * @param other The compared timer
	 * @return equivalence
	 ******************************************************************/
	@Override
	public boolean equals(Object other)
	{
		return toSeconds() == ((CountDownTimer) other).toSeconds();
	}
	
	/*******************************************************************
	 * @param other Other {@code CountDownTimer} to compare
	 * @return {@code > 0} if {@code this} is greater than {@code other}
	 * 		{@code < 0} if {@code this} is less than {@code other}
	 * 	    and zero if they are equal
	 ******************************************************************/
	@Override
	public int compareTo(CountDownTimer other)
	{
		return this.toSeconds() - other.toSeconds();
	}
	
	/*******************************************************************
	 * @return Hours, minutes, and seconds converted to seconds
	 ******************************************************************/
	private int toSeconds()
	{
		return getHours() * 3600 + getMinutes() * 60 + getSeconds();
	}
	
	/*******************************************************************
	 * Handles any reactions to actions performed on this
	 * Used for interfacing with an actual timer such as a
	 * {@link javax.swing.Timer}.
	 * @param e Event information
	 ******************************************************************/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		dec();
	}
}
