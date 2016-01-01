package skyline.allPeople;

import java.awt.Graphics;

import skyline.Engine;
import skyline.city.Building;
import skyline.city.Window;
import skyline.events.CivilianExitFromBuildingEvent;
import skyline.events.MakeWindowAwareEvent;

/**
 * Represents a Person inside a Building.
 *
 */
public class CivilianInBuilding extends Person {
	
	/**
	 * Constructs a new CivilianInBuilding object by assigning it to a random Window object in a random Building object of the City object of the Engine object.
	 * @param engine : The Engine object containing the City
	 */
	public CivilianInBuilding(Engine engine) {
		super();
		
		desiringToExit = false;
		awareOfAliens = false;
		awareDelay = 15.0;
		
		engine.getCity().addCivilianInBuilding(this);
	}
	
	public Window getWindow() {
		return building.getWindows()[windowRow][windowColumn];
	}
	
	public void setBuildingData(Building building, int windowRow, int windowColumn) {
		this.building = building;
		this.windowRow = windowRow;
		this.windowColumn = windowColumn;
	}
	

	@Override
	public void tick(double timeElapsed, Engine engine) {
		
		if (engine.isAliensArrived()) {
			
			if (awareOfAliens) {
				//make window aware, panic run to other windows
				
				desiringToExit = true;
				makeWindowAware(engine);		// plots an event to make window aware
				
				
			} else {
				//get aware if window is aware
				
				if (building.getWindows()[windowRow][windowColumn].isAwareOfAliens()) {
					awareDelay -= timeElapsed;
					if (awareDelay <= 0) {
						awareOfAliens = true;
					}
				} else {
					becomeAwareByChance(engine);
				}
				
				toggleDesiringToExit();
			}
			
		} else{
			awareOfAliens = false;
			awareDelay = 15.0;
			
			toggleDesiringToExit();
		}
		
		if (desiringToExit) {
			escapeWindow(engine);
		} else {
			moveOnASimpleWhim(engine);
		}
		
		
		
		
		
	}
	
	@Override
	public void draw(Graphics page) {
		
	}
	
	
	/**
	 * Plots an event to make the Window aware for the next simulation cycle.
	 */
	private void makeWindowAware(Engine engine) {
		
		engine.plotEvent(new MakeWindowAwareEvent(building.getWindows()[windowRow][windowColumn]));
	
	}
	
	/**
	 * Attempts to escape the current Window and move to an adjacent one.
	 * A move cannot be made unless the door between the two Windows has a clogged value equal to zero. Any successful move ends the method.
	 * The CivilianInBuilding follows the following logic:
	 * 1. If the CivilianInBuilding is at location (0,0), it will attempt to exit through the main exit. Regardless of success, the method ends.
	 * 2. The CivilianInBuilding will attempt to move downward.
	 * 3. If the CivilianInBuilding is on the bottom floor, it will attempt to move toward the door (at Window location (0,0)). Even if the move is unsuccessful, the method ends.
	 * 4. Of the current Window and the [up to] 2 adjacent Windows, the CivilianInBuilding will attempt to move to the Window with lowest occupancy.
	 */
	private void escapeWindow(Engine engine) {
		
		if (windowRow == 0 && windowColumn == 0) {
			attemptExitBuilding(engine);
			return;
		}
		
		if (attemptMoveDown()) {
			return;
		}
		
		if (windowRow == 0) {
			attemptMoveLeft();
			return;
		}
		
		//if on upper row, can't go down
		attemptMoveSideways();
		
	}
	
