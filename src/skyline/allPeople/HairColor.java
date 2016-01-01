package skyline.allPeople;

import java.awt.Color;
import java.util.Random;

public enum HairColor {
	// Colors from http://collectedwebs.com/art/colors/hair/
	BLACK				(9, 8, 6),
	OFF_BLACK			(44, 34, 43),
	DARK_GRAY			(113, 99, 90),
	MEDIUM_GRAY			(183, 166, 158),
	LIGHT_GRAY			(214, 196, 194),
	PLATINUM_BLONDE		(202, 191, 177),
	BLEACHED_BLONDE		(220, 208, 186),
	WHITE_BLONDE		(255, 245, 225),
	LIGHT_BLONDE		(230, 206, 168),
	ASH_BLONDE			(222, 188, 153),
	HONEY_BLONDE		(184, 151, 120),
	STRAWBERRY_BLONDE	(165, 107, 70),
	LIGHT_RED			(181, 82, 57),
	DARK_RED			(141, 74, 67),
	LIGHT_AUBURN		(145, 85, 61),
	DARK_AUBURN			(83, 61, 50),
	DARK_BROWN			(59, 48, 36),
	GOLDEN_BROWN		(85, 72, 56),
	MEDIUM_BROWN		(78, 67, 63),
	CHESTNUT_BROWN		(80, 68, 68),
	BROWN				(106, 78, 66),
	LIGHT_BROWN			(167, 133, 106),
	ASH_BROWN			(151, 121, 97);
	
	private HairColor(int R, int G, int B) {
		color = new Color(R, G, B);
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * Static method returns a random Color from the list of skin colors.
	 * @return A Color object representing that hair tone
	 */
	public static Color getRandomColor() {
		Random generator = new Random();
		HairColor hair = HairColor.values()[generator.nextInt(HairColor.values().length)];
		return hair.getColor();
	}
	
	private Color color;
}
