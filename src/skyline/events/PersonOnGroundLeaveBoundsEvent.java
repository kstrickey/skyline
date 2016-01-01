package skyline.events;

import skyline.Engine;
import skyline.allPeople.PersonOnGround;

public class PersonOnGroundLeaveBoundsEvent extends Event {
	
	public PersonOnGroundLeaveBoundsEvent(PersonOnGround leavingPerson) {
		this.leavingPerson = leavingPerson;
	}

	@Override
	public void execute(Engine engine) {
		engine.getAllPeople().getPeopleOnGround().remove(leavingPerson);
		engine.addEscaper();
	}
	
	private PersonOnGround leavingPerson;
	
}