	/**
	 * If the location is at (0,0) and the main exit is unclogged, the CivilianInBuilding will successfully exit the Building.
	 * @return boolean indicating success of exit
	 */
	private boolean attemptExitBuilding(Engine engine) {
		
		if (windowRow != 0 || windowColumn != 0) {
			return false;
		}
		
		if (building.getMainExitClogged() == 0) {
			building.getWindows()[0][0].subtractCivilian();
			building.clogMainExit(engine);
			engine.plotEvent(new CivilianExitFromBuildingEvent(this, building.getX() + (int) ((double) engine.WIDTH * 2.5/200)));
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Attempts to move to the Window below the current one.
	 * @return boolean indicating if move was successful
	 */
	private boolean attemptMoveDown() {
		
		if (windowRow == 0) {
			return false;
		}
		
		if (building.getWindows()[windowRow - 1][windowColumn].getTopDoorClogged() == 0) {			// check for rounding errors. Should be fine since value is set as 0
			
			building.getWindows()[windowRow][windowColumn].subtractCivilian();
			windowRow--;
			building.getWindows()[windowRow][windowColumn].addCivilian();
			
			building.getWindows()[windowRow][windowColumn].clogTopDoor();
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Attempts to move to the Window above the current one.
	 * Note: Only used if CivilianInBuilding is still unaware.
	 * @return boolean indicating success of move
	 */
	private boolean attemptMoveUp() {
		
		if (windowRow == building.WINDOWS_TALL - 1) {
			return false;
		}
		
		if (building.getWindows()[windowRow][windowColumn].getTopDoorClogged() == 0) {
			building.getWindows()[windowRow][windowColumn].subtractCivilian();
			windowRow++;
			building.getWindows()[windowRow][windowColumn].addCivilian();
			
			building.getWindows()[windowRow - 1][windowColumn].clogTopDoor();
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Attempts to move to the left.
	 * @return boolean indicating success of move.
	 */
	private boolean attemptMoveLeft() {
		
		if (windowColumn == 0) {
			return false;
		}
		
		if (building.getWindows()[windowRow][windowColumn - 1].getRightDoorClogged() == 0) {	//if not clogged
			building.getWindows()[windowRow][windowColumn].subtractCivilian();
			windowColumn--;
			building.getWindows()[windowRow][windowColumn].addCivilian();
			
			building.getWindows()[windowRow][windowColumn].clogRightDoor();
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Attempts to move to the right.
	 * @return boolean indicating success of move.
	 */
	private boolean attemptMoveRight() {
		
		//if on row 0, return false
		
		if (windowColumn == building.WINDOWS_WIDE - 1) {
			return false;
		}
		
		if (building.getWindows()[windowRow][windowColumn].getRightDoorClogged() == 0) {
			building.getWindows()[windowRow][windowColumn].subtractCivilian();
			windowColumn++;
			building.getWindows()[windowRow][windowColumn].addCivilian();
			
			building.getWindows()[windowRow][windowColumn - 1].clogRightDoor();
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Checks if either of the adjacent Windows have lower occupancies than the current one and attempts to move there.
	 * @return boolean indicating success of a move
	 */
	private boolean attemptMoveSideways() {
		
		if (windowColumn == 0) {
			return attemptMoveRight();
		}
		
		if (windowColumn == building.WINDOWS_WIDE - 1) {
			return attemptMoveLeft();
		}
		
		//if in between first and last columns
		int		middle = building.getWindows()[windowRow][windowColumn].getNumberOfCivilians(),
				right = building.getWindows()[windowRow][windowColumn + 1].getNumberOfCivilians(),
				left = building.getWindows()[windowRow][windowColumn - 1].getNumberOfCivilians();
		
		if (middle <= right && middle <= left) {
			return false;
		}
		if (left <= right) {
			if (!attemptMoveLeft()) {
				if (right < middle) {
					return attemptMoveRight();
				}
			} else { //if successfully moved right
				return true;
			}
		} else {
			if (!attemptMoveRight()) {
				if (left < middle) {
					return attemptMoveLeft();
				}
			} else {
				return true;
			}
		}
		return false;
		
		
	}
	
	/**
	 * Moves randomly to an adjacent Window. Most likely no move.
	 * Only meant for CivilianInBuilding objects that are not desiring to exit.
	 */
	private void moveOnASimpleWhim(Engine engine) {
				
		int randNum = generator.nextInt(60);
		
		if (randNum <= 1) {
			attemptMoveDown();
		} else if (randNum <= 9) {
			attemptMoveUp();
		} else if (randNum <= 11) {
			attemptMoveLeft();
		} else if (randNum <= 13) {
			attemptMoveRight();
		}
		
	}
	
	/**
	 * Random chance that the CivilianInBuilding object will change its mind about exiting or not.
	 */
	private void toggleDesiringToExit() {
		if (generator.nextInt(10) == 0) {
			desiringToExit = !desiringToExit;
		}
	}
	
	
	/**
	 * Generates randomly to see if the CivilianInBuilding becomes aware of the aliens by looking out the window, etc.
	 * The longer has passed since the alien entrance, the more chance the CivilianInBuilding will become aware.
	 */
	private void becomeAwareByChance(Engine engine) {
		
		if (generator.nextInt(999999) < engine.getTimeSinceAliensArrived()) {
			awareOfAliens = true;
		}
		
	}
	
	
	private boolean desiringToExit;
	private boolean awareOfAliens;
	private double awareDelay;
	private Building building;
	private int windowRow;
	private int windowColumn;
	
}
