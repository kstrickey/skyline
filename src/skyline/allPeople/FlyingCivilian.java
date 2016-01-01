package skyline.allPeople;

import java.awt.Color;
import java.awt.Graphics;

import skyline.Engine;

public class FlyingCivilian extends FlyingPerson {

	public FlyingCivilian(Engine engine, double X, double depth, double altitude) {
		super(engine, X, depth, altitude);
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		super.tick(timeElapsed, engine);
	}
	
	@Override
	public void draw(Graphics page) {
		double Y = getY() - getHeight();	// top-left corner (unlike in superclass)
		double X = getX() - getWidth() / 2;
		double s = getHeight() / 16;
		
		page.setColor(getPantsColor());
		page.fillRect((int) X, (int) (Y + 3*s), (int) (4*s), (int) (2*s));												// fill right leg
		page.fillRect((int) (X + 4*s), (int) (Y + 3*s), (int) (4*s), (int) (2*s));										// fill left leg
		page.setColor(Color.black);
		page.drawRect((int) X, (int) (Y + 3*s), (int) (4*s), (int) (2*s));												// outline right leg
		page.drawRect((int) (X + 4*s), (int) (Y + 3*s), (int) (4*s), (int) (2*s));										// outline left leg
		page.setColor(getShoesColor());
		page.fillRoundRect((int) (X - s), (int) (Y + 2.5*s), (int) s, (int) (4*s), (int) (.5*s), (int) (.5*s));			// fill right shoe
		page.fillRoundRect((int) (X + 8*s), (int) (Y + 2.5*s), (int) s, (int) (4*s), (int) (.5*s), (int) (.5*s));		// fill left shoe
		page.setColor(getShirtColor());
		page.fillRect((int) (X + 2*s), (int) (Y + 5*s), (int) (4*s), (int) (8*s));										// fill torso
		page.fillRect((int) X, (int) (Y + 8*s), (int) (3*s), (int) s);													// fill right arm
		page.fillRect((int) (X + 5*s), (int) (Y + 8*s), (int) (3*s), (int) s);											// fill left arm
		page.setColor(Color.black);
		page.drawRect((int) X, (int) (Y + 8*s), (int) (3*s), (int) s);													// outline right arm
		page.drawRect((int) (X + 5*s), (int) (Y + 8*s), (int) (3*s), (int) s);											// outline left arm
		page.setColor(getSkinColor());
		page.fillOval((int) (X - .5*s), (int) (Y + 8*s), (int) s, (int) s);												// fill right hand
		page.fillOval((int) (X + 7.5*s), (int) (Y + 8*s), (int) s, (int) s);											// fill left hand
		page.fillOval((int) (X + s), (int) (Y + 10*s), (int) (6*s), (int) (6*s));										// fill head
	}

}
