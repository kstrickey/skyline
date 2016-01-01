package skyline.landscape;

import java.awt.Graphics;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

/**
 * A class encompassing everything about the background, including the grass, sky, street, sun, moon, stars, etc.
 *
 */
public class Landscape implements Tickable, Drawable {

	public Landscape(Engine engine) {
		ground = new Ground(engine);
		sky = new Sky(engine);
	}
	
	public Ground getGround() {
		return ground;
	}
	
	public Sky getSky() {
		return sky;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		
		ground.tick(timeElapsed, engine);
		sky.tick(timeElapsed, engine);
		
	}

	@Override
	public void draw(Graphics page) {
		
		sky.draw(page);
		ground.draw(page);
		
	}
	
	
	private Ground ground;
	private Sky sky;
	
}
