package blazingmammoth.hamaluik.niuheimar;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;

import blazingmammoth.hamaluik.niuheimar.console.Console;
import blazingmammoth.hamaluik.niuheimar.gui.GuiSplashScreen;
import blazingmammoth.hamaluik.niuheimar.log.GameLog;

public class NiuHeimar extends BasicGame {
	// our game container provided by slick
	private static GameContainer gameContainer;
	
	// this object has control of everything
	private static ScreenProvider screenProvider;
	
	// a console object
	private static Console console;
	
	// our font
	public static SpriteSheetFont font;
	
	// internal states
	private static boolean consoleOpen = false;
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new NiuHeimar("Niu Heimar"));
		app.setDisplayMode(640, 480, false);
		app.start();
	}

	public NiuHeimar(String title) throws SlickException {		
		// set our title stuff
		super(title);
		
		// create the default screen provider (our splash screen)
		screenProvider = new GuiSplashScreen();
	}

	@Override
	public void init(GameContainer gc) {
		// begin our log
		GameLog.info(this, "Game init");
		
		// we don't need to render at a billion FPS, keep it around 30
		gc.setTargetFrameRate(30);
		
		// turn off FPS
		gc.setShowFPS(false);
		
		// initiliaze our font
		try {
			font = new SpriteSheetFont(new SpriteSheet("resources/font/default.png", 8, 8), (char)0);
			gc.setDefaultFont(font);
		}catch (SlickException e) {
			GameLog.stackTrace(this.getClass(), e);
		}
		
		// initialize our console
		console = new Console(gc);
		gc.getInput().addKeyListener(console);
		
		// update our game container
		gameContainer = gc;
		
		// let the screen provider initialize itself
		try {
			screenProvider.init(gc);
		}
		catch (SlickException e) {
			GameLog.stackTrace(this.getClass(), e);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		try {
			// see if we need to show the console or not
			if(gc.getInput().isKeyPressed(Input.KEY_GRAVE)) {
				// toggle the console!
				consoleOpen = !consoleOpen;
				
				// manage the console
				if(consoleOpen) {
					console.show(gc);
				}
				else {
					console.hide(gc);
				}
			}
			
			if(consoleOpen) {
				console.update(gc, delta);
			}
			else {
				// only update our screen provider if the console is hidden
				screenProvider.update(gc, delta);
			}
		}
		catch(Exception e) {
			GameLog.stackTrace(this, e);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		try {
			// check to see if we have the console open
			if(consoleOpen) {
				// render the console!
				// (it will deal with the current screen provider)
				console.render(gc, g);
			}
			else {
				// ok, draw our screen provider like normal
				screenProvider.render(gc, g);
			}
		}
		catch(Exception e) {
			GameLog.stackTrace(this, e);
		}
	}
	
	public static ScreenProvider currentScreenProvider() {
		return screenProvider;
	}
	
	// allow sub-classes to change the current screen provider
	public static void setScreenProvider(ScreenProvider _screenProvider) {
		try {
			screenProvider.deinit();
			screenProvider = _screenProvider;
			screenProvider.init(gameContainer);
			console.updateBackgroundScreen(_screenProvider);
		}
		catch(SlickException e) {
			GameLog.stackTrace("NieHeimar", e);
		}
	}
}
