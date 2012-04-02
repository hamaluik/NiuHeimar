package blazingmammoth.hamaluik.niuheimar.gui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class GuiScreen extends Gui {
	private ArrayList<ControlComponent> controlList = new ArrayList<ControlComponent>();

	@Override
	public void init(GameContainer gc) throws SlickException {
	}

	@Override
	public void deinit() throws SlickException {
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// update all the components
		for(int i = 0; i < controlList.size(); i++) {
			controlList.get(i).update(gc, delta);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// render all the components
		for(int i = 0; i < controlList.size(); i++) {
			controlList.get(i).render(gc, g);
		}
	}
	
	protected void addControl(ControlComponent component) {
		controlList.add(component);
	}
	
	protected void centerControls(GameContainer gc, int spacing) {
		horizontallyCenterControls(gc);
		verticallyCenterControls(gc, spacing);
	}
	
	protected void horizontallyCenterControls(GameContainer gc) {
		for(int i = 0; i < controlList.size(); i++) {
			controlList.get(i).x = (gc.getWidth() - controlList.get(i).w) / 2;
		}
	}
	
	protected void verticallyCenterControls(GameContainer gc, int spacing) {
		int height = 0;
		// add the height of the controls
		for(int i = 0; i < controlList.size(); i++) {
			height += controlList.get(i).h;
		}
		// add the spacing
		height += spacing * controlList.size() - 1;
		
		// get the leftover space
		int leftOver = gc.getHeight() - height;
		
		// start the controls at half the leftover space
		int y = leftOver / 2;
		// move all the controls around
		for(int i = 0; i < controlList.size(); i++) {
			controlList.get(i).y = y;
			y += controlList.get(i).h + spacing;
		}
	}
	
	protected void onButtonClicked(ControlButton button) {
		
	}
}
