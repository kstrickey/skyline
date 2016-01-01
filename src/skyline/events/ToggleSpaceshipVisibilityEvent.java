package skyline.events;

import skyline.Engine;

public class ToggleSpaceshipVisibilityEvent extends Event {

	@Override
	public void execute(Engine engine) {
		engine.getAlienStuff().getSpaceship().toggleVisibility();
		engine.aliensArriveOrDepart();
	}

}
