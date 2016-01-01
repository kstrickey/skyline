package skyline.landscape;

import java.awt.Color;
import java.awt.Graphics;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class Sky implements Tickable, Drawable {
	
	public Sky(Engine engine) {
		width = engine.WIDTH;
		height = (int) ((double) engine.HEIGHT * .66666667);
		
		color = Color.cyan;
		sun = new Sun(engine);
		moon = new Moon(engine);
	}
	
	
	public Color getColor() {
		return color;
	}
	
	
	@Override
	public void draw(Graphics page) {
		
		page.setColor(color);
		page.fillRect(0, 0, width, height);
		
		sun.draw(page);
		moon.draw(page);
	}

	@Override
	public void tick(double timeElapsed, Engine engine) {
		
		sun.tick(timeElapsed, engine);
		moon.tick(timeElapsed, engine);
	}
	
	
	private int width;
	private int height;
	
	private Color color;
	
	private Sun sun;
	private Moon moon;
	
}
