package blazingmammoth.hamaluik.niuheimar.console;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

import blazingmammoth.hamaluik.niuheimar.ScreenProvider;

public class Console implements KeyListener {
	// keep track of what we should be rendering in the background
	private ScreenProvider backgroundScreen;
	
	// caret stuff
    public int caretPos = 0;
    public String caret = "_";
    public String antiCaret = " ";
    public boolean showCaret = false;
    private long lastTime = 0;

    // text buffer stuff
    private StringBuffer textBuffer = new StringBuffer();
    
    // hold output and old commands
    private ArrayDeque<String> messages = new ArrayDeque<String>();
    private int oldCommandIndex = 0;
    private LinkedList<String> oldCommands = new LinkedList<String>();
    
    // states
    private boolean showing = false;
	
	public Console(GameContainer gc) {
	}
	
	public void hide(GameContainer gc) {
		showing = false;
		gc.getInput().disableKeyRepeat();
	}
	
	public void show(GameContainer gc) {
		showing = true;
		gc.getInput().enableKeyRepeat();
	}
	
	public void updateBackgroundScreen(ScreenProvider _backgroundScreen) {
		backgroundScreen = _backgroundScreen;
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		// render the background screen
		backgroundScreen.render(gc, g);
		
		// render the background of the console overtop of it
		// set the colour first
		g.setColor(new Color(0, 0, 0, 127));
		// now draw it
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		
		// render the text entry starter string
		Font font = gc.getDefaultFont();
		font.drawString(2, gc.getHeight() - font.getHeight(">> ") - 2, ">> ");
		
    	// ok, flash the caret
    	if(System.currentTimeMillis() - lastTime > 500) {
    		showCaret = !showCaret;
    		lastTime = System.currentTimeMillis();
    	}
    	
    	// now insert the actual caret
    	String display;
    	if(showCaret) {
    		display = (new StringBuffer(textBuffer)).insert(caretPos, caret).toString();
    	}
    	else {
    		display = (new StringBuffer(textBuffer)).insert(caretPos, antiCaret).toString();
    	}
    	
    	// render the string!
    	font.drawString(2 + font.getWidth(">> "), gc.getHeight() - font.getHeight(">> ") - 2, display, Color.white);
    	
    	// now display the messages
    	float y = gc.getHeight() - (2 * font.getHeight("> ")) - 6;
    	// and loop over all the messages!
    	Iterator<String> it = messages.iterator();
    	while(it.hasNext()) {
    		String message = it.next();
    		font.drawString(2, y, message, Color.lightGray);
    		y -= (font.getHeight("> ") + 2);
    	}
	}

	@Override
	public void inputEnded() {}

	@Override
	public void inputStarted() {}

	@Override
	public boolean isAcceptingInput() {
		return showing;
	}

	@Override
	public void setInput(Input input) {}

	@Override
	public void keyPressed(int key, char c) {		
		if(key == Input.KEY_TAB) {
			// handle auto-complete
		}
        else if(key == Input.KEY_LEFT && caretPos > 0) {
        	// handle going left
        	caretPos--;
        }
        else if(key == Input.KEY_RIGHT && caretPos < textBuffer.length()) {
        	// handle going right
        	caretPos++;
        }
        else if(key == Input.KEY_BACK && textBuffer.length() > 0 && caretPos > 0) {
        	// handle deleting characters backwards
        	textBuffer.deleteCharAt(caretPos - 1);
        	caretPos--;
        }
        else if(key == Input.KEY_UP && oldCommandIndex < oldCommands.size()) {
        	// handle scrolling old commands (up)
        	textBuffer = new StringBuffer(oldCommands.get(oldCommandIndex));
        	caretPos = textBuffer.length();
        	oldCommandIndex++;
        }
        else if(key == Input.KEY_DELETE && caretPos < textBuffer.length() && caretPos < textBuffer.length()) {
        	// handle deleting characters forwards
        	textBuffer.deleteCharAt(caretPos);
        }
        else if(key == Input.KEY_ENTER || key == Input.KEY_RETURN) {
        	// handle pressing enter
        	handleTextCommand();
        	
        	// reset the buffer
        	textBuffer.setLength(0);
        	caretPos = 0;
        	
        	// reset the old command list
        	oldCommandIndex = 0;
        }
        else if(key == Input.KEY_GRAVE) {
        	// don't handle the ~
        	
        }
        else if(allowedChar(c)) {
        	// normal typing
        	textBuffer.insert(caretPos, c);
        	caretPos++;
        }
	}

	@Override
	public void keyReleased(int key, char c) {}
	
	private boolean allowedChar(char c) {
		return (c >= ' ' && c <= '}');
	}
	
	private void addOldCommand(String command) {
		oldCommands.add(command);
		// pop the last one off if we're too big
		if(oldCommands.size() > 100) {
			oldCommands.removeFirst();
		}
	}
	
	private void addMessage(String message) {
		messages.push(message);
		// pop the last one off if we're too big
		if(messages.size() > 100) {
			messages.removeLast();
		}
	}
	
	private void handleTextCommand() {
		// add the current command to the old commands
		addOldCommand(textBuffer.toString());
		
		// add the current command to the top of queue
		addMessage("> " + textBuffer.toString());
	}
}
