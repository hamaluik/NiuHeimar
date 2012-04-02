package blazingmammoth.hamaluik.niuheimar.console.commands;

import java.util.HashMap;
import java.util.TreeSet;

import blazingmammoth.hamaluik.niuheimar.console.CommandInfo;
import blazingmammoth.hamaluik.niuheimar.console.CommandManager;
import blazingmammoth.hamaluik.niuheimar.console.ScriptInfo;
import blazingmammoth.hamaluik.niuheimar.console.Scriptable;

public class Help extends Scriptable {	
	@ScriptInfo(
			alias = "help",
			args = {},
			description = "shows a help menu with available commands")
	public static boolean help() {
		return help(1);
	}
	
	@ScriptInfo(
			alias = "help",
			args = {"page"},
			argDescriptions = {"specify a specific help page"},
			description = "shows a help menu with available commands")
	public static boolean help(int page) {
		// adjust the page
		page--;
		
		// get all the registered commands
		HashMap<String, CommandInfo> commands = CommandManager.getCommands();
		TreeSet<String> cmdKeys = new TreeSet<String>(commands.keySet());
		
		// calculate how many pages this is
		int numPages = 0;
		int start = page * 8;
		int end = start + 8;
		numPages = commands.size() / 8;
		if(commands.size() % 8 != 0) numPages++;
		
		// adjust the page
		if(page < 0) page = 0;
		if(page >= numPages) page = numPages - 1;
		
		// calculate start and end indices
		if(end > commands.size()) {
			end = commands.size();
		}
		
		// print a header
		println("&f=== Help (page " + (page + 1) + "/" + numPages + ") ===");
		
		// ok, display help
		for(String command: cmdKeys) {
			String usage = "&c" + commands.get(command).alias + " ";
			for(int i = 0; i < commands.get(command).argNames.length; i++) {
				usage += "&7<&6" + commands.get(command).argNames[i] + "&7> ";
			}
			usage += "&e" + commands.get(command).description;
			println(usage);
		}
		
		return true;
	}

	@ScriptInfo(
			alias = "help",
			args = {"command"},
			argDescriptions = {"which command you want help on"},
			description = "shows help for a specific command")
	public static boolean help(String command) {
		// ok, now get all the registered commands
		HashMap<String, CommandInfo> commands = CommandManager.getCommands();
		
		// bring the command to lower case
		command = command.toLowerCase();
		
		// print a header
		println("&f=== Help - " + command + " ===");
		
		if(!CommandManager.commandExists(command)) {
			// hmm, it didn't exist!
			// alert them!
			println("Error: '" + command + "' is not a recognized command!");
			return false;
		}
		
		// ok, the function exists, look it up
		// note: there could be multiple commands
		for(String cmd: commands.keySet()) {
			// tokenize it to get the command
			String[] tokens = cmd.split(":");
			if(tokens[0].equals(command)) {
				// ok, we have a command
				CommandInfo ci = commands.get(cmd);
				
				// print the info
				println("&7Command:     &c" + ci.alias);
				if(ci.argNames.length > 0) {
					println("&7Arguments:   &7<&6" + ci.argNames[0] + "&7> &e(" + ci.argDescriptions[0] + ")");
					for(int i = 1; i < ci.argNames.length; i++) {
						println("             &7<&6" + ci.argNames[i] + "&7> &e(" + ci.argDescriptions[i] + ")");
					}
				}
				else {
					println("&7Arguments:");
				}
				println("&7Description: &b" + ci.longDescription);
				println("");
			}
		}
		
		// ok, we're done!
		return true;
	}
}
