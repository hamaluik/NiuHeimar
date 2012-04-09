package blazingmammoth.hamaluik.niuheimar.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class DebugOverlay {
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// render the background of the console overtop of it
		// set the colour first
		g.setColor(new Color(0, 0, 0, 85));
		// now draw it
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		
		// draw the FPS
		gc.getDefaultFont().drawString(2, 2, "FPS: " + gc.getFPS());
	}
}
