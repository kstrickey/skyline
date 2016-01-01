package skyline.events;

import skyline.Engine;
import skyline.humanDefense.Missile;

public class FireMissileEvent extends Event {
	
	public FireMissileEvent() {
		
	}
	
	@Override
	public void execute(Engine engine) {
		engine.getHumanDefense().getMissiles().add(new Missile(engine));
	}

}
