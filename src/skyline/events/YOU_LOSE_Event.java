package skyline.events;

import skyline.Engine;

public class YOU_LOSE_Event extends Event {

	@Override
	public void execute(Engine engine) {
		engine.playerDie();
	}

}
