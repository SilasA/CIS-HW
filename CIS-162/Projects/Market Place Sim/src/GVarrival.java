/**
 * Represents an event where a {@link Customer} gets in line for a cashier.
 * @see GVevent
 *
 * @author Silas Agnew
 * @version November 17, 2017
 */

public class GVarrival extends GVevent
{
    /**
     * Constructs an event to occur at a provided time.
     * @param store The {@code MarketPlace} the event is occurring in
     * @param time The time to process the event
     */
    public GVarrival(MarketPlace store, double time)
    {
        super(store, time);
    }

    /**
     * Processes the event
	 * @see GVevent#process()
     */
    @Override
    public void process()
    {
        store.customerGetsInLine();
    }
}
