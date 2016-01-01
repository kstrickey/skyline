package skyline.alienStuff;

import java.awt.Color;
import java.awt.Graphics;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class MouseTarget implements Tickable, Drawable {
	
	public MouseTarget(Engine engine) {
		color = Color.black;
	}
	
	public void setPosition(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		if (engine.isAliensArrived()) {
			color = Color.red;
		} else {
			color = Color.black;
		}
	}
	
	@Override
	public void draw(Graphics page) {
		page.setColor(color);
		page.fillRect(X - 1, Y - 15, 4, 11);	// Top spoke
		page.fillRect(X - 15, Y - 1, 11, 4);	// Left spoke
		page.fillRect(X - 1, Y + 5, 4, 11);		// Bottom spoke
		page.fillRect(X + 5, Y - 1, 11, 4);		// Right spoke
	}
	
	private int X;
	private int Y;
	
	private Color color;
	
}
