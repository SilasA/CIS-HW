/**
 * A GVSU Marketplace simulator.
 * <br/>
 * Runs the checkout area to simulate customers waiting in line and
 * being served by a cashier.  This tracks the wait times and line lengths
 * for statistics.  The times at which customers arrive and cashier service
 * times are generated pseudo-randomly around an average time set by the
 * simulation parameters.
 * <br/>
 * The simulation is run by the {@link MarketPlace#startSimulation()} method.
 * That will only output to the console.
 *
 * @author Silas Agnew
 * @version December 4, 2017
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.SimpleTimeZone;

public class MarketPlace
{
	private final int OPEN_TIME  = 600;
	private final int CLOSE_TIME = 1080;
	
	private double                 averageArrivalInterval = 0;
	private double                 averageServicetime     = 0;
	private int                    numCashiers            = 0;
	private boolean                displayCheckout        = false;
	private double                 currentTime            = 0;
	private ArrayList<Customer>    customerList           = null;
	private Customer[]             cashiers               = null;
	private PriorityQueue<GVevent> eventQueue             = null;
	private GVrandom               rand                   = null;
	private String                 report                 = "";
	private int                    customersServed        = 0;
	private int                    longestLineLength      = -1;
	private double                 lengthTimestamp        = -1;
	
	// this serves as an accumulative total of wait times then
	// when the simulation finishes it will become the average.
	private double averageWaitTime = 0;
	
	// More
	
	/**
	 * Constructs a simulated market place.
	 */
	public MarketPlace()
	{
		this.averageArrivalInterval = 2.5;
		this.averageServicetime = 6.6;
		this.numCashiers = 3;
		this.displayCheckout = false;
		this.currentTime = -1;
		this.customerList = new ArrayList<>();
		this.eventQueue = new PriorityQueue<>();
		this.rand = new GVrandom();
	}
	
	//-Accessors---------------------------------------------------//
	
	public int getNumCashiers()
	{
		return numCashiers;
	}
	
	public double getArrivalTime()
	{
		return averageArrivalInterval;
	}
	
	public double getServiceTime()
	{
		return averageServicetime;
	}
	
	public int getNumCustomersServed()
	{
		return customersServed;
	}
	
	public String getReport()
	{
		return report;
	}
	
	public int getLongestLineLength()
	{
		return longestLineLength;
	}
	
	public double getAverageWaitTime()
	{
		return averageWaitTime;
	}
	
	//-Mutators----------------------------------------------------//
	
	/**
	 * Sets the parameters for the simulation.
	 * This method assumes all numbers are {@code > 0}.
	 * @param cashiers Number of cashiers
	 * @param avgServiceTime Average time a customer is with a cashier
	 * @param avgArrival     Average time between customer arrivals
	 * @param displayCheck   Display the checkout information or not
	 */
	public void setParameters(int cashiers, double avgServiceTime,
							  double avgArrival, boolean displayCheck)
	{
		numCashiers = cashiers;
		averageServicetime = avgServiceTime;
		averageArrivalInterval = avgArrival;
		displayCheckout = displayCheck;
	}
	
	/**
	 * Simulates a customer getting in line to checkout. This will add a new
	 * customer to the queue, transfer customers in line to cashiers, if
	 * possible, and push a new event to the event queue.
	 */
	public void customerGetsInLine()
	{
		customerList.add(new Customer(currentTime));
		
		// Move customers to cashiers if possible
		while (cashierAvailable() > -1 && customerList.size() > 0)
		{
			customerToCashier(cashierAvailable());
		}
		
		// update stats of longest line and time
		if (customerList.size() > longestLineLength)
		{
			longestLineLength = customerList.size();
			lengthTimestamp = currentTime;
		}
		
		// Set another customer to arrive
		// I do not try to compensate the future arrival time for current time
		// vs closing time because the time they get in line doesn't control the
		// the time they walked into the store.
		if (currentTime < CLOSE_TIME)
		{
			eventQueue.add(new GVarrival(
					this, randomFutureTime(averageArrivalInterval)));
		}
	}
	
	/**
	 * Simulates a customer paying and leaving the store. Frees the current
	 * cashier and, if there is anymore customers enqueued, assign them to a
	 * cashier.
	 *
	 * @param num Cashier to free from a customer
	 */
	public void customerPays(int num)
	{
		cashiers[num] = null;
		customersServed++;
		
		// Move customers to cashiers if possible
		while (cashierAvailable() > -1 && customerList.size() > 0)
		{
			customerToCashier(cashierAvailable());
		}
	}
	
	/**
	 * Runs the simulation.
	 */
	public void startSimulation()
	{
		reset();
		eventQueue.add(new GVarrival(this, currentTime));
		
		while (!eventQueue.isEmpty())
		{
			GVevent e = eventQueue.poll();
			currentTime = e.getTime();
			e.process();
			
			if (displayCheckout) showCheckoutArea();
		}
		createReport();
	}
	
	/**
	 * Formats minutes of time with format hh:mm (am/pm).
	 *
	 * @param mins time in minutes
	 * @return A formatted string equivalent to {@code mins}.
	 */
	public String formatTime(double mins)
	{
		// Yes this will be time since unix epoch, but date is not needed
		SimpleDateFormat fmt = new SimpleDateFormat("h:mma");
		Date t = new Date((int) mins * 60000); // Convert to ms

		// Make a timezone because it automatically converts it to local time of
		// the system
		fmt.setTimeZone(new SimpleTimeZone(
				0, "GVSU MarketPlace Time (GMT)")); // Hi-fives self
		
		// Make am/pm lowercase as per the specs
		return fmt.format(t).replace("AM", "am")
				.replace("PM", "pm"); // Hi-fives self again
	}
	
	//-Private Helpers---------------------------------------------//
	
	/**
	 * Resets the simulation.
	 * Does not reset any parameters previously set.
	 */
	private void reset()
	{
		customerList = new ArrayList<>();
		eventQueue = new PriorityQueue<>();
		this.cashiers = new Customer[numCashiers];
		
		currentTime = OPEN_TIME; // IDK if this is right
		report = "";
		customersServed = 0;
		averageWaitTime = -1;
		longestLineLength = -1;
		lengthTimestamp = -1;
	}
	
	/**
	 * @return The first available cashier's index. If there is none, -1.
	 */
	private int cashierAvailable()
	{
		for (int i = 0; i < cashiers.length; i++)
		{
			if (cashiers[i] == null) return i;
		}
		return -1;
	}
	
	/**
	 * Generates a random time based on {@code avg} and adds it to the current
	 * time to ensure that it is in the future of the simulation.
	 *
	 * @param avg Number to base generation on.
	 * @return A random time in the future of the simulation.
	 */
	private double randomFutureTime(double avg)
	{
		return currentTime + rand.nextPoisson(avg);
	}
	
	/**
	 * Moves a customer from the line to a cashier of index {@code num}.
	 *
	 * @param num Index of the receiving cashier.
	 */
	private void customerToCashier(int num)
	{
		cashiers[num] = customerList.remove(0);
		double waitTime = currentTime - cashiers[num].getArrivalTime();
		
		averageWaitTime += (waitTime < 0) ? 0 : waitTime;
		eventQueue.add(new GVdeparture(
				this, randomFutureTime(averageServicetime), num));
	}
	
	/**
	 * Appends a timestamp and checkout information to the simulation report.
	 */
	private void showCheckoutArea()
	{
		// Timestamp
		report += formatTime(currentTime) + " ";
		
		// Cashiers
		for (int i = 0; i < this.cashiers.length; i++)
		{
			if (this.cashiers[i] == null) report += "_";
			else report += "C";
		}
		
		report += " ";
		
		// Queue
		for (int i = 0; i < customerList.size(); i++)
			report += "*";
		report += "\n";
	}
	
	/**
	 * Compiles simulation data and stats and appends them to the simulation report.
	 */
	private void createReport()
	{
		// Sim params
		report += "\n\nSIMULATION PARAMETERS\nNumber of cashiers: " + cashiers.length
				+ "\nAverage arrival: " + averageArrivalInterval
				+ "\nAverage service: " + averageServicetime;
		
		// Calculate average
		averageWaitTime /= customersServed;
		
		NumberFormat fmt = new DecimalFormat("#0.00");
		
		// Results
		report += "\n\nRESULTS\nAverage wait time: " + fmt.format(averageWaitTime)
				+ " mins" + "\nMax line length: " + longestLineLength + " at "
				+ formatTime(lengthTimestamp) + "\nCustomers served: "
				+ customersServed + "\nLast departure: " + formatTime(currentTime);
	}
}