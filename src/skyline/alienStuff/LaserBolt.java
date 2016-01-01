package skyline.alienStuff;

import java.awt.Color;
import java.awt.Graphics;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;
import skyline.events.LaserBoltDisappearEvent;

public class LaserBolt implements Tickable, Drawable {
	
	public LaserBolt(Engine engine, Color color, int destinationX, int destinationY) {
		this.color = color;
		
		this.destinationX = destinationX;
		this.destinationY = destinationY;
		
		length = engine.WIDTH * .08;
		width = engine.WIDTH * .003;
		speed = engine.WIDTH / 1.0;	// experimentally determined number
		
		X = engine.getAlienStuff().getSpaceship().getX() + engine.WIDTH / 25.0;	// middle of bottom oval of Spaceship
		Y = engine.getAlienStuff().getSpaceship().getY() + engine.WIDTH * .04;
		
		double totalX = destinationX - X;
		double totalY = destinationY - Y;
		double totalDistance = Math.sqrt(totalX*totalX + totalY*totalY);
		if (Math.abs(totalDistance) < Engine.TOLERANCE) {
			totalX = 0;
			totalY = 0;
			totalDistance = 0;
			dx = 0; dy = 0;
		} else {
			dx = totalX * speed / totalDistance;
			dy = totalY * speed / totalDistance;
			
			x1 = length * totalX / totalDistance;
			y1 = length * totalY / totalDistance;
			x3 = width / length * y1;
			y3 = width / length * x1;
			x2 = x3 + x1;
			y2 = y3 + y1;
		}
		
		hit = false;
		
	}
	
	public boolean isHit() {
		return hit;
	}
	
	public int getHitX() {
		return destinationX;//(int) (X + x1);
	}
	
	public int getHitY() {
		return destinationY;//(int) (Y + y1);
	}
	
	@Override
	public void draw(Graphics page) {
		page.setColor(color);
		int[] xPts = {(int) X, (int) (X + x1), (int) (X + x2), (int) (X + x3)};
		int[] yPts = {(int) Y, (int) (Y + y1), (int) (Y + y2), (int) (Y + y3)};
		page.fillPolygon(xPts, yPts, 4);
	}

	@Override
	public void tick(double timeElapsed, Engine engine) {
		X += dx * timeElapsed;
		Y += dy * timeElapsed;
		
		if (Math.abs(X + 2 * x1 - destinationX) >= Math.abs(X - destinationX) && Math.abs(Y + 2 * y1 - destinationY) >= Math.abs(Y - destinationY)) {
			engine.plotEvent(new LaserBoltDisappearEvent(this));
			hit = true;
		}
	}
	
	private final Color color;
	
	private final int destinationX;
	private final int destinationY;
	
	private final double length;
	private final double width;
	private final double speed;
	
	private final double dx;	// delta x per 1 second, based on speed
	private final double dy;	// delta y per 1 second
	
	private double X;
	private double Y;
	private double x1, x2, x3;		// relative to X
	private double y1, y2, y3;		// relative to Y
	
	private boolean hit;
	
}
