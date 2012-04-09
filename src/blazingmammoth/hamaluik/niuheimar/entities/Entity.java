package blazingmammoth.hamaluik.niuheimar.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.worldprovider.Camera;

public class Entity {
	protected int posX;
	protected int posY;
	protected int width;
	protected int height;
	protected boolean visible;
	
	protected Image image;
	
	public Entity(Image _image, int x, int y, int _width, int _height) {
		image = _image;
		posX = x;
		posY = y;
		setWidth(_width);
		setHeight(_height);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
	}
	
	public void render(Camera camera) throws SlickException {
		if(this.image != null && this.visible) {
			camera.draw(this);
		}
	}
	
	public Image getImage() {
		return image;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
