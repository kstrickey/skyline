package skyline.events;

import skyline.Engine;
import skyline.allPeople.CivilianInBuilding;
import skyline.allPeople.CivilianOnGround;

public class CivilianExitFromBuildingEvent extends Event {

	public CivilianExitFromBuildingEvent(CivilianInBuilding exitingCivilianInBuilding, int doorPosition) {
		this.exitingCivilianInBuilding = exitingCivilianInBuilding;
		this.doorPosition = doorPosition;
	}

	@Override
	public void execute(Engine engine) {
		engine.getAllPeople().getCiviliansInBuildings().remove(exitingCivilianInBuilding);
		
		CivilianOnGround enteringCivilianOnGround = new CivilianOnGround(engine);
		enteringCivilianOnGround.copyDataFrom(exitingCivilianInBuilding);
		enteringCivilianOnGround.walkOutOfDoor(doorPosition);
		
		engine.getAllPeople().getPeopleOnGround().add(enteringCivilianOnGround);
	}
	
	private CivilianInBuilding exitingCivilianInBuilding;
	private int doorPosition;
}
