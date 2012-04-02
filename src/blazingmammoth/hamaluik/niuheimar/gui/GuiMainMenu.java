package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.NiuHeimar;
import blazingmammoth.hamaluik.niuheimar.log.GameLog;

public class GuiMainMenu extends GuiScreen {
	private Image bg;

	@Override
	public void init(GameContainer gc) throws SlickException {
		bg = new Image("resources/gui/menubg.png");
		addControl(new ControlStaticText(0, 0, "Main Menu"));
		addControl(new ControlSpacer(0, 0, 1, 8));
		addControl(new ControlButton(0, 0, 0, "New Game", this));
		addControl(new ControlButton(1, 0, 0, "Load Game", this));
		addControl(new ControlButton(2, 0, 0, "Options", this));
		addControl(new ControlButton(3, 0, 0, "Quit", this));
		centerControls(gc, 4);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// render the background
		for(int x = 0; x < gc.getWidth(); x += bg.getWidth()) {
			for(int y = 0; y < gc.getHeight(); y += bg.getHeight()) {
				bg.draw(x, y);
			}
		}
		
		// now let the super class render all our components
		super.render(gc, g);
	}

	@Override
	protected void onButtonClicked(ControlButton button) {
		if(button.getId() == 0) {
			GameLog.info(this, "New game!");
		}
		else if(button.getId() == 1) {
			GameLog.info(this, "Load game!");
		}
		else if(button.getId() == 2) {
			GameLog.info(this, "Options");
		}
		else if(button.getId() == 3) {
			NiuHeimar.quit();
		}
	}

}
