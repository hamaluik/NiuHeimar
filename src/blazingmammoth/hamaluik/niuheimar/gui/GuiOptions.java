package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.NiuHeimar;

public class GuiOptions extends GuiScreen {

	@Override
	public void init(GameContainer gc) throws SlickException {
		super.init(gc);
		addControl(new ControlStaticText(0, 0, "Options"));
		addControl(new ControlSpacer(0, 0, 1, 8));
		addControl(new ControlButton(0, 0, 0, "Back", this));
		centerControls(gc, 4);
	}

	@Override
	protected void onButtonClicked(ControlButton button) {
		if(button.getId() == 0) {
			// go back
			NiuHeimar.setScreenProvider(new GuiMainMenu());
		}
	}
}
