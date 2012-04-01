package blazingmammoth.hamaluik.niuheimar.console;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import blazingmammoth.hamaluik.niuheimar.log.GameLog;

public class CommandManager {
	private static HashMap<String, CommandInfo> commands = new HashMap<String, CommandInfo>();
	private static ArrayList<Scriptable> commandClasses = new ArrayList<Scriptable>();
	
	public CommandManager() {
		// instantiate our command classes
		commandClasses.add(new Help());
	}
	
	@SuppressWarnings("rawtypes")
	public static void registerCommands(Class cls) {		
		// loop over all the methods in the class
		Method[] methods = cls.getMethods();
		for(int i = 0; i < methods.length; i++) {
			// get the method's annotations
			Annotation[] annotations = methods[i].getAnnotations();
			for(int j = 0; j < annotations.length; j++) {
				if(annotations[j] instanceof ScriptInfo) {
					// we found our annotation
					ScriptInfo si = (ScriptInfo)annotations[i];
					
					// add the command
					if(!commands.containsKey(si.alias())) {
						commands.put(si.alias(), new CommandInfo(si.alias(), si.args(), si.description(), si.longDescription(), methods[i]));
					}
				}
			}
		}
	}
	
	private boolean handleCommand(String command, String[] args) {		
		Method method = commands.get(command).method;
		Object[] objArgs = new Object[1];
		objArgs[0] = args;
		try {
			return (boolean)method.invoke(null, objArgs);
		}
		catch (Exception e) {
			GameLog.stackTrace("CommandManager", e);
		}
		
		return false;
	}
	
	public boolean parseCommand(String line) {
		// split the line by spaces
		String[] tokens = line.split(" ");
		
		// error!
		if(tokens.length < 1) {
			return false;
		}
		
		// get the command
		String command = tokens[0];
		
		// make sure it exists
		if(!commands.containsKey(command)) {
			// didn't exist, couldn't handle it
			return false;
		}
		
		// figure out how many arguments we have
		int argsLength = tokens.length - 1;
		if(argsLength < 0) {
			argsLength = 0;
		}
		
		// parse the args
		String[] args = new String[argsLength];
		for(int i = 0; i < argsLength; i++) {
			args[i] = tokens[i + 1];
		}
		
		// ok, everything seems to be in order
		// call it!
		if(!handleCommand(command, args)) {
			// uh-oh, something was wrong!
			// tell them how to call it..
			Console.addMessage("Invalid command: " + command);
			String usage = commands.get(command).alias + " ";
			for(int i = 0; i < commands.get(command).argTypes.length; i++) {
				usage += commands.get(command).argTypes[i] + " ";
			}
			Console.addMessage("  usage: " + usage);
		}
		
		// true because we handled it
		return true;
	}
	
	public static HashMap<String, CommandInfo> getCommands() {
		return commands;
	}
}
