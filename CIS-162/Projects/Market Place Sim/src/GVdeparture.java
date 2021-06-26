/**
 * An event representing the departure of a {@link Customer} from the store.
 * @see GVevent
 *
 * @author Silas Agnew
 * @version November 20, 2017
 */

public class GVdeparture extends GVevent
{
	/**
	 * Constructs a departure event to occur at the provided time
	 * @param store {@code MarketPlace} at which the event is occurring
	 * @param time Time at which the event occurs
	 * @param id the serving cashier identification
	 */
    public GVdeparture(MarketPlace store, double time, int id)
    {
        super(store, time, id);
    }

    /**
     * Processes the event
	 * @see GVevent#process()
     */
    @Override
    public void process()
    {
		store.customerPays(getCashier());
    }
}
