package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.util.FontRenderer;

public class GuiMainMenu extends Gui {
	private Image bg;

	@Override
	public void init(GameContainer gc) throws SlickException {
		bg = new Image("resources/gui/menubg.png");
	}

	@Override
	public void deinit() throws SlickException {
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// render the background
		for(int x = 0; x < gc.getWidth(); x += bg.getWidth()) {
			for(int y = 0; y < gc.getHeight(); y += bg.getHeight()) {
				bg.draw(x, y);
			}
		}
		
		// render the menu name
		String mainMenuText = "Main Menu";
		FontRenderer.drawString((gc.getWidth() - FontRenderer.getWidth(mainMenuText)) / 2, 5, mainMenuText);
	}

}
