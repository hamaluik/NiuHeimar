package blazingmammoth.hamaluik.niuheimar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface ScreenProvider {
	public void init(GameContainer gc) throws SlickException;
	public void deinit() throws SlickException;
	public void update(GameContainer gc, int delta) throws SlickException;
	public void render(GameContainer gc, Graphics g) throws SlickException;
}
