package skyline;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import skyline.alienStuff.AlienStuff;
import skyline.alienStuff.LaserBolt;
import skyline.allPeople.AllPeople;
import skyline.city.Building;
import skyline.city.City;
import skyline.city.Window;
import skyline.dashboard.Dashboard;
import skyline.events.*;
import skyline.humanDefense.HumanDefense;
import skyline.landscape.Landscape;

public class Engine implements Drawable {
	
	public static final double TOLERANCE = .0000001;

	public final int WIDTH = 1000;
	public final int HEIGHT = 750;
	
	public final double GRAVITY = 175.0;
	
	public Engine() {
		
		dead = false;
		
		calendar = new GregorianCalendar();
		randomizeDate(calendar);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 15);
		
		aliensArrived = false;
		timeSinceAliensArrived = 0;
		
		countKills = 0;
		countEscapers = 0;
		
		plottedEvents = new ArrayList<Event>();
		
		landscape = new Landscape(this);
		city = new City(this, 4 + new Random().nextInt(7));
		allPeople = new AllPeople(this);
		alienStuff = new AlienStuff(this);
		humanDefense = new HumanDefense(this);
		dashboard = new Dashboard(this);
		
		laserTargets = new ArrayList<LaserTarget>();
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public GregorianCalendar getCalendar() {
		return calendar;
	}
	
	public boolean isAliensArrived() {
		return aliensArrived;
	}
	
	public double getTimeSinceAliensArrived() {
		return timeSinceAliensArrived;
	}
	
	public int getCountKills() {
		return countKills;
	}
	
	public int getCountEscapers() {
		return countEscapers;
	}
	
	public Landscape getLandscape() {
		return landscape;
	}
	
	public City getCity() {
		return city;
	}
	
	public AllPeople getAllPeople() {
		return allPeople;
	}
	
	public AlienStuff getAlienStuff() {
		return alienStuff;
	}
	
	public HumanDefense getHumanDefense() {
		return humanDefense;
	}
	
	/**
	 * Calls this method to set boolean dead to true.
	 */
	public void playerDie() {
		dead = true;
	}
	
	/**
	 * Toggles boolean field aliensArrived.
	 */
	public void aliensArriveOrDepart() {
		aliensArrived = !aliensArrived;
	}
	
	/**
	 * Adds one to the countEscapers field. Meant to be called in PersonOnGroundLeaveBoundsEvent.
	 */
	public void addEscaper() {
		countEscapers++;
	}
	
	@Override
	public void draw(Graphics page) {
		
		landscape.draw(page);
		city.draw(page);
		allPeople.draw(page);
		alienStuff.draw(page);
		humanDefense.draw(page);
		dashboard.draw(page);
		
		
	}
	
	
	/**
	 * Runs one cycle by ticking all Tickable objects owned.
	 * @param timeElapsed : double indicating how much time elapsed in the cycle
	 */
	public void tick(double timeElapsed) {
		
		calendar.add(GregorianCalendar.MILLISECOND, (int) (timeElapsed * 1000));
		if (aliensArrived) {
			timeSinceAliensArrived += timeElapsed;
		}
		countKills = allPeople.getDeadPeople().size();
		
		refreshLaserDestinations();
		
		landscape.tick(timeElapsed, this);
		city.tick(timeElapsed, this);
		allPeople.tick(timeElapsed, this);
		alienStuff.tick(timeElapsed, this);
		humanDefense.tick(timeElapsed, this);
		dashboard.tick(timeElapsed, this);
		
		for (LaserBolt bolt : alienStuff.getLaserBolts()) {
			if (bolt.isHit()) {
				for (LaserTarget destination : laserTargets) {
					destination.laserHits(this, bolt.getHitX(), bolt.getHitY());
				}
			}
		}
		
		
		executeAllEvents();
		
	}
	
	
	/**
	 * Adds an Event to the Engine's list of plotted Events to execute after the current cycle.
	 * @param event : The Event to be added
	 */
	public void plotEvent(Event event) {
		synchronized(eventQueueMonitor) {
			plottedEvents.add(event);
		}
	}
	
	
	/**
	 * Executes all plotted events and clears the plottedEvents ArrayList.
	 */
	private void executeAllEvents() {
		List<Event> eventsToExecute = null;
		synchronized(eventQueueMonitor) {
			eventsToExecute = plottedEvents;
			plottedEvents = new ArrayList<>();
		}
		
		Iterator<Event> iter = eventsToExecute.iterator();
		while (iter.hasNext()) {
			Event event = iter.next();
			event.execute(this);
		}
	}
	
	
	/**
	 * Randomizes the date of a GregorianCalendar to a value between 1 January 3030 and 31 December 4029.
	 * @param calendar : The GregorianCalendar to be randomized
	 */
	private void randomizeDate(GregorianCalendar calendar) {

		Random generator = new Random();

		calendar.set(GregorianCalendar.YEAR, 3030 + generator.nextInt(1000));
		calendar.set(GregorianCalendar.MONTH, 1 + generator.nextInt(12));
		calendar.set(GregorianCalendar.DAY_OF_MONTH, 1 + generator.nextInt(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)));
		
	}
	
	private void refreshLaserDestinations() {
		laserTargets.clear();
		laserTargets.add(landscape.getGround());
		laserTargets.addAll(allPeople.getPeopleOnGround());
		laserTargets.addAll(humanDefense.getMissiles());
		for (Building building : city.getBuildings()) {
			for (Window[] windowRow : building.getWindows()) {
				for (Window window : windowRow) {
					laserTargets.add(window);
				}
			}
		}
	}
	
	private boolean dead;
	
	private GregorianCalendar calendar;
	private boolean aliensArrived;				//if the game has started.. aka aliens have appeared and people can be aware of aliens
	private double timeSinceAliensArrived;
	
	private int countKills;
	private int countEscapers;
	
	private Landscape landscape;
	private City city;
	private AllPeople allPeople;
	private AlienStuff alienStuff;
	private HumanDefense humanDefense;
	private Dashboard dashboard;
	
	private ArrayList<LaserTarget> laserTargets;	// include people on ground, windows
	
	private ArrayList<Event> plottedEvents;
	
	private Object eventQueueMonitor = new Object();
	
	
}
