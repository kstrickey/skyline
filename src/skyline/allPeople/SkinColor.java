package skyline.allPeople;

import java.awt.Color;
import java.util.Random;

public enum SkinColor {
	// Colors from http://www.paintandpowderstore.com/proddetail.php?prod=PPS-0047
		GRACEFUL_SWAN		(240, 205, 141),
		INGENUE				(239, 198, 132),
		VIXEN				(231, 184, 116),
		TEMPTRESS			(217, 170, 100),
		BUTTERMILK			(218, 171, 101),
		ENCHANTRESS			(203, 153, 84),
		FEMME_FATALE		(202, 158, 93),
		DESERT_SAND			(197, 146, 80),
		WINTER_WHEAT		(193, 143, 82),
		GOLDEN_SUNSET		(185, 132, 62),
		DEEP_XANTHE			(169, 112, 45),
		GINGER				(173, 113, 43),
		MIDNIGHT_MARIGOLD	(162, 108, 46),
		BURNT_AMBER			(159, 104, 47),
		SIENNA				(125, 81, 42),
		SUNLIT_LINEN		(240, 189, 108),
		CARAMEL				(207, 161, 73),
		PECAN				(156, 101, 44),
		BOMBSHELL			(234, 202, 153),
		PRIMA_DONNA			(228, 189, 134),
		LEADING_LADY		(219, 178, 122),
		SWEET_HEART			(211, 166, 107),
		GLAMOUR_GIRL		(203, 157, 95),
		CABARET_KITTEN		(203, 158, 99),
		BROADWAY_STAR		(203, 154, 98),
		SHOW_STOPPER		(201, 146, 90),
		SCREEN_GODDESS		(190, 138, 81),
		DIVA				(172, 121, 66),
		CHESTNUT			(163, 101, 52),
		WARM_UMBER			(142, 88, 50),
		HAZELNUT			(108, 77, 48),
		HIDDEN_MAGIC		(73, 51, 37),
		NIGHTFALL			(51, 28, 10),
		OLIVIA				(190, 139, 60),
		EVENING_MIST		(191, 147, 102),
		SMOKE				(154, 108, 72),
		PHANTOM				(120, 69, 38),
		SOFT_WISP			(144, 108, 84),
		BUFF				(238, 203, 161),
		SUNRISE_FLUSH		(237, 188, 129),
		AURORA				(228, 176, 116),
		CASHMERE_BELGE		(225, 166, 110),
		MORNING_GLOW		(220, 163, 108),
		AFTERGLOW			(215, 160, 106),
		BISQUE				(213, 147, 97),
		CEDER_SPICE			(203, 141, 84),
		SANDSTONE			(208, 139, 80),
		BUTTERSCOTCH		(212, 140, 81),
		CEYLON_CINNAMON		(205, 143, 94),
		AUBURN				(192, 129, 78),
		HENNA				(178, 116, 67),
		SABLE				(153, 87, 37),
		SHADOW_DANCE		(119, 63, 26),
		SILK_SPRITE			(239, 210, 168);
	
	private SkinColor(int R, int G, int B) {
		color = new Color(R, G, B);
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * Static method returns a random Color from the list of skin colors.
	 * @return A Color object representing that skin tone
	 */
	public static Color getRandomColor() {
		Random generator = new Random();
		SkinColor skin = SkinColor.values()[generator.nextInt(SkinColor.values().length)];
		return skin.getColor();
	}
	
	private Color color;
}
