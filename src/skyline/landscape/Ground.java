package skyline.landscape;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import skyline.Drawable;
import skyline.Engine;
import skyline.LaserTarget;
import skyline.Tickable;

public class Ground implements Tickable, Drawable, LaserTarget {
	
	public Ground(Engine engine) {
		ENGINE_WIDTH = engine.WIDTH;
		ENGINE_HEIGHT = engine.HEIGHT;
		
		laserBurns = new ArrayList<LaserBurn>();
	}
	
	
	@Override
	public void draw(Graphics page) {
		
		// Draw grass
		page.setColor(Color.green);
		page.fillRect(0, (int) ((double) ENGINE_HEIGHT * 2/3), ENGINE_WIDTH, (int) ((double) ENGINE_HEIGHT / 3));
		
		//Draw street
		page.setColor(Color.gray);
		page.fillRect(0, (int) ((double) ENGINE_HEIGHT * 4/5), ENGINE_WIDTH, (int) ((double) ENGINE_HEIGHT * 1/15));
		page.setColor(Color.yellow);
		for (int x = /*25*/ ENGINE_WIDTH / 40; x < /*1000*/ ENGINE_WIDTH; x += /*100*/ ENGINE_WIDTH / 10) {
			page.fillRect(x, /*623*/ ENGINE_HEIGHT * 5/6 - ENGINE_HEIGHT / 300, /*50*/ ENGINE_WIDTH / 20, /*5*/ ENGINE_HEIGHT / 150);
		}
		
		for (LaserBurn burn : laserBurns) {
			burn.draw(page);
		}
	}

	@Override
	public void tick(double timeElapsed, Engine engine) {
		
	}
	
	@Override
	public void laserHits(Engine engine, int X, int Y) {
		if (Y > ENGINE_HEIGHT * 2.0/3.0) {
			// Add to laserBurns
			laserBurns.add(new LaserBurn(X, Y, ENGINE_HEIGHT));
		}
	}

	
	private final int ENGINE_HEIGHT;
	private final int ENGINE_WIDTH;
	
	private ArrayList<LaserBurn> laserBurns;

}
