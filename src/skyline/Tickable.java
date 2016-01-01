package skyline;

public interface Tickable {
	
	/**
	 * The standard function for passing one simulator cycle on a Tickable.
	 * @param timeElapsed : double amount of time that has passed in this cycle
	 * @param engine : The Engine running the simulation
	 */
	public void tick(double timeElapsed, Engine engine);
	
}
