package skyline.allPeople;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;
import skyline.events.FlyingPersonHitsGroundEvent;

public abstract class FlyingPerson extends Person implements Drawable, Tickable {

	public FlyingPerson(Engine engine, double X, double depth, double altitude) {
		horizonY = (int) (engine.HEIGHT * 2.0 / 3);
		screenBottomY = engine.HEIGHT;
		normalWidth = (int) (engine.WIDTH / 100.0);
		normalHeight = (int) (engine.HEIGHT * 2.0 / 75.0);
		
		this.X = X;
		this.depth = depth;
		
		depthMultiplier = Math.pow(2, depth);
		Y = horizonY + Math.pow(depth, 2) * (screenBottomY - horizonY) / 4;
		
		width = (int) (normalWidth * depthMultiplier);
		height = (int) (normalHeight * depthMultiplier);
		
		this.altitude = altitude;
		vX = -60 + generator.nextInt(120) + generator.nextDouble();
		vY = -75 - generator.nextInt(55) - generator.nextDouble();
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
	
	public double getDepth() {
		return depth;
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		X += vX * depthMultiplier * timeElapsed;
		altitude += vY * depthMultiplier * timeElapsed;
		if (altitude >= 0) {
			altitude = 0;
			engine.plotEvent(new FlyingPersonHitsGroundEvent(this));
		}
		Y = horizonY + Math.pow(depth, 2) * (screenBottomY - horizonY) / 4 + altitude;
		
		vY += engine.GRAVITY * timeElapsed;
	}
	
	
	private final int horizonY;
	private final int screenBottomY;
	
	private final double depth;
	private final double depthMultiplier;
	private double X;	// BOTTOM-CENTER
	private double Y;
	private final double normalWidth;
	private final double normalHeight;
	private final double width;
	private final double height;
	
	private double altitude;
	private double vX;
	private double vY;

}
