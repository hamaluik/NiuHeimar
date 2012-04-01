package blazingmammoth.hamaluik.niuheimar.console;

import java.util.HashMap;

public class Help extends Scriptable {
	public Help() {
		CommandManager.registerCommands(this.getClass());
	}
	
	/*@ScriptInfo(alias = "help", args = {"[function|page]"}, description = "shows help for a specific function or help page")
	public static boolean help(String[] args) {
		// do we have too many args?
		if(args.length > 1) {
			return false;
		}
		
		// first, parse the args
		String function = "";
		int page = 0;
		
		// do we have args?
		if(args.length > 0) {
			// yes, we do!
			// try to parse it as an integer first to see if it's a page
			try {
				page = Integer.parseInt(args[0]) - 1;
			}
			catch(Exception e) {
				// well, it wasn't a page number for sure
				// assume it's a function
				function = args[0];
			}
		}
		
		// ok, now get all the registered commands
		HashMap<String, CommandInfo> commands = CommandManager.getCommands();
		
		// calculate how many pages this is
		int numPages = 0;
		int start = page * 8;
		int end = start + 8;
		if(function.equals("")) {
			numPages = commands.size() / 8;
			if(commands.size() % 8 != 0) numPages++;
			if(page < 0) page = 0;
			if(page >= numPages) page = numPages - 1;
			
			// calculate start and end indices
			if(end > commands.size()) {
				end = commands.size();
			}
		}
		
		// print a header
		if(function.equals("")) {
			println("=== Help (page " + (page + 1) + "/" + numPages + ") ===");
		}
		else {
			println("=== Help - " + function + " ===");
		}
		
		// if we think it's a function, look it up
		if(!function.equals("") && !commands.containsKey(function)) {
			// hmm, it didn't exist!
			// alert them!
			println("Error: '" + function + "' is not a recognized command!");
			return false;
		}
		else if(commands.containsKey(function)) {
			// ok, the function exists, look it up
			println("Command:     " + commands.get(function).alias);
			println("Arguments:   ");
			for(int i = 0; i < commands.get(function).argTypes.length; i++) {
				println("             " + commands.get(function).argTypes[i]);
			}
			println("Description: " + commands.get(function).longDescription);
			
			// ok, we're done!
			return true;
		}
		
		// ok, display help
		for(String command: commands.keySet()) {
			String usage = commands.get(command).alias + " ";
			for(int i = 0; i < commands.get(command).argTypes.length; i++) {
				usage += commands.get(command).argTypes[i] + " ";
			}
			usage += commands.get(command).description;
			println(usage);
		}
		
		return true;
	}*/
	
	@ScriptInfo(alias = "help", args = {}, description = "shows a help menu with available commands")
	public static boolean help() {
		return help(1);
	}
	
	@ScriptInfo(alias = "help", args = {"page"}, argDescriptions = {"specify a specific help page"}, description = "shows a help menu with available commands")
	public static boolean help(int page) {
		// adjust the page
		page--;
		
		// get all the registered commands
		HashMap<String, CommandInfo> commands = CommandManager.getCommands();
		
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
		for(String command: commands.keySet()) {
			String usage = "&c" + commands.get(command).alias + " ";
			for(int i = 0; i < commands.get(command).argNames.length; i++) {
				usage += "&7<&6" + commands.get(command).argNames[i] + "&7> ";
			}
			usage += "&e" + commands.get(command).description;
			println(usage);
		}
		
		return true;
	}

	@ScriptInfo(alias = "help", args = {"command"}, argDescriptions = {"which command you want help on"}, description = "shows help for a specific command")
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
