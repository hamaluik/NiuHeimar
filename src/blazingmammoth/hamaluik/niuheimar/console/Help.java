package blazingmammoth.hamaluik.niuheimar.console;

import java.util.HashMap;

public class Help extends Scriptable {
	public Help() {
		CommandManager.registerCommands(this.getClass());
	}
	
	@ScriptInfo(alias = "help", args = {"[function|page]"}, description = "shows help for a specific function or help page")
	public static boolean call(String[] args) {
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
	}
}
