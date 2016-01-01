package skyline.city;

import java.awt.Graphics;
import java.util.Random;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;
import skyline.allPeople.CivilianInBuilding;

/**
 * Represents all Building objects on screen.
 *
 */
public class City implements Tickable, Drawable {
	
	public City(Engine engine, int numberOfBuildings) {

		constructBuildings(numberOfBuildings, engine);
		
		displayingCivilianNumbers = true;
	}
	
	public Building[] getBuildings() {
		return buildings;
	}
	
	public boolean isDisplayingCivilianNumbers() {
		return displayingCivilianNumbers;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		for (Building b : buildings) {
			b.tick(timeElapsed, engine);
		}
	}	

	@Override
	public void draw(Graphics page) {
		for (Building b : buildings) {
			b.draw(page);
		}
	}	
	
	
	
	
	/**
	 * Returns the total number of windows included in the City.
	 * @return int number of windows
	 */
	public int totalNumberOfWindows() {
		int windowCount = 0;
		for (Building b : buildings){
			windowCount += b.WINDOWS_TALL * b.WINDOWS_WIDE;
		}
		return windowCount;
	}
	
	/**
	 * Meant for use in the CivilianInBuilding object constructor. Chooses a random Window object from the City and sets the CivilianInBuilding object's data accordingly.
	 * @param civilian : The CivilianInBuilding object to be consructed
	 */
	public void addCivilianInBuilding(CivilianInBuilding civilian) {
		Random generator = new Random();
		int windowNumber = generator.nextInt(totalNumberOfWindows());
		int currentNum = 0;		// Trying to match currentNum with windowNumber
		
		int buildingIndex = 0;
		while (currentNum <= windowNumber) {
			currentNum += buildings[buildingIndex].WINDOWS_TALL * buildings[buildingIndex].WINDOWS_WIDE;
			buildingIndex++;
		}
		buildingIndex--;
		currentNum -= buildings[buildingIndex].WINDOWS_TALL * buildings[buildingIndex].WINDOWS_WIDE;
		
		int windowColumn = 0, windowRow = 0;
		while (currentNum < windowNumber) {
			if (windowColumn == buildings[buildingIndex].WINDOWS_WIDE - 1) {
				windowRow++;
				windowColumn = 0;
			} else {
				windowColumn++;
			}
			currentNum++;
		}
		
		buildings[buildingIndex].getWindows()[windowRow][windowColumn].addCivilian();
		civilian.setBuildingData(buildings[buildingIndex], windowRow, windowColumn);
	}
	

	
	/**
	 * Initializes buildings by constructing several Building objects. Adds element of randomness to Building objects and placement within City.
	 * @param engine : The Engine controlling everything
	 * @param numberOfBuildings : int number of Building objects to be constructed in the City
	 */
	private void constructBuildings(int numberOfBuildings, Engine engine) {
		if (numberOfBuildings < 1) {
			numberOfBuildings = 1;
		} else if (numberOfBuildings > 12) {
			numberOfBuildings = 12;
		}
		
		Random generator = new Random();
		final int SPACE = engine.WIDTH / 50;		// space for one Window + gap
		
		int spacesAvailable = 50;
		spacesAvailable -= (3 + numberOfBuildings + generator.nextInt(51 - 2 * numberOfBuildings - (3 + numberOfBuildings)));	// spaces available for buildings
															// Max: 2 open on each side, 1 open b/t each Building; Min: each Building width = 2
		int pixelsAvailable = engine.WIDTH - (spacesAvailable * SPACE) - 2 * SPACE;												// pixels available for gaps
		
		int[] buildingSpaces = new int[numberOfBuildings];			// An array holding values for the number of SPACES (20-pixel chunks if engine width=1000) of each building width
		for (int i = 0; i < numberOfBuildings; i++) {
			buildingSpaces[i] = 2;
		}
		int spacesTaken = numberOfBuildings * 2;																									// spaces taken by buildings already
		while (spacesTaken < spacesAvailable) {
			++buildingSpaces[generator.nextInt(numberOfBuildings)];			// Adds another space to a random building
			++spacesTaken;
		}
		
		int[] gapWidths = new int[numberOfBuildings + 1];			// An array holding values for the width (in pixels) of each gap between buildings, including outermost
		for (int i = 0; i < numberOfBuildings + 1; i++) {
			gapWidths[i] = SPACE;
		}
		int pixelsTaken = SPACE * (numberOfBuildings + 1) + 2 * SPACE;															// pixels taken by gaps already
		while (pixelsTaken < pixelsAvailable) {
			++gapWidths[generator.nextInt(numberOfBuildings + 1)];
			++pixelsTaken;
		}
		
		int xMarker = SPACE;
		buildings = new Building[numberOfBuildings];		// Initialize buildings array
		for (int i = 0; i < numberOfBuildings; i++) {
			xMarker += gapWidths[i];
			buildings[i] = new Building(engine, buildingSpaces[i], 5 + generator.nextInt(12), xMarker);
			xMarker += buildingSpaces[i] * SPACE;
		}
	}
	
	private Building[] buildings;

	private boolean displayingCivilianNumbers;

	
}
