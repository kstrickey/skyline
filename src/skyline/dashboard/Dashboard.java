package skyline.dashboard;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import skyline.Drawable;
import skyline.Engine;
import skyline.Tickable;

public class Dashboard implements Drawable, Tickable {

	public Dashboard(Engine engine) {
		X = engine.WIDTH - 300;
		Y = 10;
		width = 290;
		height = 50;
		
		calendar = engine.getCalendar();
	}
	
	@Override
	public void tick(double timeElapsed, Engine engine) {
		countKills = engine.getCountKills();
		countEscapers = engine.getCountEscapers();
		timeSurvived = (int) engine.getTimeSinceAliensArrived();
	}

	@Override
	public void draw(Graphics page) {										// Please re-do draw function for things
//		page.setColor(Color.white);
//		page.fillRoundRect(X, Y, width, height, 50, 50);
//		
//		page.setColor(Color.blue);
//		page.drawString("Date: " + calendar.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, Locale.getDefault()) + 
//						" " + calendar.get(GregorianCalendar.DAY_OF_MONTH) + 
//						", " + calendar.get(GregorianCalendar.YEAR) + 
//						"     " + fmt.format(calendar.get(GregorianCalendar.HOUR_OF_DAY)) + 
//						":" + fmt.format(calendar.get(GregorianCalendar.MINUTE)) + 
//						":" + fmt.format(calendar.get(GregorianCalendar.SECOND)), X + 20, Y + 20);
//		page.drawString("Kills: " + countKills + "       Escapers: " + countEscapers + "       Time Survived: " + timeSurvived, X + 20, Y + 40);
	}
	
	private int X;
	private int Y;
	private int width;		// size is not relative :(
	private int height;
	
	private int countKills;
	private int countEscapers;
	private int timeSurvived;
	
	private GregorianCalendar calendar;
	private final DecimalFormat fmt = new DecimalFormat("00");

}
