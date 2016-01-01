package skyline.alienStuff;

import java.awt.Color;
import java.awt.Graphics;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class Spaceship implements Tickable, Drawable {
	
	public Spaceship(Engine engine) {
		width = (int) (engine.WIDTH * 2.0/25.0);
		height = (int) (width * .75);
		maximumX = engine.WIDTH - width;
		maximumY = (int) (engine.HEIGHT * 2.0/3.0 - height - engine.HEIGHT/10.0);
		maximumSpeed = engine.WIDTH / 276.0;		// experimentally determined number
		standardAcceleration = maximumSpeed / 2.0;	// another experimentally determined number... maximumSpeed / time to accelerate from (or stop to) 0
		
		baseColor = Color.orange;
		topColor = Color.magenta;
		windowColor = Color.yellow;
		laserColor = Color.red;
		
		visible = false;
		
		X = engine.WIDTH / 3;
		Y = engine.HEIGHT / 15;
		velocityMagnitude = 0;
		velocityAngle = 0;
		accelerationMagnitude = 0;
		accelerationAngle = 0;
		
		rightDepressed = false;
		leftDepressed = false;
		upDepressed = false;
		downDepressed = false;
	}
	
	public double getX() {
		return X;
	}
	
	public double getY() {
		return Y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Color getLaserColor() {
		return laserColor;
	}
	
	
	public void pressRight() {
		rightDepressed = true;
	}
	public void pressLeft() {
		leftDepressed = true;
	}
	public void pressUp() {
		upDepressed = true;
	}
	public void pressDown() {
		downDepressed = true;
	}
	public void releaseRight() {
		rightDepressed = false;
	}
	public void releaseLeft() {
		leftDepressed = false;
	}
	public void releaseUp() {
		upDepressed = false;
	}
	public void releaseDown() {
		downDepressed = false;
	}
	
	/**
	 * Method called if space is pressed. Toggles whether boolean field visible.
	 */
	public void toggleVisibility() {
		visible = !visible;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		advanceAcceleration();
		advanceVelocity(timeElapsed);
		advancePosition(timeElapsed);
		
		if (X > maximumX) {
			X = maximumX;
			calculateVelocity(0, velocityMagnitude * Math.sin(velocityAngle));
		} else if (X < 0) {
			X = 0;
			calculateVelocity(0, velocityMagnitude * Math.sin(velocityAngle));
		}
		if (Y > maximumY) {
			Y = maximumY;
			calculateVelocity(velocityMagnitude * Math.cos(velocityAngle), 0);
		} else if (Y < 0) {
			Y = 0;
			calculateVelocity(velocityMagnitude * Math.cos(velocityAngle), 0);
		}
	}
	
	@Override
	public void draw(Graphics page) {
		if (visible) {
			page.setColor(baseColor);
			page.fillOval((int) X, (int) (Y + height * 1.0/3.0), (int) width, (int) (height * 2.0/3.0));
			page.setColor(topColor);
			page.fillOval((int) (X + width / 4.0), (int) (Y + height / 2.0), (int) (width / 2.0), (int) (height / 3.0));
			int[] trapezoidCornersX = {(int) (X + width / 4.0),			(int) (X + width * 3.0/4.0),	(int) (X + width * 2.0/3.0),	(int) (X + width / 3.0)};
			int[] trapezoidCornersY = {(int) (Y + height * 2.0/3.0),	(int) (Y + height * 2.0/3.0),	(int) (Y + height / 4.0),		(int) (Y + height / 4.0)};
			page.fillPolygon(trapezoidCornersX, trapezoidCornersY, 4);
			page.fillOval((int) (X + width / 3.0), (int) (Y + height / 4.0 - height / 10.0), (int) (width / 3.0), (int) (height / 5.0));
			page.setColor(windowColor);
			page.fillOval((int) (X + width / 3.3) + 1, (int) (Y + height / 3.0), (int) (width / 10.0), (int) (width / 10.0));
			page.fillOval((int) (X + width / 2.3) + 1, (int) (Y + height / 3.0), (int) (width / 10.0), (int) (width / 10.0));
			page.fillOval((int) (X + width / 1.8) + 1, (int) (Y + height / 3.0), (int) (width / 10.0), (int) (width / 10.0));
		} else {
			page.setColor(Color.black);
			page.drawOval((int) X, (int) (Y + height * 1.0/3.0), (int) width, (int) (height * 2.0/3.0));
			page.drawOval((int) (X + width / 4.0), (int) (Y + height / 2.0), (int) (width / 2.0), (int) (height / 3.0));
			int[] trapezoidCornersX = {(int) (X + width / 4.0),			(int) (X + width * 3.0/4.0),	(int) (X + width * 2.0/3.0),	(int) (X + width / 3.0)};
			int[] trapezoidCornersY = {(int) (Y + height * 2.0/3.0),	(int) (Y + height * 2.0/3.0),	(int) (Y + height / 4.0),		(int) (Y + height / 4.0)};
			page.drawPolygon(trapezoidCornersX, trapezoidCornersY, 4);
			page.drawOval((int) (X + width / 3.0), (int) (Y + height / 4.0 - height / 10.0), (int) (width / 3.0), (int) (height / 5.0));
			page.drawOval((int) (X + width / 3.3) + 1, (int) (Y + height / 3.0), (int) (width / 10.0), (int) (width / 10.0));
			page.drawOval((int) (X + width / 2.3) + 1, (int) (Y + height / 3.0), (int) (width / 10.0), (int) (width / 10.0));
			page.drawOval((int) (X + width / 1.8) + 1, (int) (Y + height / 3.0), (int) (width / 10.0), (int) (width / 10.0));
		}
	}
	
	
	/**
	 * Updates accelerationMagnitude and accelerationAngle based on what arrow keys are depressed.
	 */
	private void advanceAcceleration() {
			
		if (rightDepressed && leftDepressed && upDepressed && downDepressed) {			// if everything is pressed, it's like nothing is depressed
			accelerationMagnitude = 0;
			accelerationAngle = velocityAngle;
		} else if (rightDepressed && leftDepressed && upDepressed) {					// it's like only up is depressed
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * .5;
		} else if (rightDepressed && leftDepressed && downDepressed) {					// it's like only down is depressed
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * 1.5;
		} else if (upDepressed && downDepressed && rightDepressed) {					// it's like only right is depressed
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = 0;
		} else if (upDepressed && downDepressed && leftDepressed) {						// it's like only left is depressed
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI;
		} else if (upDepressed && downDepressed || rightDepressed && leftDepressed) {	// it's like nothing is depressed
			accelerationMagnitude = 0;
			accelerationAngle = velocityAngle;
		} else if (rightDepressed && upDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * .25;
		} else if (rightDepressed && downDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * 1.75;
		} else if (leftDepressed && upDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * .75;
		} else if (leftDepressed && downDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * 1.25;
		} else if (rightDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = 0;
		} else if (leftDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI;
		} else if (upDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * .5;
		} else if (downDepressed) {
			accelerationMagnitude = standardAcceleration;
			accelerationAngle = Math.PI * 1.5;
		} else {																		// nothing is depressed
			accelerationMagnitude = 0;
			accelerationAngle = velocityAngle;
		}
		
	}
	
	/**
	 * Updates velocity based on acceleration.
	 * @param timeElapsed : double
	 */
	private void advanceVelocity(double timeElapsed) {
		atFullSpeed = (Math.abs(velocityMagnitude - maximumSpeed) < Engine.TOLERANCE);
		
		// Convert to component form
		vX = velocityMagnitude * Math.cos(velocityAngle);
		vY = -velocityMagnitude * Math.sin(velocityAngle);
		
		// Subtract natural deceleration
		if (velocityMagnitude > standardAcceleration * timeElapsed) {
			vX -= standardAcceleration * Math.cos(velocityAngle) * timeElapsed;
			vY += standardAcceleration * Math.sin(velocityAngle) * timeElapsed;
		} else {
			vX = 0;
			vY = 0;
		}
		
		// Add acceleration components * timeElapsed to velocity components
		vX += accelerationMagnitude * Math.cos(accelerationAngle) * timeElapsed;
		vY -= accelerationMagnitude * Math.sin(accelerationAngle) * timeElapsed;
		
		// Atone for rounding errors
		if (Math.abs(vX) < Engine.TOLERANCE) {
			vX = 0;
		}
		if (Math.abs(vY) < Engine.TOLERANCE) {
			vY = 0;
		}
		
		// Convert back to polar form
		calculateVelocity(vX, vY);
		
		if (velocityMagnitude > maximumSpeed || atFullSpeed && accelerationMagnitude != 0) {		// if the speed > maximumSpeed OR it was at full speed and still accelerating
			velocityMagnitude = maximumSpeed;
		}
	}
	
	/**
	 * Updates X and Y based on velocityMagnitude and velocityAngle.
	 * @param timeElapsed : double
	 */
	private void advancePosition(double timeElapsed) {
		X += velocityMagnitude * Math.cos(velocityAngle) * timeElapsed * 10000;	// experimentally determined multiplier
		Y += velocityMagnitude * Math.sin(velocityAngle) * timeElapsed * 10000;
	}
	
	/**
	 * Takes vX and vY and calculates velocityMagnitude and velocityAngle.
	 * @param vX : double
	 * @param vY : double
	 */
	private void calculateVelocity(double vX, double vY) {
		velocityMagnitude = Math.sqrt(vX*vX + vY*vY);
		if (vX != 0 && vY != 0) {
			if (vX > 0 && vY > 0) {				// fourth quadrant
				velocityAngle = Math.PI * 2 + Math.atan(vY / vX);
			} else if (vX < 0 && vY < 0) {		// second quadrant
				velocityAngle = Math.PI + Math.atan(vY / vX);
			} else if (vX < 0 && vY > 0) {		// third quadrant
				velocityAngle = Math.PI + Math.atan(vY / vX);
			} else if (vX > 0 && vY < 0) {		// first quadrant
				velocityAngle = Math.atan(vY / vX);
			}
		} else if (vX != 0) {
			if (vX > 0) {
				velocityAngle = 0;
			} else {
				velocityAngle = Math.PI;
			}
		} else if (vY != 0) {
			if (vY < 0) {
				velocityAngle = Math.PI * 1.5;	//lolwut
			} else {								// should not these two be switched? Y it be workin?
				velocityAngle = Math.PI * .5;	//lolwut
			}
		} else {	// vX and vY == 0
			// do nothing..
		}
	}
	
	
	private final int width;
	private final int height;
	
	private final int maximumX;
	private final int maximumY;
	
	private final double maximumSpeed;
	
	private final double standardAcceleration;
	
	private Color baseColor;
	private Color topColor;
	private Color windowColor;
	private Color laserColor;
	
	private boolean visible;
	
	private double X;		// top-left corner
	private double Y;		// top-left corner
	private double velocityMagnitude;	// a.k.a. speed
	private double velocityAngle;
	private double accelerationMagnitude;
	private double accelerationAngle;
	
	private double vX;	// used for velocity calculations only
	private double vY;
	private boolean atFullSpeed;
	
	private boolean rightDepressed;
	private boolean leftDepressed;
	private boolean upDepressed;
	private boolean downDepressed;
	
}
