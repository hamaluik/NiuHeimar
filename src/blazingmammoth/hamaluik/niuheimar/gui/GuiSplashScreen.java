package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.NiuHeimar;

public class GuiSplashScreen extends Gui {
	private Image logo;
	private int t;
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		logo = new Image("resources/gui/splash.png");
		t = 0;
	}

	@Override
	public void deinit() throws SlickException {
	}

	@Override
	public void update(GameContainer gc, int delta) {
		t += delta;
		
		if(t >= 1000) {
			NiuHeimar.setScreenProvider(new GuiMainMenu());
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		logo.draw((gc.getWidth() - logo.getWidth()) / 2, (gc.getHeight() - logo.getHeight()) / 2);
	}

}
