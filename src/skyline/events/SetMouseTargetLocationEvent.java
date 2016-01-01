package skyline.events;

import skyline.Engine;

public class SetMouseTargetLocationEvent extends Event {

	public SetMouseTargetLocationEvent(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}
	
	@Override
	public void execute(Engine engine) {
		engine.getAlienStuff().getMouseTarget().setPosition(X, Y);
	}

	private int X;
	private int Y;
	
}
