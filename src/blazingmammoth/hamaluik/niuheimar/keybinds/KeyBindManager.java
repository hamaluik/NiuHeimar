package blazingmammoth.hamaluik.niuheimar.keybinds;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import blazingmammoth.hamaluik.niuheimar.NiuHeimar;
import blazingmammoth.hamaluik.niuheimar.log.GameLog;
import blazingmammoth.hamaluik.niuheimar.util.Utilities;

public class KeyBindManager implements KeyListener {
	// store our game container
	GameContainer gameContainer;
	// keep track of our key binds
	private static ArrayList<KeyBinding> keyBindings = new ArrayList<KeyBinding>();
	
	public KeyBindManager(GameContainer gc) throws Exception {
		// load all classes in our classpath
		@SuppressWarnings("rawtypes")
		Class[] classes = Utilities.getClasses("blazingmammoth.hamaluik.niuheimar");
		// check out our classes
		for(int i = 0; i < classes.length; i++) {
			// try to register any keys that this class might have
			registerKeyBinds(classes[i]);
		}
		
		// register ourselves as a keylistener
		gc.getInput().addKeyListener(this);
		
		// and store our game container
		gameContainer = gc;
	}
	
	private static void registerKeyBinds(Class<?> cls) {		
		// loop over all the methods in the class
		Method[] methods = cls.getMethods();
		for(int i = 0; i < methods.length; i++) {
			// get the method's annotations
			Annotation[] annotations = methods[i].getAnnotations();
			for(int j = 0; j < annotations.length; j++) {
				if(annotations[j] instanceof KeyBind) {
					// we found our annotation
					KeyBind kb = (KeyBind)annotations[j];
					
					// create the keybind object
					KeyBinding keyBinding = new KeyBinding();
					keyBinding.key = kb.key();
					keyBinding.keyMods = kb.keyMods();
					keyBinding.scope = kb.scope();
					keyBinding.method = methods[i];
					
					// and add the keybind!
					keyBindings.add(keyBinding);
				}
			}
		}
	}

	@Override
	public void inputEnded() { }

	@Override
	public void inputStarted() { }

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input input) { }

	@Override
	public void keyPressed(int key, char c) {
		// see if we have this key in our list
		Iterator<KeyBinding> it = keyBindings.iterator();
		while(it.hasNext()) {
			KeyBinding kb = it.next();
			if(kb.key == key) {
				// we found the key!
				// check to see if we have the correct mods
				boolean modsOk = true;
				for(int i = 0; i < kb.keyMods.length; i++) {
					if(!gameContainer.getInput().isKeyDown(kb.keyMods[i])) {
						modsOk = false;
						break;
					}
				}
				
				// check to see if we failed the mods check
				if(!modsOk) {
					continue;
				}
				
				// check to see if we're in the correct scope
				boolean scopeOk = false;
				if(kb.scope == NiuHeimar.class) {
					// global scope, go for it
					scopeOk = true;
				}
				else if(kb.scope == NiuHeimar.currentScreenProvider().getClass()) {
					// local scope, but yes we're in the correct class
					scopeOk = true;
				}
				
				// if we're in the correct scope,
				// and we have the right key
				// go for it!
				if(scopeOk) {
					try {
						kb.method.invoke(null, (Object[])null);
					}
					catch (Exception e) {
						GameLog.stackTrace(this, e);
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(int key, char c) { }
}
