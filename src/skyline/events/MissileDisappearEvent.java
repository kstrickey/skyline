package skyline.events;

import skyline.Engine;
import skyline.humanDefense.Missile;

public class MissileDisappearEvent extends Event {

	public MissileDisappearEvent(Missile missile) {
		this.missile = missile;
	}
	
	@Override
	public void execute(Engine engine) {
		engine.getHumanDefense().getMissiles().remove(missile);
	}
	
	private Missile missile;
	
}
