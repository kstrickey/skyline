package skyline.allPeople;

import java.awt.Color;
import java.util.Random;

import skyline.Drawable;
import skyline.Tickable;

public abstract class Person implements Tickable, Drawable {
	
	public final Random generator = new Random();
	
	public Person() {
		female = generator.nextBoolean();
		
		skinColor = SkinColor.getRandomColor();
		hairColor = HairColor.getRandomColor();
		
		shirtColor = ShirtColor.getRandomColor();
		pantsColor = PantsColor.getRandomColor();
		shoesColor = ShoesColor.getRandomColor();
	}
	
	
	public boolean isFemale() {
		return female;
	}
	
	public Color getSkinColor() {
		return skinColor;
	}
	
	public Color getHairColor() {
		return hairColor;
	}
	
	public Color getShirtColor() {
		return shirtColor;
	}
	
	public Color getPantsColor() {
		return pantsColor;
	}
	
	public Color getShoesColor() {
		return shoesColor;
	}
	
	public void copyDataFrom(Person other) {
		female = other.isFemale();
		skinColor = other.getSkinColor();
		hairColor = other.getHairColor();
		shirtColor = other.getShirtColor();
		pantsColor = other.getPantsColor();
		shoesColor = other.getShoesColor();
	}
	
	private boolean female;
	
	private Color skinColor;
	private Color hairColor;
	
	private Color shirtColor;
	private Color pantsColor;
	private Color shoesColor;
	
	
	
}
