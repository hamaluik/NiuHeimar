package blazingmammoth.hamaluik.niuheimar.worldprovider;

import org.newdawn.slick.GameContainer;

import blazingmammoth.hamaluik.niuheimar.entities.Entity;

public class Camera {
	private int posX;
	private int posY;
	private int width;
	private int height;
	
	public Camera(int x, int y, GameContainer gc) {
		posX = x;
		posY = y;
		width = gc.getWidth();
		height = gc.getHeight();
	}
	
	public void setFocus(int x, int y) {
		posX = x - (width / 2);
		posY = y - (height / 2);
	}
	
	public boolean inFocus(Entity entity) {
		return (posX < (entity.getPosX() + entity.getWidth()) &&
				(posX + width) > entity.getPosX() &&
				posY < (entity.getPosY() + entity.getHeight()) &&
				(posY + height) > entity.getPosY());
	}
	
	public void draw(Entity entity) {
		// make sure they're in focus
		if(!inFocus(entity)) {
			return;
		}
		
		// draw it!
		entity.getImage().draw(entity.getPosX() - posX, entity.getPosY() - posY);
	}
}
