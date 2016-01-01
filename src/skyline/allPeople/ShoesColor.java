package skyline.allPeople;

import java.awt.Color;
import java.util.Random;

public enum ShoesColor {
	BLACK			(0, 0, 0),
	DARK_BROWN		(30, 85, 55),
	BEAN_COLOR		(20, 75, 60),
	MILD			(15, 50, 85);
	
	private ShoesColor(int R, int G, int B) {
		color = new Color(R, G, B);
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * Static method returns a random Color from the list of shoes colors.
	 * @return A Color object representing that shoes tone
	 */
	public static Color getRandomColor() {
		Random generator = new Random();
		ShoesColor shoes = ShoesColor.values()[generator.nextInt(ShoesColor.values().length)];
		return shoes.getColor();
	}
	
	private Color color;
}
