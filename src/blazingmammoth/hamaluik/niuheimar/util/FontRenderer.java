package blazingmammoth.hamaluik.niuheimar.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;

public class FontRenderer {
	private static GameContainer gc;
	private static SpriteSheetFont font;
	
	public FontRenderer(GameContainer _gc) throws SlickException {
		gc = _gc;
		font = new SpriteSheetFont(new SpriteSheet("resources/font/default.png", 8, 8), (char)0);
		gc.setDefaultFont(font);
	}
	
	private static boolean inColourRange(char c) {
		return ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'));
	}
	
	private static Color mapColour(char c) {
		if(c == '0') return new Color(0, 0, 0);
		else if(c == '1') return new Color(0, 0, 170);
		else if(c == '2') return new Color(0, 170, 0);
		else if(c == '3') return new Color(0, 170, 170);
		else if(c == '4') return new Color(0, 170, 0);
		else if(c == '5') return new Color(170, 0, 170);
		else if(c == '6') return new Color(255, 170, 0);
		else if(c == '7') return new Color(170, 170, 170);
		else if(c == '8') return new Color(85, 85, 85);
		else if(c == '9') return new Color(85, 85, 255);
		else if(c == 'a') return new Color(85, 255, 85);
		else if(c == 'b') return new Color(85, 255, 255);
		else if(c == 'c') return new Color(255, 85, 85);
		else if(c == 'd') return new Color(255, 85, 255);
		else if(c == 'e') return new Color(255, 255, 85);
		else if(c == 'f') return new Color(255, 255, 255);
		return Color.white;
	}
	
	public static void drawString(float x, float y, String text) {
		// assume fixed-width fonts for now
		int width = font.getWidth(" ");
		Color currentColour = Color.white;
		
		for(int i = 0; i < text.length(); i++) {
			// check for special characters
			if(text.charAt(i) == '&' && i < text.length() - 1 && inColourRange(text.charAt(i + 1))) {
				// change colour!
				currentColour = mapColour(text.charAt(i + 1));
				// skip the colour code
				i++;
			}
			else {
				// regular text
				font.drawString(x, y, text.substring(i, i + 1), currentColour);
				x += width;
			}
		}
	}
	
	public static int getWidth(String text) {
		// first, remove all "&[0-9a-f]"
		text = text.replaceAll("&[0-9a-f]", "");
		return font.getWidth(text);
	}
	
	public static int getLineHeight() {
		return font.getLineHeight();
	}
}
