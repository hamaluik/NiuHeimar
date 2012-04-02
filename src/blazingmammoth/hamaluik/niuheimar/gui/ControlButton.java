package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.util.FontRenderer;

public class ControlButton extends ControlComponent {
	private Image image;
	private int id;
	private String text;
	private boolean hoverState = false;
	private GuiScreen parent;
	
	public ControlButton(int _id, float _x, float _y, String _text, GuiScreen _parent) throws SlickException {
		this(_id, _x, _y, 120, 20, _text, _parent);
	}
		
	public ControlButton(int _id, float _x, float _y, float _w, float _h, String _text, GuiScreen _parent) throws SlickException {
		id = _id;
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		text = _text;
		image = new Image("resources/gui/gui_components.png");
		parent = _parent;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
	}

	@Override
	public void deinit() throws SlickException {
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// see if we're hovering
		int mx = gc.getInput().getMouseX();
		int my = gc.getInput().getMouseY();
		hoverState = (mx >= x && mx <= (x + w) && my >= y && my <= (y + h));
		
		// check to see if we were clicked
		if(hoverState && gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			// yup!
			parent.onButtonClicked(this);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// draw the button
		g.drawImage(image, x, y, x + (w / 2), y + h, 0, hoverState ? 20 : 0, w / 2, 20 + (hoverState ? 20 : 0), Color.white);
		g.drawImage(image, x + (w / 2), y, x + w, y + h, 200 - (w / 2), hoverState ? 20 : 0, 200, 20 + (hoverState ? 20 : 0), Color.white);
		
		// draw the text on the button
		FontRenderer.drawString(x + ((w - FontRenderer.getWidth(text)) / 2), y + ((h - FontRenderer.getLineHeight()) / 2), (hoverState ? "&e" : "") + text);
	}
	
	public int getId() {
		return id;
	}
}
