package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.NiuHeimar;
import blazingmammoth.hamaluik.niuheimar.log.GameLog;

public class GuiMainMenu extends GuiScreen {

	@Override
	public void init(GameContainer gc) throws SlickException {
		super.init(gc);
		addControl(new ControlStaticText(0, 0, "Main Menu"));
		addControl(new ControlSpacer(0, 0, 1, 8));
		addControl(new ControlButton(0, 0, 0, "New Game", this));
		addControl(new ControlButton(1, 0, 0, "Load Game", this));
		addControl(new ControlButton(2, 0, 0, "Options", this));
		addControl(new ControlButton(3, 0, 0, "Quit", this));
		centerControls(gc, 4);
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
			NiuHeimar.setScreenProvider(new GuiOptions());
		}
		else if(button.getId() == 3) {
			NiuHeimar.exit();
		}
	}

}
