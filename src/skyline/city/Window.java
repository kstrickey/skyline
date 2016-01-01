package skyline.city;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;

import skyline.Drawable;
import skyline.Engine;
import skyline.LaserTarget;
import skyline.Tickable;
import skyline.allPeople.CivilianInBuilding;
import skyline.events.CivilianBlastedIntoSkyEvent;

public class Window implements Tickable, Drawable, LaserTarget {
	
	public Window(Engine engine) {
		WIDTH = (int) ((double) engine.WIDTH * 3/200);
		HEIGHT = (int) ((double) engine.HEIGHT * 2/75);
		color = Color.yellow;
		numberOfCivilians = 0;
		awareOfAliens = false;
		topDoorClogged = 0;
		rightDoorClogged = 0;
		intact = true;
	}
	
	public int getMiddleX() {
		return X + WIDTH / 2;
	}
	
	public int getBottomY() {
		return Y + HEIGHT;
	}
	
	public int getNumberOfCivilians() {
		return numberOfCivilians;
	}
	
	public double getTopDoorClogged() {
		return topDoorClogged;
	}
	
	public double getRightDoorClogged() {
		return rightDoorClogged;
	}
	
	public boolean isAwareOfAliens() {
		return awareOfAliens;
	}
	
	
	
	public void becomeAware() {
		awareOfAliens = true;
	}
	
	public void clogTopDoor() {
		if (topDoorClogged <= 0) {
			Random generator = new Random();
			topDoorClogged = 8 + generator.nextInt(10) + generator.nextDouble();
		}
	}
	
	public void clogRightDoor() {
		if (rightDoorClogged <= 0) {
			Random generator = new Random();
			rightDoorClogged = 4 + generator.nextInt(8) + generator.nextDouble();
		}
	}
	
	public void setPosition(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}
	
//	@Override
//	public boolean equals(Object other) {
//		if (other instanceof Window) {
//			return X == ((Window) other).X && Y == ((Window) other).Y;
//		}
//		return false;
//	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		
		if (intact) {
			if (!engine.isAliensArrived()) {
				awareOfAliens = false;			// if the aliens went away, then they're no longer aware.
			}
			
			topDoorClogged = reduceDoorClog(topDoorClogged, timeElapsed);
			rightDoorClogged = reduceDoorClog(rightDoorClogged, timeElapsed);
			if (numberOfCivilians >= MAX_CIVILIANS_PER_WINDOW) {
				clogTopDoor();
				clogRightDoor();
			}
			
			if (numberOfCivilians > 0) {
				color = Color.yellow;
			} else if (numberOfCivilians == 0) {
				color = Color.black;
			} else {
				System.out.println("BUG! numberOfCivilians < 0");
			}
			
			displayingCivilianNumbers = engine.getCity().isDisplayingCivilianNumbers();
		}
		
	}
	
	@Override
	public void draw(Graphics page) {
		if (intact) {
			if (awareOfAliens) {
				page.setColor(Color.pink);
			} else {
				page.setColor(color);
			}
			page.fillRect(X, Y, WIDTH, HEIGHT);
			
			page.setColor(Color.blue);
		} else {
			page.setColor(colorOfDeadness);
			page.fillRect(X, Y, WIDTH, HEIGHT);
		}
		
		if (displayingCivilianNumbers) {
			page.drawString(Integer.toString(numberOfCivilians), X, Y + 10);
		}
	}
	
	@Override
	public void laserHits(Engine engine, int X, int Y) {
		if (X >= this.X && X <= this.X + WIDTH && Y >= this.Y && Y <= this.Y + HEIGHT) {
			intact = false;
			numberOfCivilians = 0;	// everyone dies
			Iterator<CivilianInBuilding> iter = engine.getAllPeople().getCiviliansInBuildings().iterator();
			while (iter.hasNext()) {
				CivilianInBuilding person = iter.next();
				if (person.getWindow() == this) {
					engine.plotEvent(new CivilianBlastedIntoSkyEvent(person));
					iter.remove();
				}
			}
		}
	}
	
	/**
	 * Adds one to numberOfCivilians field.
	 */
	public void addCivilian() {
		numberOfCivilians++;
	}
	
	/**
	 * Subtracts one from numberOfCivilians field.
	 */
	public void subtractCivilian() {
		numberOfCivilians--;
	}
	
	
	
	/**
	 * Reduces the amount of door clog to this Window by however much timeElapsed or until it reaches 0.
	 * @param amountClogged : double how much the door is still clogged
	 * @param timeElapsed : double how much time is elapsed during simulation (seconds)
	 * @return double new amountClogged
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
	
	private final int WIDTH;
	private final int HEIGHT;
	
	private int X;
	private int Y;
	private Color color;
	
	private int numberOfCivilians;
	private boolean awareOfAliens;
	
	private double topDoorClogged;			// the amount of time left before the top door becomes unclogged
	private double rightDoorClogged;
	
	private boolean intact;
	
	private boolean displayingCivilianNumbers;
	
	private final Color colorOfDeadness = new Color(148, 0, 0);
	private final int MAX_CIVILIANS_PER_WINDOW = 20;
	
}
