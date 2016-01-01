package skyline.allPeople;

import java.awt.Color;
import java.util.Random;

public enum PantsColor {
	BLACK			(0, 0, 0),
	DARK_GRAY		(53, 53, 53),
	GRAY			(117, 117, 117),
	KHAKI			(196, 166, 135),
	BLUE			(0, 0, 142),
	PURPLISH_BLUE	(7, 47, 122),
	SKY_BLUE		(97, 119, 171),
	CYAN			(0, 148, 189),
	OLIVE_GREEN		(160, 155, 89);
	
	private PantsColor(int R, int G, int B) {
		color = new Color(R, G, B);
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * Static method returns a random Color from the list of pants colors.
	 * @return A Color object representing that pants tone
	 */
	public static Color getRandomColor() {
		Random generator = new Random();
		PantsColor pants = PantsColor.values()[generator.nextInt(PantsColor.values().length)];
		return pants.getColor();
	}
	
	private Color color;
}
