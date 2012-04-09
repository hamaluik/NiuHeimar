package blazingmammoth.hamaluik.niuheimar.worldprovider;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.ScreenProvider;

public class WorldProvider implements ScreenProvider {
	protected TileManager tileManager;
	protected ArrayList<MapLayer> mapLayers;

	@Override
	public void init(GameContainer gc) throws SlickException {
		// initialize the tile manager first
		tileManager = new TileManager("resources/terrain.png", 32);
		
		// then the map itself
		mapLayers = new ArrayList<MapLayer>();
	}

	@Override
	public void deinit() throws SlickException {}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
