package skyline.landscape;

import java.awt.Color;
import java.awt.Graphics;
import java.util.GregorianCalendar;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class Moon implements Tickable, Drawable {
	
	public Moon(Engine engine) {
		RADIUS = engine.HEIGHT / 30;
		calculatePosition(engine);
	}
	
	@Override
	public void draw(Graphics page) {
		
		if (onScreen) {
			page.setColor(Color.white);
			page.fillOval((int) X - RADIUS, (int) Y - RADIUS, RADIUS * 2, RADIUS * 2);
			page.setColor(skyColor);
			page.fillOval((int) X + crescentOffset - RADIUS, (int) Y - RADIUS, RADIUS * 2, RADIUS * 2);
		}
		
	}

	@Override
	public void tick(double timeElapsed, Engine engine) {
		
		calculatePosition(engine);
		skyColor = engine.getLandscape().getSky().getColor();
		
	}
	
	
	/**
	 * Calculates X and Y given the Engine object's Calendar value.
	 * @param engine : The Engine object containing the Calendar value
	 */
	private void calculatePosition(Engine engine) {
		
		int minutesAfterEighteen = (engine.getCalendar().get(GregorianCalendar.HOUR_OF_DAY) - 18) * 60 + engine.getCalendar().get(GregorianCalendar.MINUTE);
		if (minutesAfterEighteen < 0) {
			minutesAfterEighteen += 24 * 60;
		}
		
		if (minutesAfterEighteen < 0 || minutesAfterEighteen > 12 * 60) {
			onScreen = false;
			return;
		}
		
		onScreen = true;
		// X = pixels/minute * minute
		X = (double) (engine.WIDTH + 2 * RADIUS) / (12.0*60.0) * minutesAfterEighteen;
		// Y = a(x-h)^2 + k
		Y = (4.0 / 3.0 * engine.HEIGHT / engine.WIDTH / engine.WIDTH) * Math.pow((X - engine.WIDTH / 2), 2) + engine.HEIGHT / 15.0;
		
		
		crescentOffset = (engine.getCalendar().get(GregorianCalendar.DAY_OF_YEAR) % 30) * 2 - 30; 
		
	}
	
	private boolean onScreen;
	
	private double X;		// denotes CENTER position
	private double Y;
	private final int RADIUS;

	private int crescentOffset;	// how far the black circle is covering it
	private Color skyColor;		//for making crescent

}
