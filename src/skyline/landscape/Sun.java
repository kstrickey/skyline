package skyline.landscape;

import java.awt.Color;
import java.awt.Graphics;
import java.util.GregorianCalendar;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class Sun implements Tickable, Drawable {

	public Sun(Engine engine) {
		RADIUS = engine.HEIGHT / 20;
		calculatePosition(engine);
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		
		calculatePosition(engine);
		
	}

	@Override
	public void draw(Graphics page) {
		
		if (onScreen) {
			page.setColor(Color.yellow);
			page.fillOval((int) X - RADIUS, (int) Y - RADIUS, RADIUS * 2, RADIUS * 2);
		}
		
	}
	
	
	
	/**
	 * Calculates X and Y given the Engine object's Calendar value.
	 * @param engine : The Engine object holding the Calendar value 
	 */
	private void calculatePosition(Engine engine) {
		
		int minutesAfterSix = (engine.getCalendar().get(GregorianCalendar.HOUR_OF_DAY) - 6) * 60 + engine.getCalendar().get(GregorianCalendar.MINUTE);
		
		if (minutesAfterSix < 0 || minutesAfterSix > 12 * 60) {
			onScreen = false;
			return;
		}
		
		onScreen = true;
		// X = pixels/minute * minute
		X = (double) (engine.WIDTH + 2 * RADIUS) / (12.0*60.0) * minutesAfterSix;
		// Y = a(x-h)^2 + k
		Y = (4.0 / 3.0 * engine.HEIGHT / engine.WIDTH / engine.WIDTH) * Math.pow((X - engine.WIDTH / 2), 2) + engine.HEIGHT / 15.0;
		
	}
	
	private boolean onScreen;
	
	private double X;		// denotes CENTER position
	private double Y;
	private final int RADIUS;
	

}
