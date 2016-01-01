package skyline.allPeople;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

/**
 * A class encompassing all the People objects of the simulation.
 *
 */
public class AllPeople implements Tickable, Drawable {

	public AllPeople(Engine engine) {
		civiliansInBuildings = new ArrayList<CivilianInBuilding>();
		populateBuildings(engine, 10);	// average 10 people per Window
		
		peopleOnGround = new ArrayList<PersonOnGround>();
		populateGrounds(engine, 50);
		
		flyingPeople = new ArrayList<FlyingPerson>();
		deadPeople = new ArrayList<DeadPerson>();
	}
	
	public ArrayList<CivilianInBuilding> getCiviliansInBuildings() {
		return civiliansInBuildings;
	}
	
	public ArrayList<PersonOnGround> getPeopleOnGround() {
		return peopleOnGround;
	}
	
	public ArrayList<FlyingPerson> getFlyingPeople() {
		return flyingPeople;
	}
	
	public ArrayList<DeadPerson> getDeadPeople() {
		return deadPeople;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		
		for (CivilianInBuilding person : civiliansInBuildings) {
			person.tick(timeElapsed, engine);
		}
		
		for (PersonOnGround person : peopleOnGround) {
			person.tick(timeElapsed, engine);
		}
		Collections.sort(peopleOnGround, personOnGroundComparator);
		
		for (FlyingPerson person : flyingPeople) {
			person.tick(timeElapsed, engine);
		}
		
		for (DeadPerson person : deadPeople) {
			person.tick(timeElapsed, engine);
		}
		
	}
	
	@Override
	public void draw(Graphics page) {
		
		// civiliansInBuildings need not be drawn
		
		for (DeadPerson person : deadPeople) {
			person.draw(page);
		}
		
		for (PersonOnGround person : peopleOnGround) {
			person.draw(page);
		}
		
		for (FlyingPerson person : flyingPeople) {
			person.draw(page);
		}
		
	}
	
	/**
	 * Fills the ArrayList civiliansInBuildings by instantiating new CivilianInBuilding objects.
	 * @param engine : The Engine object containing the City object where the CivilianInBuilding objects will reside 
	 * @param averageNumberPerWindow : int the average number of people per Window object in the City
	 */
	private void populateBuildings(Engine engine, int averageNumberPerWindow) {
		if (averageNumberPerWindow > 25) {
			averageNumberPerWindow = 25;
		}
		for (int i = 0; i < engine.getCity().totalNumberOfWindows() * averageNumberPerWindow; i++) {
			civiliansInBuildings.add(new CivilianInBuilding(engine));
		}
	}
	
	/**
	 * Adds a specified number of CivilianOnGround objects to TreeSet of peopleOnGround.
	 * @param engine : The Engine object hosting everything
	 * @param numberOfCivilians : int total number of CivilianOnGround objects to be added
	 */
	private void populateGrounds(Engine engine, int numberOfCivilians) {
		for (int i = 0; i < numberOfCivilians; i++) {
			peopleOnGround.add(new CivilianOnGround(engine));
		}
	}
	
	private ArrayList<CivilianInBuilding> civiliansInBuildings;
	private ArrayList<PersonOnGround> peopleOnGround;		// re-ordered (for drawing purposes) after every tick cycle, using the personOnGroundComparator
	private ArrayList<FlyingPerson> flyingPeople;				// re-ordered with peopleOnGround?????
	private ArrayList<DeadPerson> deadPeople;					// re-ordered with peopleOnGround ????
	
	private final PersonOnGroundComparator personOnGroundComparator = new PersonOnGroundComparator();	// used to sort peopleOnGround

}
