package skyline.city;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class Building implements Tickable, Drawable {
	
	public final int WINDOWS_TALL;		// how many windows tall
	public final int WINDOWS_WIDE;

	/**
	 * Constructs a new Building with the specified parameters.
	 * @param WINDOWS_WIDE : int number of windows per row
	 * @param WINDOWS_TALL : int number of rows of windows tall
	 */
	public Building(Engine engine, int columns, int rows, int X) {
		this.WINDOWS_WIDE = columns;
		this.WINDOWS_TALL = rows;
		windowWidth = (int) ((double) engine.WIDTH * 3/200);
		windowHeight = (int) ((double) engine.HEIGHT * 2/75);
		windowGap = engine.WIDTH / 200;
		width = windowGap + (windowGap + windowWidth) * WINDOWS_WIDE;
		height = 2 * windowGap + (windowGap + windowHeight) * WINDOWS_TALL;
		Y = (int) (engine.HEIGHT * .6666667 - height);
		this.X = X;
		
		chooseBuildingColor();
		
		windows = new Window[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				windows[i][j] = new Window(engine);
			}
		}
		calculateWindowPositions();
		
		Random generator = new Random();
		mainExitClogged = 1 + generator.nextInt(8) + generator.nextDouble();
		doorLooksOpen = false;
		
		intact = true;
		
	}
	
	
	public int getX() {
		return X;
	}
	
	public Window[][] getWindows() {
		return windows;
	}
	
	public double getMainExitClogged() {
		return mainExitClogged;
	}
	
	public boolean isIntact() {
		return intact;
	}
	
	
	/**
	 * Counts and returns the total number of CivilianInBuilding objects currently inside the building.
	 * @return int number of CivilianInBuilding objects
	 */
	public int getNumberOfCivilians() {
		int count = 0;
		for (int i = 0; i < WINDOWS_TALL; i++) {
			for (int j = 0; j < WINDOWS_WIDE; j++) {
				count += windows[i][j].getNumberOfCivilians();
			}
		}
		return count;
	}
	
	
	/**
	 * Clogs the main exit by 10 seconds.
	 */
	public void clogMainExit(Engine engine) {
		if (engine.isAliensArrived()) {
			mainExitClogged = 5.0;
		} else {
			mainExitClogged = 10.0;
		}
	}
	
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		mainExitClogged = reduceDoorClog(mainExitClogged, timeElapsed);
		if (engine.isAliensArrived()) {
			if (mainExitClogged > 5) {
				mainExitClogged = 5.0;
			}
			doorLooksOpen = (mainExitClogged > 4.5);
		} else {
			doorLooksOpen = (mainExitClogged > 9.5);
		}
		calculateWindowPositions();
		for (Window[] row : windows) {
			for (Window w : row) {
				w.tick(timeElapsed, engine);
			}
		}
	}
	
	@Override
	public void draw(Graphics page) {
		page.setColor(color);
		page.fillRect(X, Y, width, height);
		for (Window[] row : windows) {
			for (Window w : row) {
				w.draw(page);
			}
		}
		drawDoor(page);
	}
	
	/**
	 * Copied from Window class
	 */
	private double reduceDoorClog(double amountClogged, double timeElapsed) {
		
		if (amountClogged > 0) {
			amountClogged -= timeElapsed;
		}
		if (amountClogged < 0) {
			amountClogged = 0;
		}
		return amountClogged;
		
	}
	
	/**
	 * Sets the color of the building to a random shade of gray. Meant for use in the constructor.
	 */
	private void chooseBuildingColor() {
		Random generator = new Random();
		int shade = 95 + generator.nextInt(90);
		color = new Color(shade, shade, shade);
	}
	
	/**
	 * Draws the door at Window location (0,0). Black if closed (mainExitClogged <= 5); red if open.
	 * @param page : The Graphics object to which to draw
	 */
	private void drawDoor(Graphics page) {
		
		if (doorLooksOpen) {							// If open
			// Black parallelogram symbolizing swung-open door
			page.setColor(Color.black);
			int[] xPts = { 
					X + windowGap, 
					X + windowGap, 
					X - 2 * windowGap, 
					X - 2 * windowGap 
			};
			int[] yPts = { 
					Y + height - 2 * windowGap - windowHeight, 
					Y + height, 
					Y + height + windowGap, 
					Y + height - windowGap - windowHeight
			};
			page.fillPolygon(xPts, yPts, 4);
			
			// Draw open door space with magenta
			page.setColor(Color.yellow);
			page.fillRect(X + windowGap, Y + height - 2 * windowGap - windowHeight, windowWidth, windowHeight + 2 * windowGap);
		} else {											// If closed (or mostly closed)
			// Draw closed door space with black
			page.setColor(Color.black);
			page.fillRect(X + windowGap, Y + height - 2 * windowGap - windowHeight, windowWidth, windowHeight + 2 * windowGap);
			
			// Draw window and doorknob on door
			page.setColor(Color.yellow);
			page.fillRect(X + windowGap + (int) (windowGap * .6), Y + height - 2 * windowGap - windowHeight + (int) (windowGap * .6),
							windowWidth - (int) (windowGap * 1.2), windowWidth - (int) (windowGap * 1.2));
			page.fillOval(X + windowWidth, Y + height - 3 * windowGap, windowGap / 2, windowGap / 2);
		}
		
		
	}
	
	private void calculateWindowPositions() {
		for (int row = 0; row < windows.length; row++) {
			for (int column = 0; column < windows[row].length; column++) {
				int windowX = X + windowGap + (windowWidth + windowGap) * column;
				int windowY = Y + height - (2 * windowGap + (windowHeight + windowGap) * row) - windowHeight;
				windows[row][column].setPosition(windowX, windowY);
			}
		}
	}
	
	private final int windowWidth;
	private final int windowHeight;
	private final int windowGap;
	
	private int X;
	private int Y;
	private int width;
	private int height;
	
	private Color color;
	private Window[][] windows;
	
	private double mainExitClogged;
	private boolean doorLooksOpen;
	
	private boolean intact;
	
	
}
