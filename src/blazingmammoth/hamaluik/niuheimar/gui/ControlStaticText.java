package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.util.FontRenderer;

public class ControlStaticText extends ControlComponent {
	private String text;
	
	public ControlStaticText(float _x, float _y, String _text) {
		x = _x;
		y = _y;
		w = FontRenderer.getWidth(_text);
		h = FontRenderer.getLineHeight();
		text = _text;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		FontRenderer.drawStringWithShadow(x, y, text);
	}
}
