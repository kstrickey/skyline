package skyline.allPeople;

import java.awt.Color;
import java.util.Random;

public enum ShirtColor {
	// Colors from http://www.creativecolorschemes.com/resources/free-color-schemes/basic-color-scheme.shtml (basic color scheme)
	MAROON			(178, 31, 53),
	RED				(216, 39, 53),
	RED_ORANGE		(255, 116, 53),
	ORANGE			(255, 161, 53),
	YELLOW_ORANGE	(255, 203, 53),
	YELLOW			(255, 249, 53),
	DARK_GREEN		(0, 117, 58),
	GREEN			(0, 158, 71),
	LIGHT_GREEN		(22, 221, 53),
	DARK_BLUE		(0, 82, 165),
	BLUE			(0, 121, 231),
	LIGHT_BLUE		(0, 169, 252),
	DARK_PURPLE		(104, 30, 126),
	PURPLE			(125, 60, 181),
	LIGHT_PURPLE	(189, 122, 246);
	
	private ShirtColor(int R, int G, int B) {
		color = new Color(R, G, B);
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * Static method returns a random Color from the list of shirt colors.
	 * @return A Color object representing that shirt tone
	 */
	public static Color getRandomColor() {
		Random generator = new Random();
		ShirtColor shirt = ShirtColor.values()[generator.nextInt(ShirtColor.values().length)];
		return shirt.getColor();
	}
	
	private Color color;
}
