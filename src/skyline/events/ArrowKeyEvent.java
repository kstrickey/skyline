package skyline.events;

import skyline.Engine;

public class ArrowKeyEvent extends Event {

	public ArrowKeyEvent(ArrowKeyType key, boolean pressed) {
		this.key = key;
		this.pressed = pressed;
	}
	
	@Override
	public void execute(Engine engine) {
		switch (key) {
		case RIGHT:
			if (pressed) {
				engine.getAlienStuff().getSpaceship().pressRight();
			} else {
				engine.getAlienStuff().getSpaceship().releaseRight();
			}
			break;
		case LEFT:
			if (pressed) {
				engine.getAlienStuff().getSpaceship().pressLeft();
			} else {
				engine.getAlienStuff().getSpaceship().releaseLeft();
			}
			break;
		case UP:
			if (pressed) {
				engine.getAlienStuff().getSpaceship().pressUp();
			} else {
				engine.getAlienStuff().getSpaceship().releaseUp();
			}
			break;
		case DOWN:
			if (pressed) {
				engine.getAlienStuff().getSpaceship().pressDown();
			} else {
				engine.getAlienStuff().getSpaceship().releaseDown();
			}
			break;
		}
	}
	
	private ArrowKeyType key;
	private boolean pressed;	// false if released

}
