package blazingmammoth.hamaluik.niuheimar.worldprovider;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileManager {
	private Image tileMap;
	private int tileSize;
	private Image[] tiles;
	
	public TileManager(String tileset, int _tileSize) throws SlickException {
		tileSize = _tileSize;
		// load the tiles
		tileMap = new Image(tileset);
		
		// calculate the number of tiles in this image
		int numWide = tileMap.getWidth() / tileSize;
		int numTall = tileMap.getHeight() / tileSize;
		
		// initialize the tiles container
		tiles = new Image[numWide * numTall];
		
		// and fill it in
		for(int y = 0; y < numTall; y++) {
			for(int x = 0; x < numWide; x++) {
				tiles[y * numWide + x] = tileMap.getSubImage(x * tileSize, y * tileSize, tileSize, tileSize);
			}
		}
	}
	
	public Image getTile(int id) {
		return tiles[id];
	}
}
