package skyline.allPeople;

import java.awt.Color;
import java.awt.Graphics;

import skyline.Engine;
import skyline.events.CivilianBlastedIntoSkyEvent;


public class CivilianOnGround extends PersonOnGround {

	public CivilianOnGround(Engine engine) {
		super(engine);
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
		
		switch (getDirectionFacing()) {
		case RIGHT:
			
			if (getStageOfWalking() < 1) {
				
				page.setColor(getShoesColor());
				page.fillRoundRect((int) (X + 2*s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));			// fill back shoe
				page.fillRoundRect((int) (X + 3*s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));			// fill front shoe
				
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// fill back leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// outline back leg
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 3.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// fill front leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 3.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// outline front leg
				
				page.setColor(getShirtColor());
				page.fillRect((int) (X + 2*s), (int) (Y + 3*s), (int) (4*s), (int) (8*s) + 1 /*+1 to fill gap from (int)*/);	// fill torso
				page.setColor(Color.black);
				page.drawRect((int) (X + 4*s), (int) (Y + 7*s), (int) s, (int) (2.5*s));										// outline [front] arm
				page.setColor(getSkinColor());
				page.fillOval((int) (X + 4*s), (int) (Y + 9*s), (int) s, (int) s);												// fill [front] hand
				
			} else {
				
				page.setColor(getShoesColor());
				page.fillRoundRect((int) (X + .5*s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));			// fill back shoe
				page.fillRoundRect((int) (X + 4.5*s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));		// fill front shoe
				
				int[] xPts = new int[4];
				int[] yPts = new int[4];
				
				xPts[0] = (int) (X + 2.5*s);
				yPts[0] = (int) (Y + 11*s);
				xPts[1] = (int) (X + 4.5*s);
				yPts[1] = (int) (Y + 11*s);
				xPts[2] = (int) (X + 3*s);
				yPts[2] = (int) (Y + 15*s);
				xPts[3] = (int) (X + s);
				yPts[3] = (int) (Y + 15*s);
				page.setColor(getPantsColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill back leg
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline back leg
				
				xPts[0] = (int) (X + 3.5*s);
				yPts[0] = (int) (Y + 11*s);
				xPts[1] = (int) (X + 5.5*s);
				yPts[1] = (int) (Y + 11*s);
				xPts[2] = (int) (X + 7*s);
				yPts[2] = (int) (Y + 15*s);
				xPts[3] = (int) (X + 5*s);
				yPts[3] = (int) (Y + 15*s);
				page.setColor(getPantsColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill front leg
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline front leg
				
				xPts[0] = (int) (X + 4*s);
				yPts[0] = (int) (Y + 6*s);
				xPts[1] = (int) (X + 5*s);
				yPts[1] = (int) (Y + 7*s);
				xPts[2] = (int) (X + 2*s);
				yPts[2] = (int) (Y + 10*s);
				xPts[3] = (int) (X + s);
				yPts[3] = (int) (Y + 9*s);
				page.setColor(getShirtColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill back arm
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline back arm
				
				page.setColor(getShirtColor());
				page.fillRect((int) (X + 2*s), (int) (Y + 3*s), (int) (4*s), (int) (8*s) + 1 /*+1 to fill gap from (int)*/);	// fill torso
				
				xPts[0] = (int) (X + 4*s);
				yPts[0] = (int) (Y + 7*s);
				xPts[1] = (int) (X + 5*s);
				yPts[1] = (int) (Y + 6*s);
				xPts[2] = (int) (X + 8*s);
				yPts[2] = (int) (Y + 9*s);
				xPts[3] = (int) (X + 7*s);
				yPts[3] = (int) (Y + 10*s);
				page.setColor(getShirtColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill front arm
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline front arm
				
			}
			
			page.setColor(getSkinColor());
			page.fillOval((int) (X + s), (int) Y, (int) (6*s), (int) (6*s));													// fill head
			
			page.setColor(getHairColor());
			page.fillRect((int) (X + 2*s), (int) Y, (int) (5.5*s), (int) (2*s));												// fill hair on top
			if (!isFemale()) {
				page.fillRect((int) (X + s), (int) (Y + s), (int) (2*s), (int) (4*s));											// fill hair on back if male
			} else {
				page.fillRect((int) (X + s), (int) (Y + s), (int) (2*s), (int) (7*s));											// fill hair on back if female
			}
			
			break;
			
		case LEFT:
			
			if (getStageOfWalking() < 1) {
				
				page.setColor(getShoesColor());
				page.fillRoundRect((int) (X + 2*s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));			// fill back shoe
				page.fillRoundRect((int) (X + s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));			// fill front shoe
				
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 3.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// fill back leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 3.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// outline back leg
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// fill front leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (2*s), (int) (4*s));									// outline front leg
				
				page.setColor(getShirtColor());
				page.fillRect((int) (X + 2*s), (int) (Y + 3*s), (int) (4*s), (int) (8*s) + 1 /*+1 to fill gap from (int)*/);	// fill torso
				page.setColor(Color.black);
				page.drawRect((int) (X + 3*s), (int) (Y + 7*s), (int) s, (int) (2.5*s));										// outline [front] arm
				page.setColor(getSkinColor());
				page.fillOval((int) (X + 3*s), (int) (Y + 9*s), (int) s, (int) s);												// fill [front] hand
				
			} else {
				
				page.setColor(getShoesColor());
				page.fillRoundRect((int) (X + 3.5*s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));		// fill back shoe
				page.fillRoundRect((int) (X - .5*s), (int) (Y + 15*s), (int) (4*s), (int) s, (int) (s/2), (int) (s/2));			// fill front shoe
				
				int[] xPts = new int[4];
				int[] yPts = new int[4];
				
				xPts[0] = (int) (X + 5.5*s);
				yPts[0] = (int) (Y + 11*s);
				xPts[1] = (int) (X + 3.5*s);
				yPts[1] = (int) (Y + 11*s);
				xPts[2] = (int) (X + 5*s);
				yPts[2] = (int) (Y + 15*s);
				xPts[3] = (int) (X + 7*s);
				yPts[3] = (int) (Y + 15*s);
				page.setColor(getPantsColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill back leg
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline back leg
				
				xPts[0] = (int) (X + 4.5*s);
				yPts[0] = (int) (Y + 11*s);
				xPts[1] = (int) (X + 2.5*s);
				yPts[1] = (int) (Y + 11*s);
				xPts[2] = (int) (X + s);
				yPts[2] = (int) (Y + 15*s);
				xPts[3] = (int) (X + 3*s);
				yPts[3] = (int) (Y + 15*s);
				page.setColor(getPantsColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill front leg
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline front leg
				
				xPts[0] = (int) (X + 4*s);
				yPts[0] = (int) (Y + 6*s);
				xPts[1] = (int) (X + 3*s);
				yPts[1] = (int) (Y + 7*s);
				xPts[2] = (int) (X + 5*s);
				yPts[2] = (int) (Y + 10*s);
				xPts[3] = (int) (X + 7*s);
				yPts[3] = (int) (Y + 9*s);
				page.setColor(getShirtColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill back arm
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline back arm
				
				page.setColor(getShirtColor());
				page.fillRect((int) (X + 2*s), (int) (Y + 3*s), (int) (4*s), (int) (8*s) + 1 /*+1 to fill gap from (int)*/);	// fill torso
				
				xPts[0] = (int) (X + 4*s);
				yPts[0] = (int) (Y + 7*s);
				xPts[1] = (int) (X + 3*s);
				yPts[1] = (int) (Y + 6*s);
				xPts[2] = (int) X;
				yPts[2] = (int) (Y + 9*s);
				xPts[3] = (int) (X + s);
				yPts[3] = (int) (Y + 10*s);
				page.setColor(getShirtColor());
				page.fillPolygon(xPts, yPts, 4);																				// fill front arm
				page.setColor(Color.black);
				page.drawPolygon(xPts, yPts, 4);																				// outline front arm
				
			}
			
			page.setColor(getSkinColor());
			page.fillOval((int) (X + s), (int) Y, (int) (6*s), (int) (6*s));													// fill head
			
			page.setColor(getHairColor());
			page.fillRect((int) (X + .5*s), (int) Y, (int) (5.5*s) + 1, (int) (2*s));											// fill hair on top
			if (!isFemale()) {
				page.fillRect((int) (X + 5*s), (int) (Y + s), (int) (2*s) + 1, (int) (4*s));									// fill hair on back if male
			} else {
				page.fillRect((int) (X + 5*s), (int) (Y + s), (int) (2*s) + 1, (int) (7*s));									// fill hair on back if female
			}
			
			break;
			
		case IN:
			
			if (getStageOfWalking() < 1) {
				page.setColor(getShoesColor());
				page.fillRect((int) (X + 1.5*s), (int) (Y + 15*s), (int) (2.5*s), (int) s);										// fill left shoe
				page.fillRect((int) (X + 4*s), (int) (Y + 15*s), (int) (2.5*s), (int) s);										// fill right shoe
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// fill left leg
				page.fillRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// fill right leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// outline left leg
				page.drawRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// outline right leg
			} else {
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (1.5*s), (int) (2*s));									// fill left (raised) leg
				page.fillRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// fill right leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// outline right leg
				page.setColor(getShoesColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 12.5*s), (int) (1.5*s), (int) (2*s));								// fill left (raised) shoe
				page.fillRect((int) (X + 4*s), (int) (Y + 15*s), (int) (1.5*s), (int) s);										// fill right shoe
			}
			
			page.setColor(getShirtColor());
			page.fillRect((int) (X + 2*s), (int) (Y + 3*s), (int) (4*s) + 1, (int) (8*s) + 1);									// fill torso
			page.setColor(getHairColor());
			page.fillOval((int) (X + s), (int) Y, (int) (6*s), (int) (6*s));													// fill head (hair, since facing in)
			if (isFemale()) {
				page.fillRect((int) (X + s), (int) (Y + 3*s), (int) (6*s), (int) (4*s));										// fill more hair if female
			}
			
			break;
			
		case OUT:
			
			page.setColor(getHairColor());
			if (isFemale()) {
				page.fillRect((int) (X + s), (int) Y, (int) (6*s), (int) (8*s));												// fill hair in back if female
			} else {
				page.fillRect((int) (X + s), (int) Y, (int) (6*s), (int) (3*s));												// fill hair in back if male
			}
			
			if (getStageOfWalking() < 1) {
				page.setColor(getShoesColor());
				page.fillRect((int) (X + 1.5*s), (int) (Y + 15*s), (int) (2.5*s), (int) s);										// fill left shoe
				page.fillRect((int) (X + 4*s), (int) (Y + 15*s), (int) (2.5*s), (int) s);										// fill right shoe
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// fill left leg
				page.fillRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// fill right leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// outline left leg
				page.drawRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// outline right leg
			} else {
				page.setColor(getPantsColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 11*s), (int) (1.5*s), (int) (3*s));									// fill right (raised) leg
				page.fillRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// fill left leg
				page.setColor(Color.black);
				page.drawRect((int) (X + 4*s), (int) (Y + 11*s), (int) (1.5*s), (int) (4*s));									// outline left leg
				page.setColor(getShoesColor());
				page.fillRect((int) (X + 2.5*s), (int) (Y + 13.5*s), (int) (1.5*s), (int) (s));									// fill right (raised) shoe
				page.fillRect((int) (X + 4*s), (int) (Y + 15*s), (int) (1.5*s), (int) s);										// fill left shoe
			}
			page.setColor(getShirtColor());
			page.fillRect((int) (X + 2*s), (int) (Y + 3*s), (int) (4*s) + 1, (int) (8*s) + 1);									// fill torso
			page.fillRect((int) (X + 1.5*s), (int) (Y + 7*s), (int) s, (int) (3*s));											// fill right arm (left arm from viewer's perspective)
			page.fillRect((int) (X + 5.5*s), (int) (Y + 7*s), (int) s, (int) (3*s));											// fill left arm (right from viewer's perspective)
			page.setColor(Color.black);
			page.drawRect((int) (X + 1.5*s), (int) (Y + 7*s), (int) s, (int) (3*s));											// outline right arm
			page.drawRect((int) (X + 5.5*s), (int) (Y + 7*s), (int) s, (int) (3*s));											// outline left arm
			page.setColor(getSkinColor());
			page.fillOval((int) (X + 1.5*s), (int) (Y + 9.5*s), (int) s, (int) s);												// fill right hand
			page.fillOval((int) (X + 5.5*s), (int) (Y + 9.5*s), (int) s, (int) s);												// fill left hand
			page.setColor(getSkinColor());
			page.fillOval((int) (X + s), (int) Y, (int) (6*s), (int) (6*s));													// fill face
			page.setColor(getHairColor());
			page.fillRect((int) (X + s), (int) Y, (int) (6*s), (int) s);														// fill hair bangs
			
			break;
			
		}
		
		//for  testing
		//page.setColor(Color.black);
		//page.drawRect((int)X,(int) Y, (int)getWidth(), (int) getHeight());//draws target box around body
	}
	
	@Override
	public void laserHits(Engine engine, int X, int Y) {
		if (X >= getX() - getWidth()/2 && X <= getX() + getWidth()/2 && Y <= getY() && Y >= getY() - getHeight()) {	// if laser hit within body area
			engine.plotEvent(new CivilianBlastedIntoSkyEvent(this));
		}
		
	}
	
	
	
}
