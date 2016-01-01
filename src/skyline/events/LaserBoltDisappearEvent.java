package skyline.events;

import skyline.Engine;
import skyline.alienStuff.LaserBolt;

public class LaserBoltDisappearEvent extends Event {

	public LaserBoltDisappearEvent(LaserBolt bolt) {
		this.bolt = bolt;
	}
	
	@Override
	public void execute(Engine engine) {
		engine.getAlienStuff().getLaserBolts().remove(bolt);
	}
	
	private LaserBolt bolt;
	
}
