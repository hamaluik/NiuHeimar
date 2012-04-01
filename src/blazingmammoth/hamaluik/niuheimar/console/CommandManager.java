package blazingmammoth.hamaluik.niuheimar.console;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import blazingmammoth.hamaluik.niuheimar.log.GameLog;

public class CommandManager {
	private static HashMap<String, CommandInfo> commands = new HashMap<String, CommandInfo>();
	private static ArrayList<Scriptable> commandClasses = new ArrayList<Scriptable>();
	
	@SuppressWarnings("rawtypes")
	private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class> classes = new ArrayList<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}
	
    @SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
	
	public CommandManager() throws Exception {
		// load all classes in our classpath
		// and see if they are scriptable
		// if they are, create an instance of them here
		@SuppressWarnings("rawtypes")
		Class[] classes = getClasses("blazingmammoth.hamaluik.niuheimar");
		// check out our classes
		for(int i = 0; i < classes.length; i++) {
			// see if it's scriptable
			if(!classes[i].getSimpleName().equals("Scriptable") && Scriptable.class.isAssignableFrom(classes[i])) {
				// yup, we found a scriptable class!
				// instantiate it!
				commandClasses.add((Scriptable)classes[i].newInstance());
			}
		}
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
