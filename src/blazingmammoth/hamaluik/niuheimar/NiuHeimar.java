package blazingmammoth.hamaluik.niuheimar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

import blazingmammoth.hamaluik.niuheimar.console.Console;
import blazingmammoth.hamaluik.niuheimar.console.ScriptInfo;
import blazingmammoth.hamaluik.niuheimar.gui.GuiSplashScreen;
import blazingmammoth.hamaluik.niuheimar.keybinds.KeyBind;
import blazingmammoth.hamaluik.niuheimar.keybinds.KeyBindManager;
import blazingmammoth.hamaluik.niuheimar.log.GameLog;
import blazingmammoth.hamaluik.niuheimar.util.FontRenderer;

public class NiuHeimar extends BasicGame {
	// our game container provided by slick
	private static GameContainer gameContainer;
	
	// this object has control of everything
	private static ScreenProvider screenProvider;
	
	// a console object
	private static Console console;
	
	// keep track of key bindings
	@SuppressWarnings("unused")
	private static KeyBindManager keyBindManger;
	
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
	}
	
	@ScriptInfo(
			alias = "fullscreen",
			args = {},
			description = "toggles fullscreen mode")
	@KeyBind(key = Input.KEY_F3)
	public static void toggleFullScreen() {
		try {
			gameContainer.setFullscreen(!gameContainer.isFullscreen());
		}
		catch (SlickException e) {
			GameLog.stackTrace(NiuHeimar.class, e);
		}
	}

	@ScriptInfo(
			alias = "screenshot",
			args = {},
			description = "takes a screenshot")
	@KeyBind(key = Input.KEY_F2)
	public static void screenShot() {
		screenShot("");
	}
	
	@ScriptInfo(
			alias = "screenshot",
			args = {"filename"},
			argDescriptions = {"the name of the file to store the screenshot in"},
			description = "takes a screenshot")
	public static void screenShot(String filename) {
		Image target;
		try {
			// get the current date if have a blank filename
			if(filename.equals("")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
				filename = df.format(new Date());
			}
			// make sure it's a png
			if(!filename.endsWith(".png")) {
				filename += ".png";
			}
			
			// make a folder for our screenshot
			File screenshotDir = new File("screenshots");
			screenshotDir.mkdirs();
			
			// create an image to hold our screenshot
			target = new Image(gameContainer.getWidth(), gameContainer.getHeight());
			
			// capture our current game container into that image
			gameContainer.getGraphics().copyArea(target, 0, 0);
			
			// write it out!
			ImageOut.write(target, "screenshots/" + filename);
		}
		catch (Exception e) {
			GameLog.stackTrace(NiuHeimar.class, e);
		}
	}

	
	@ScriptInfo(
			alias = "exit",
			args = {},
			description = "exits the game")
	@KeyBind(key = Input.KEY_F4, keyMods = {Input.KEY_LCONTROL})
	public static void exit() {
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
			
			// create out keybind manager
			keyBindManger = new KeyBindManager(gc);
			
			// initialize our console
			console = new Console(gc);
			gc.getInput().addKeyListener(console);
			
			// update our game container
			gameContainer = gc;
			
			// create the default screen provider (our splash screen)
			screenProvider = new GuiSplashScreen();

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
