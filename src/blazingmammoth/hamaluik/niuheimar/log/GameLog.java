package blazingmammoth.hamaluik.niuheimar.log;

//import java.io.File;
import java.util.logging.ConsoleHandler;
//import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("rawtypes")
public class GameLog {
	private static boolean initialized = false;
	public static Logger log;
	 
	private GameLog() {
		init();
	}
	
	private static void init() {
		// if we've already initialized, gtfo
		if(initialized) {
			return;
		}
		
		// create our logger
		log = Logger.getLogger("NiuHeimar");
		
		// set the level
		// TODO: change for release
		log.setLevel(Level.CONFIG);
		
		try {
			/*// access the log file
			File logFile = new File("log.txt");
			FileHandler handler = new FileHandler(logFile.getAbsolutePath(), true);
			// set the formatter and handler
			/*GameLogFormatter formatter = new GameLogFormatter();
			handler.setFormatter(formatter);
			log.addHandler(handler);*/
			ConsoleHandler handler = new ConsoleHandler();
			handler.setFormatter(new GameLogFormatter());
			log.addHandler(handler);
			
			// yes, we initialized!
			initialized = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void error(String obj, String error) {
		init();
		log.severe("[" + obj + "] ERROR: " + error);
	}
	
	public static void info(String obj, String info) {
		init();
		log.info("[" + obj + "] info: " + info);
	}
	
	public static void config(String obj, String config) {
		init();
		log.config("[" + obj + "] config: " + config);
	}
	
	public static void debug(String obj, String debug) {
		init();
		log.fine("[" + obj + "] debug: " + debug);
	}
		
	public static void error(Class obj, String error) {
		error(obj.getName(), error);
	}
	
	public static void info(Class obj, String info) {
		info(obj.getName(), info);
	}
	
	public static void config(Class obj, String config) {
		config(obj.getName(), config);
	}
	
	public static void debug(Class obj, String debug) {
		debug(obj.getName(), debug);
	}
	
	public static void error(Object obj, String error) {
		error(obj.getClass(), error);
	}
	
	public static void info(Object obj, String info) {
		info(obj.getClass(), info);
	}
	
	public static void config(Object obj, String config) {
		config(obj.getClass(), config);
	}
	
	public static void debug(Object obj, String debug) {
		debug(obj.getClass(), debug);
	}
	
	public static void stackTrace(Class obj, Exception exception) {
		StackTraceElement[] elements = exception.getStackTrace();
		String result = exception.getClass().getName() + ": " + exception.getMessage();
		result += System.getProperty("line.separator");
		for(int i = 0; i < elements.length; i++) {
			result += "\tat line " + elements[i].getLineNumber() + " in: " + elements[i].getClassName() + "." + elements[i].getMethodName();
			if(i < elements.length - 1) {
				result += System.getProperty("line.separator");
			}
		}
		error(obj, result);
	}
	
	public static void stackTrace(Object obj, Exception exception) {
		stackTrace(obj.getClass(), exception);
	}
}
