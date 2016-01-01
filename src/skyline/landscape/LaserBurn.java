package skyline.landscape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class LaserBurn implements Tickable, Drawable {

	public LaserBurn(int X, int Y, int engineHeight) {
		color = Color.black;
		
		double horizonY = (engineHeight * 2.0 / 3);
		double screenBottomY = engineHeight;
		depth = Math.sqrt(4.0 * (Y - horizonY) / (screenBottomY - horizonY));
		depthMultiplier = Math.pow(2, depth);
		
		blackedPoints = new ArrayList<Point>();
		Random generator = new Random();
		for (double relativeX = -2 * depthMultiplier; relativeX <= 2 * depthMultiplier; relativeX++) {		// SIZE OF BURN NOT RELATIVE TO SCREEN SIZE
			for (double relativeY = -2 * depthMultiplier; relativeY <= 2 * depthMultiplier; relativeY++) {
				if (generator.nextInt((int) Math.pow(2, Math.sqrt(relativeX*relativeX + relativeY*relativeY))) <= 1) {
					blackedPoints.add(new Point((int) (X + relativeX), (int) (Y + relativeY)));
				}
			}
		}
	}
	
	@Override
	public void draw(Graphics page) {
		page.setColor(color);
		for (Point pt : blackedPoints) {
			page.drawLine((int) pt.getX(), (int) pt.getY(), (int) pt.getX(), (int) pt.getY());
		}
	}

	@Override
	public void tick(double timeElapsed, Engine engine) {
		
	}
	
	private Color color;
	
	private final double depth;
	private final double depthMultiplier;
	
	private final ArrayList<Point> blackedPoints;

}
