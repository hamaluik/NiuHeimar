package blazingmammoth.hamaluik.niuheimar.entities;

import org.newdawn.slick.Image;

public class EntityTile extends Entity {
	private boolean passThrough;

	public EntityTile(Image _image, int x, int y, int tileSize, boolean _passThrough) {
		super(_image, x, y, tileSize, tileSize);
		passThrough = _passThrough;
	}

	public boolean isPassThrough() {
		return passThrough;
	}

	public void setPassThrough(boolean passThrough) {
		this.passThrough = passThrough;
	}
}
