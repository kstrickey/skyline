package skyline.allPeople;

import skyline.Engine;
import skyline.LaserTarget;
import skyline.events.PersonOnGroundLeaveBoundsEvent;

/**
 * Represents a person walking around on the ground, not in a Building, etc.
 *
 */
public abstract class PersonOnGround extends Person implements LaserTarget {
	
	public PersonOnGround(Engine engine) {
		horizonY = (int) (engine.HEIGHT * 2.0 / 3);
		screenBottomY = engine.HEIGHT;
		normalWidth = (int) (engine.WIDTH / 100.0);
		normalHeight = (int) (engine.HEIGHT * 2.0 / 75.0);
		depth = generator.nextInt(2) + generator.nextDouble();
		calculateSize();
		X = generator.nextInt(engine.WIDTH);
		directionFacing = Direction.values()[generator.nextInt(Direction.values().length)];
		stageOfWalking = generator.nextInt(1);
		awareOfAliens = false;
		walking = generator.nextBoolean() || generator.nextBoolean();		// 3/4 walking, 1/4 standing
	}
	
	
	public double getDepth() {
		return depth;
	}
	
	public double getX() {
		return X;
	}
	
	public double getY() {
		return Y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public Direction getDirectionFacing() {
		return directionFacing;
	}
	
	public double getStageOfWalking() {
		return stageOfWalking;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		awareOfAliens = engine.isAliensArrived();
		
		if (awareOfAliens) {
			normalVelocity = runningSpeed;
		} else {
			changeDirectionOnASimpleWhim();
			
			if (walking) {
				normalVelocity = walkingSpeed;
			} else {
				normalVelocity = 0;
			}
		}
		
		switch (directionFacing) {
		case RIGHT:
			X += normalVelocity * depthMultiplier * timeElapsed;
			break;
		case LEFT:
			X -= normalVelocity * depthMultiplier * timeElapsed;
			break;
		case OUT:
			depth += normalVelocity * timeElapsed / engine.WIDTH * 3.0;
			if (depth >= 2) {
				depth = 2;
				changeDirection();
			}
			break;
		case IN:
			depth -= normalVelocity * timeElapsed / engine.WIDTH * 3.0;
			if (depth <= 0) {
				depth = 0;
				changeDirection();
			}
		}
		calculateSize();
		
		advanceStageOfWalking(timeElapsed);
		
		if (X < 0 - width || X > engine.WIDTH + width) {
			engine.plotEvent(new PersonOnGroundLeaveBoundsEvent(this));
		}
	}

	
	
	/**
	 * Sets the data of the PersonOnGround to match as if he/she just walked out of a door of a Building. Meant for use in the CivilianExitFromBuildingEvent class.
	 * @param doorPosition : int indicating the X-position of the middle of the door.
	 */
	public void walkOutOfDoor(int doorPosition) {
		X = doorPosition;
		depth = 0;
		calculateSize();
		directionFacing = Direction.IN;
		changeDirection();			// any direction but IN
		stageOfWalking = 0;
		walking = true;
	}
	
	
	/**
	 * Uses depth to calculate depthMultiplier and Y, then calculate width and height.
	 */
	private void calculateSize() {
		depthMultiplier = Math.pow(2, depth);
		Y = horizonY + Math.pow(depth, 2) * (screenBottomY - horizonY) / 4;
		
		width = (int) (normalWidth * depthMultiplier);
		height = (int) (normalHeight * depthMultiplier);
	}
	
	/**
	 * Changes the directionFacing of the PersonOnGround.
	 */
	private void changeDirection() {
		Direction oldDirection = directionFacing;
		while (directionFacing == oldDirection) {
			directionFacing = Direction.values()[generator.nextInt(Direction.values().length)];
		}
	}
	
	/**
	 * Gives a small, random chance for the PersonOnGround to stop, start, or change directions. Meant for while not awareOfAliens.
	 */
	private void changeDirectionOnASimpleWhim() {
		int randNum = generator.nextInt(5000);
		if (walking == true) {
			if (randNum <= 1) {
				walking = false;
			} else if (randNum <= 7) {
				changeDirection();
			}
		} else {
			if (randNum <= 1) {
				walking = true;
				if (generator.nextBoolean()) {
					changeDirection();
				}
			}
		}
	}
	
	/**
	 * Updates the stageOfWalking value of the PersonOnGround object, based on whether stopped, walking, or running.
	 * @param timeElapsed : double how much time elapsed in that cycle
	 */
	private void advanceStageOfWalking(double timeElapsed) {
		if (awareOfAliens) {
			stageOfWalking += 500 * timeElapsed / runningSpeed;
		} else if (walking) {
			stageOfWalking += 50 * timeElapsed / walkingSpeed;
		} else {
			stageOfWalking = 0;
		}
		while (stageOfWalking >= 2) {
			stageOfWalking -= 2;
		}
	}
	
	private final int horizonY;
	private final int screenBottomY;
	
	private final int normalWidth;
	private final int normalHeight;
	
	private int width;
	private int height;
	
	private double X;		// AT BOTTOM-CENTER
	private double Y;		// AT BOTTOM-CENTER
	private double depth;	// 0-on horizon; 2-bottom of screen (linear)
	private double depthMultiplier;	//calculated from depth (2^depth)
	
	private Direction directionFacing;
	private double normalVelocity;
	private double stageOfWalking;		// <1 if standing, <2 if stepping
	
	private boolean awareOfAliens;		// If he is aware of the aliens, then he is running.
	private boolean walking;			// If he is not aware of the aliens, is he walking (true) or stopped & standing (false)?
		
	private final double walkingSpeed = 8 + generator.nextInt(4) + generator.nextDouble();
	private final double runningSpeed = 35 + generator.nextInt(15) + generator.nextDouble();

}
