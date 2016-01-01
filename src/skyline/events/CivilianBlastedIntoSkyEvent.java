package skyline.events;

import skyline.Engine;
import skyline.allPeople.CivilianInBuilding;
import skyline.allPeople.CivilianOnGround;
import skyline.allPeople.FlyingCivilian;
import skyline.allPeople.Person;

public class CivilianBlastedIntoSkyEvent extends Event {
	
	public CivilianBlastedIntoSkyEvent(CivilianInBuilding victim) {
		this.victim = victim;
	}
	
	public CivilianBlastedIntoSkyEvent(CivilianOnGround victim) {
		this.victim = victim;
	}
	
	@Override
	public void execute(Engine engine) {
		double X = 0, depth = 0, altitude = 0;
		
		if (victim instanceof CivilianOnGround) {
			engine.getAllPeople().getPeopleOnGround().remove(victim);
			
			X = ((CivilianOnGround) victim).getX();
			depth = ((CivilianOnGround) victim).getDepth();
			altitude = 0;
		} else if (victim instanceof CivilianInBuilding) {
			engine.getAllPeople().getCiviliansInBuildings().remove(victim);
			
			X = ((CivilianInBuilding) victim).getWindow().getMiddleX();
			depth = 0;
			altitude = -(engine.HEIGHT * 2.0/3.0 - ((CivilianInBuilding) victim).getWindow().getBottomY());
		}
		
		FlyingCivilian newFlier = new FlyingCivilian(engine, X, depth, altitude);
		newFlier.copyDataFrom(victim);
		
		engine.getAllPeople().getFlyingPeople().add(newFlier);
	}
	
	private Person victim;
	
}
