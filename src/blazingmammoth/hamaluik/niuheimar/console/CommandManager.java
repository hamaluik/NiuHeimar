package blazingmammoth.hamaluik.niuheimar.console;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import blazingmammoth.hamaluik.niuheimar.util.Utilities;

public class CommandManager {
	private static HashMap<String, CommandInfo> commands = new HashMap<String, CommandInfo>();
	
	public CommandManager() throws Exception {
		// load all classes in our classpath
		Class<?>[] classes = Utilities.getClasses("blazingmammoth.hamaluik.niuheimar");
		// check out our classes
		for(int i = 0; i < classes.length; i++) {
			// try to register any commands this class might have
			registerCommands(classes[i]);
		}
	}
	
	private static void registerCommands(Class<?> cls) {		
		// loop over all the methods in the class
		Method[] methods = cls.getMethods();
		for(int i = 0; i < methods.length; i++) {
			// get the method's annotations
			Annotation[] annotations = methods[i].getAnnotations();
			for(int j = 0; j < annotations.length; j++) {
				if(annotations[j] instanceof ScriptInfo) {
					// we found our annotation
					ScriptInfo si = (ScriptInfo)annotations[j];
					
					// create the command name
					String commandName = si.alias();
					// get the command args
					Class<?>[] params = methods[i].getParameterTypes();
					for(int k = 0; k < params.length; k++) {
						// append the arguments to it so that each one is unique
						commandName += ":" + params[k].getSimpleName();
					}
					
					// create the command info
					CommandInfo ci = new CommandInfo();
					ci.alias = si.alias();
					ci.argNames = si.args();
					ci.argDescriptions = si.argDescriptions();
					ci.description = si.description();
					if(si.longDescription().equals("")) {
						ci.longDescription = si.description();
					}
					else {
						ci.longDescription = si.longDescription();
					}
					ci.method = methods[i];
					
					// and add the command!
					commands.put(commandName, ci);
				}
			}
		}
	}
	
	public static boolean commandExists(String command) {
		for(String cmd: commands.keySet()) {
			// tokenize it
			String[] parts = cmd.split(":");
			
			// see if we have the command
			if(parts[0].equals(command)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean parseCommand(String line) throws Exception {
		// split the line by spaces
		String[] tokens = line.split(" ");
		
		// error!
		if(tokens.length < 1) {
			return false;
		}
		
		// get the command
		String command = tokens[0].toLowerCase();
		
		// make sure it exists
		if(!commandExists(command)) {
			// didn't exist, couldn't handle it
			return false;
		}
		
		// figure out how many arguments we have
		int argsLength = tokens.length - 1;
		if(argsLength < 0) {
			argsLength = 0;
		}
		
		// collect all functions that match our description
		ArrayList<String> possibleCommands = new ArrayList<String>();
		// loop through all commands
		for(String cmd: commands.keySet()) {
			// tokenize it
			String[] parts = cmd.split(":");
			
			// see if we have the command
			if(parts[0].equals(command)) {
				// ok, now see if we have the right number of args
				if(argsLength == commands.get(cmd).method.getParameterTypes().length) {
					// ok, same length
					// add it, we'll check parameter types in a sec
					possibleCommands.add(cmd);
				}
			}
		}
		
		// ok, now loop over all possible commands and see if we have one that matches
		// what we called
		for(int i = 0; i < possibleCommands.size(); i++) {
			Class[] params = commands.get(possibleCommands.get(i)).method.getParameterTypes();
			boolean possible = true;
			Object[] args = new Object[params.length];
			for(int j = 0; j < params.length && possible; j++) {
				// parse ints first
				if(params[j].equals(int.class)) {
					try {
						int pj = Integer.parseInt(tokens[j + 1]);
						args[j] = pj;
					}
					catch(Exception e) {
						// we didn't supply an int..
						possible = false;
					}
				}
				// parse floats next
				else if(params[j].equals(float.class)) {
					try {
						float pj = Float.parseFloat(tokens[j + 1]);
						args[j] = pj;
					}
					catch(Exception e) {
						// we didn't supply an int..
						possible = false;
					}
				}
				// parse strings last
				else if(params[j].equals(String.class)) {
					args[j] = tokens[j + 1];
				}
				// something not understood?
				else {
					possible = false;
				}
			}
			
			if(possible) {
				// we found a possible function!
				// call it!
				commands.get(possibleCommands.get(i)).method.invoke(null, args);
				
				// and get out of here!
				break;
			}
		}
		
		// true because we handled it
		return true;
	}
	
	public static HashMap<String, CommandInfo> getCommands() {
		return commands;
	}
}
