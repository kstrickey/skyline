package skyline.alienStuff;

import java.awt.Graphics;
import java.util.ArrayList;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class AlienStuff implements Tickable, Drawable {
	
	public AlienStuff(Engine engine) {
		spaceship = new Spaceship(engine);
		mouseTarget = new MouseTarget(engine);
		laserBolts = new ArrayList<LaserBolt>();
	}
	
	public Spaceship getSpaceship() {
		return spaceship;
	}
	
	public MouseTarget getMouseTarget() {
		return mouseTarget;
	}
	
	public ArrayList<LaserBolt> getLaserBolts() {
		return laserBolts;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		spaceship.tick(timeElapsed, engine);
		mouseTarget.tick(timeElapsed, engine);
		for (LaserBolt bolt : laserBolts) {
			bolt.tick(timeElapsed, engine);
		}
	}

	@Override
	public void draw(Graphics page) {
		for (LaserBolt bolt : laserBolts) {
			bolt.draw(page);
		}
		spaceship.draw(page);
		mouseTarget.draw(page);
		
	}
	
	/**
	 * Adds a new LaserBolt object to ArrayList laserBolts.
	 * @param engine : The Engine object
	 * @param destinationX : int indicating x-position where the mouse was clicked
	 * @param destinationY : int indicating y-position where the mouse was clicked
	 */
	public void fireLaserBolt(Engine engine, int destinationX, int destinationY) {
		laserBolts.add(new LaserBolt(engine, spaceship.getLaserColor(), destinationX, destinationY));
	}
	
	
	private Spaceship spaceship;
	private MouseTarget mouseTarget;
	private ArrayList<LaserBolt> laserBolts;
	
}
