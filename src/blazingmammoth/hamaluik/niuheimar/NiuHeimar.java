package blazingmammoth.hamaluik.niuheimar;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.console.Console;
import blazingmammoth.hamaluik.niuheimar.gui.GuiSplashScreen;
import blazingmammoth.hamaluik.niuheimar.log.GameLog;
import blazingmammoth.hamaluik.niuheimar.util.FontRenderer;

public class NiuHeimar extends BasicGame {
	// our game container provided by slick
	private static GameContainer gameContainer;
	
	// this object has control of everything
	private static ScreenProvider screenProvider;
	
	// a console object
	private static Console console;
	
	// our font
	FontRenderer fontRenderer;
	
	// internal states
	private static boolean consoleOpen = false;
	
	public static void main(String[] args) throws SlickException {
		// set the native library paths
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "natives"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		// ok, now create and run the game
		AppGameContainer app = new AppGameContainer(new ScalableGame(new NiuHeimar("Niu Heimar"), 640, 480, true));
		app.start();
	}

	public NiuHeimar(String title) throws SlickException {		
		// set our title stuff
		super(title);
		
		// create the default screen provider (our splash screen)
		screenProvider = new GuiSplashScreen();
	}
	
	public static void toggleFullScreen() {
		try {
			gameContainer.setFullscreen(!gameContainer.isFullscreen());
		}
		catch (SlickException e) {
			GameLog.stackTrace(NiuHeimar.class, e);
		}
	}
	
	public static void quit() {
		gameContainer.exit();
	}

	@Override
	public void init(GameContainer gc) {		
		try {
			// begin our log
			GameLog.info(this, "Game init");
			
			// we don't need to render at a billion FPS, keep it around 30
			gc.setTargetFrameRate(30);
			
			// turn off FPS being displayed
			gc.setShowFPS(false);
			
			// set our cursor
			gc.setMouseCursor("resources/gui/cursor.png", 0, 0);
			
			// initialize our font
			fontRenderer = new FontRenderer(gc);
			
			// initialize our console
			console = new Console(gc);
			gc.getInput().addKeyListener(console);
			
			// update our game container
			gameContainer = gc;

			// let the screen provider initialize itself
			screenProvider.init(gc);
		}
		catch (Exception e) {
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
