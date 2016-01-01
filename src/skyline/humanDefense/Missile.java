package skyline.humanDefense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Random;

import skyline.Drawable;
import skyline.Engine;
import skyline.LaserTarget;
import skyline.Tickable;
import skyline.events.MissileDisappearEvent;
import skyline.events.YOU_LOSE_Event;

public class Missile implements Tickable, Drawable, LaserTarget {
	
	public Missile(Engine engine) {
		Random generator = new Random();
		width = (int) (engine.WIDTH * 2.0/25.0);	// Same as Spaceship
		height = (int) (width * .5);			// except height
		
		speed = engine.WIDTH / (200 + generator.nextInt(200));
		
		exploding = 0;
		hitSpaceship = false;
		
		facingRight = generator.nextBoolean();
		if (facingRight) {
			X = 0;
		} else {
			X = engine.WIDTH;
		}
		// Initial Y cannot be above spaceship (so missile cannot head toward ground)
		Y = engine.getAlienStuff().getSpaceship().getY() + generator.nextInt((int) (engine.HEIGHT * 2.0/3.0 - engine.getAlienStuff().getSpaceship().getY())) + generator.nextDouble();
		
		// Always aimed toward spaceship's current position.
		double xDifference = engine.getAlienStuff().getSpaceship().getX() - X;
		double yDifference = engine.getAlienStuff().getSpaceship().getY() - Y;
		double totalDistance = Math.sqrt(xDifference*xDifference + yDifference*yDifference);
		
		vX = xDifference / totalDistance * speed;
		vY = yDifference / totalDistance * speed;
	}

	@Override
	public void draw(Graphics page) {
		if (facingRight) {
			int[] xPts = {(int) (X - width * 2.0/3.0), (int) (X - width), (int) (X - width)};
			int[] yPts = {(int) (Y + height / 3.0), (int) (Y + height / 3.0), (int) Y};
			page.setColor(finColor);
			if (exploding > 0) {
				page.setColor(new Color(90 + new Random().nextInt(155), 0, 0));
			}
			page.fillPolygon(xPts, yPts, 3);																				// fill top fin
			page.setColor(Color.black);
			page.drawPolygon(xPts, yPts, 3);																				// outline top fin
			xPts[0] = (int) (X - width * 2.0/3.0);
			yPts[0] = (int) (Y + height * 2.0/3.0);
			xPts[1] = (int) (X - width);
			yPts[1] = (int) (Y + height * 2.0/3.0);
			xPts[2] = (int) (X - width);
			yPts[2] = (int) (Y + height);
			page.setColor(finColor);
			if (exploding > 0) {
				page.setColor(new Color(90 + new Random().nextInt(155), 0, 0));
			}
			page.fillPolygon(xPts, yPts, 3);																				// fill bottom fin
			page.setColor(Color.black);
			page.drawPolygon(xPts, yPts, 3);																				// outline bottom fin
			page.setColor(bodyColor);
			if (exploding > 0) {
				page.setColor(new Color(90 + new Random().nextInt(155), 0, 0));
			}
			page.fillOval((int) (X - width * .75), (int) (Y + height * .25), (int) (width * .75), (int) (height * .5));		// fill body
			page.setColor(Color.black);
			page.drawOval((int) (X - width * .75), (int) (Y + height * .25), (int) (width * .75), (int) (height * .5));		// outline body
		} else {
			int[] xPts = {(int) (X + width * 2.0/3.0), (int) (X + width), (int) (X + width)};
			int[] yPts = {(int) (Y + height / 3.0), (int) (Y + height / 3.0), (int) Y};
			page.setColor(finColor);
			if (exploding > 0) {
				page.setColor(new Color(90 + new Random().nextInt(155), 0, 0));
			}
			page.fillPolygon(xPts, yPts, 3);																				// fill top fin
			page.setColor(Color.black);
			page.drawPolygon(xPts, yPts, 3);																				// outline top fin
			xPts[0] = (int) (X + width * 2.0/3.0);
			yPts[0] = (int) (Y + height * 2.0/3.0);
			xPts[1] = (int) (X + width);
			yPts[1] = (int) (Y + height * 2.0/3.0);
			xPts[2] = (int) (X + width);
			yPts[2] = (int) (Y + height);
			page.setColor(finColor);
			if (exploding > 0) {
				page.setColor(new Color(90 + new Random().nextInt(155), 0, 0));
			}
			page.fillPolygon(xPts, yPts, 3);																				// fill bottom fin
			page.setColor(Color.black);
			page.drawPolygon(xPts, yPts, 3);																				// outline bottom fin
			page.setColor(bodyColor);
			if (exploding > 0) {
				page.setColor(new Color(90 + new Random().nextInt(155), 0, 0));
			}
			page.fillOval((int) X, (int) (Y + height * .25), (int) (width * .75), (int) (height * .5));						// fill body
			page.setColor(Color.black);
			page.drawOval((int) X, (int) (Y + height * .25), (int) (width * .75), (int) (height * .5));						// outline body
		}
	}

	@Override
	public void tick(double timeElapsed, Engine engine) {
		if (exploding == 0) {
			X += vX;
			Y += vY;
			
			// Collision with Spaceship detection (start exploding)
			Area area = null;
			if (facingRight) {
				area = new Area(new Rectangle((int) (X - width), (int) Y, width, height));
			} else {
				area = new Area(new Rectangle((int) X, (int) Y, width, height));
			}
			if (area.intersects((int) engine.getAlienStuff().getSpaceship().getX(), (int) engine.getAlienStuff().getSpaceship().getY(), 
					engine.getAlienStuff().getSpaceship().getWidth(), engine.getAlienStuff().getSpaceship().getHeight())) {
				hitSpaceship = true;
				exploding = .001;
			}
			
			// Offscreen detection
			if (Y + height < 0 || facingRight && (X - width > engine.WIDTH) || !facingRight && (X + width < 0)) {
				engine.plotEvent(new MissileDisappearEvent(this));
			}
		} else {
			exploding+= timeElapsed;
			if (exploding > .4) {
				engine.plotEvent(new MissileDisappearEvent(this));
				if (hitSpaceship) {
					engine.plotEvent(new YOU_LOSE_Event());
				}
			}
		}
		
		
	}
	
	@Override
	public void laserHits(Engine engine, int X, int Y) {
		if (Y > this.Y && Y < this.Y + height && ((facingRight && X > this.X - width && X < this.X) || (!facingRight && X > this.X && X < this.X + width))) {
			exploding = .001;
		}
	}
	
	private final boolean facingRight;
	private final int width;
	private final int height;
	
	private double exploding;
	private boolean hitSpaceship;
	
	private double X;		// denotes the TIP end of the missile (right/left depends on facingRight)
	private double Y;		// at TOP (top corner at front end)
	
	private final double speed;	
	private final double vX;
	private final double vY;
	
	private final Color finColor = Color.green;
	private final Color bodyColor = Color.pink;
	
}
