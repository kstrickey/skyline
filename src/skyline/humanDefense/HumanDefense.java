package skyline.humanDefense;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;
import skyline.events.FireMissileEvent;

public class HumanDefense implements Drawable, Tickable {

	public HumanDefense(Engine engine) {
		missiles = new ArrayList<Missile>();
	}
	
	public ArrayList<Missile> getMissiles() {
		return missiles;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		fireMissiles(engine);
		
		for (Missile missile : missiles) {
			missile.tick(timeElapsed, engine);
		}
	}

	@Override
	public void draw(Graphics page) {
		for (Missile missile : missiles) {
			missile.draw(page);
		}
	}
	
	/**
	 * Randomly fires Missile objects at the Spaceship.
	 * @param engine : The all-encompassing Engine object
	 */
	private void fireMissiles(Engine engine) {
		if (engine.isAliensArrived()) {
			if (new Random().nextInt(2500) < engine.getTimeSinceAliensArrived()) {
				engine.plotEvent(new FireMissileEvent());
			}
		}
	}
	
	private ArrayList<Missile> missiles;
	
}
