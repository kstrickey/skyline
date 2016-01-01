package skyline.allPeople;

import java.awt.Color;
import java.awt.Graphics;

import skyline.Engine;

public class DeadCivilian extends DeadPerson {

	public DeadCivilian(Engine engine, int X, double depth) {
		super(engine, X, depth);
	}

	@Override
	public void tick(double timeElapsed, Engine engine) {
		super.tick(timeElapsed, engine);
	}
	
	@Override
	public void draw(Graphics page) {
		page.setColor(new Color(75 + generator.nextInt(100), 0, 0));
		page.fillOval((int) (getX() - getWidth() / 2), (int) (getY() - getHeight() / 4), (int) getWidth(), (int) (getHeight() / 4));
	}

}
