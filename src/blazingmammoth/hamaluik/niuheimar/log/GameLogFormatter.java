package blazingmammoth.hamaluik.niuheimar.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameLogFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		String string = "";
		
		string += "[" + convertMillis(record.getMillis()) + "] ";
		string += record.getMessage();
		string += System.getProperty("line.separator");
		
		return string;
	}
	
	private String convertMillis(long millis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd HH:mm:ss");
		return dateFormat.format(new Date(millis));
	}
	
	@Override
	public String getHead(Handler handler) {
		return "--- New session: " + convertMillis(System.currentTimeMillis()) + " ---" + System.getProperty("line.separator");
	}
}
