package skyline.events;

import skyline.Engine;

public class FireLaserBoltEvent extends Event {

	public FireLaserBoltEvent(int destinationX, int destinationY) {
		this.destinationX = destinationX;
		this.destinationY = destinationY;
	}
	
	@Override
	public void execute(Engine engine) {
		engine.getAlienStuff().fireLaserBolt(engine, destinationX, destinationY);
	}
	
	private int destinationX;
	private int destinationY;

}
