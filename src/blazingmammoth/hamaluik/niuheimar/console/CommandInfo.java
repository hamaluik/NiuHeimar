package blazingmammoth.hamaluik.niuheimar.console;

import java.lang.reflect.Method;

public class CommandInfo {
	public String alias;
	public String[] argTypes;
	public String description;
	public String longDescription;
	public Method method;
	
	public CommandInfo(String _alias, String[] _argTypes, String _description, String _longDescription, Method _method) {
		alias = _alias;
		argTypes = _argTypes;
		description = _description;
		if(_longDescription.equals("")) {
			longDescription = _description;
		}
		else {
			longDescription = _longDescription;
		}
		method = _method;
	}
}