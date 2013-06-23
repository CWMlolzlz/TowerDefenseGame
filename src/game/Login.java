package game;

import gui.Button;
import gui.GUIElement;
import gui.Panel;
import gui.Text;
import gui.TextBox;
import gui.login.CreateAccountPanel;
import gui.login.LoginPanel;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Login extends BasicGameState implements KeyListener{

	public static final int LOGIN = 0, NEWACCOUNT = 5,QUIT = 9, BACKTOLOGIN = 50, CREATEACCOUNT = 51;
	public static Point mousepoint;
	
	private static CreateAccountPanel createAccountPanel = new CreateAccountPanel(200,150,400,280);
	private static LoginPanel loginPanel = new LoginPanel(200,150, 400, 230);
	private static Text label = new Text(230, 150, 300, "Enter Login Details", Text.CENTER);
	
	private static Panel activePanel = loginPanel;
	private static TextBox textBoxFocus = null;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException{
		loginPanel.addElement(label);
		createAccountPanel.addElement(label);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		activePanel.draw(g);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		mousepoint = new Point(Mouse.getX(), gc.getHeight() - Mouse.getY());
	}

	@Override
	public int getID() {
		return 0;
	}

	//mouse clicks
	public void leftClick(StateBasedGame sbg){
		if(textBoxFocus!=null){
			if(textBoxFocus.text == ""){
				textBoxFocus.setToDefault();
			}
		}
		
		GUIElement e = activePanel.getClickedElement(mousepoint);
		if(e instanceof Button){
			checkEvent(((Button) e).getEvent());
		}else if(e instanceof TextBox){
			((TextBox) e).select();
			textBoxFocus = (TextBox) e;
		}
	}
	
	private void checkEvent(int event) {
		switch(event){
		case(LOGIN):
			if(loginPanel.isFilled() && loginPanel.email.truetext.contains("@")){
				label.setText("Logging In");
				NetworkConnect.login(loginPanel.getDetails());
			}else{
				error("Please Fill Out Form Completely");
			}
			break;
		case(NEWACCOUNT):
			activePanel = createAccountPanel;
			label.setText("Enter New Account Details");
			label.setColor(Color.white);
			createAccountPanel.clearTextBoxes();
			break;
		case(BACKTOLOGIN):
			activePanel = loginPanel;
			label.setText("Enter Login Details");
			label.setColor(Color.white);
			loginPanel.clearTextBoxes();
			break;
		case(CREATEACCOUNT):
			if(createAccountPanel.passwordMatch()){
				if(createAccountPanel.isFilled()){
					label.setText("Creating Account");
					NetworkConnect.createAccount(createAccountPanel.getDetails());
				}else{
					error("Please Fill Out Form Completely");
				}
			}else{
				createAccountPanel.clearTextBoxes();
				error("Passwords dont match");
			}
			break;
		case(QUIT):
			Launch.quit();
			break;
		}
	}

	@Override
    public void keyPressed(int k, char c){
		int ascii = (int) c;
		if(31 <= ascii && ascii <= 126){
			input(c);
		}else if(ascii == 13){
			if(activePanel == loginPanel){
				checkEvent(LOGIN);
			}else{
				checkEvent(CREATEACCOUNT);
			}
		}else if(ascii == 8){
			backspace();
		}else if(ascii == 9){
			//next textbox
			if(textBoxFocus != null){
				if(textBoxFocus.isEmpty()){
					textBoxFocus.setToDefault();
				}
				textBoxFocus = activePanel.nextTextBox(textBoxFocus);
				textBoxFocus.select();
			}
		}
    }
	
	private void backspace() {
		if(textBoxFocus != null){
			textBoxFocus.backSpace();
		}	
	}

	private void input(char c) {
		if(textBoxFocus != null){
			textBoxFocus.addChar(c);
		}		
	}

	public static void error(String response){
		label.setColor(Color.red);
		label.setText(response);
		if(activePanel instanceof LoginPanel){
			((LoginPanel)activePanel).clearTextBoxes();
		}else{
			((CreateAccountPanel)activePanel).clearTextBoxes();
		}
	}
	
	public static void createdAccount(){
		activePanel = loginPanel;
		createAccountPanel.clearTextBoxes();
		loginPanel.clearTextBoxes();
	}
	
}
