package blazingmammoth.hamaluik.niuheimar.console.commands;

import blazingmammoth.hamaluik.niuheimar.NiuHeimar;
import blazingmammoth.hamaluik.niuheimar.console.ScriptInfo;
import blazingmammoth.hamaluik.niuheimar.console.Scriptable;

public class WindowStates extends Scriptable {
	@ScriptInfo(
			alias = "fullscreen",
			args = {},
			description = "toggles fullscreen mode")
	public static boolean fullscreen() {
		NiuHeimar.toggleFullScreen();
		return true;
	}
	
	@ScriptInfo(
			alias = "exit",
			args = {},
			description = "exits the game")
	public static boolean exit() {
		NiuHeimar.quit();
		return true;
	}
	
	@ScriptInfo(
			alias = "quit",
			args = {},
			description = "exits the game")
	public static boolean quit() {
		NiuHeimar.quit();
		return true;
	}
}
