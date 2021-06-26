/**
 * Represents a customer shopping at the Marketplace.
 * Tracks arrival time in queue for statistical purposes.
 *
 * @author Silas Agnew
 * @version November 21, 2017
 */

public class Customer
{
    private double arrivalTime = 0;

    /**
     * Constructs a customer with time in simulation of construction.
     * @param time Time in simulation
     */
    public Customer(double time)
    {
        setArrivalTime(time);
    }

    /**
     * Sets the time the customer arrives in queue.
     * @param arrivalTime arrival time
     */
    public void setArrivalTime(double arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return The time the customer arrived in queue
     */
    public double getArrivalTime()
    {
        return arrivalTime;
    }

    //-Test Main---------------------------------------------------//

    public static void main(String[] args)
    {
		Customer c = new Customer(10);
		assert (Math.abs(c.getArrivalTime() - 10) <= .001) :
				"Assertion failed: Customer arrival time is supposed to be 10, not" + c.getArrivalTime();
    }
}
