package skyline.events;

import skyline.Engine;
import skyline.allPeople.DeadCivilian;
import skyline.allPeople.DeadPerson;
import skyline.allPeople.FlyingCivilian;
import skyline.allPeople.FlyingPerson;

public class FlyingPersonHitsGroundEvent extends Event {
	
	public FlyingPersonHitsGroundEvent(FlyingPerson projectile) {
		this.projectile = projectile;
	}
	
	@Override
	public void execute(Engine engine) {
		engine.getAllPeople().getFlyingPeople().remove(projectile);
		DeadPerson casualty = null;		// must initialize depending on type of FlyingPerson object
		if (projectile instanceof FlyingCivilian) {
			casualty = new DeadCivilian(engine, (int) projectile.getX(), projectile.getDepth());
		}
		engine.getAllPeople().getDeadPeople().add(casualty);
	}
	
	private FlyingPerson projectile;
	
}
