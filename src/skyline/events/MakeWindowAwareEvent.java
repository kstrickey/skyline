package skyline.events;

import skyline.Engine;
import skyline.city.Window;

public class MakeWindowAwareEvent extends Event {

	public MakeWindowAwareEvent(Window window) {
		this.window = window;
	}
	
	
	@Override
	public void execute(Engine engine) {
		
		window.becomeAware();
		
	}
	
	
	private Window window;
	
}
