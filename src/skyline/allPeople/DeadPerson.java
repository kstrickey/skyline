package skyline.allPeople;

import skyline.Engine;

public abstract class DeadPerson extends Person {
	
	public DeadPerson(Engine engine, int X, double depth) {

		int horizonY = (int) (engine.HEIGHT * 2.0 / 3);
		int screenBottomY = engine.HEIGHT;
		int normalWidth = (int) (engine.WIDTH / 100.0);
		int normalHeight = (int) (engine.HEIGHT * 2.0 / 75.0);
		
		this.X = X;
		
		double depthMultiplier = Math.pow(2, depth);
		Y = (int) (horizonY + Math.pow(depth, 2) * (screenBottomY - horizonY) / 4);
		
		width = (int) (normalWidth * depthMultiplier);
		height = (int) (normalHeight * depthMultiplier);
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
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		
	}
	
	private int X;
	private int Y;
	private int height;
	private int width;

}
