package gui.login;

import game.Login;
import gui.Button;
import gui.Panel;
import gui.TextBox;

public class LoginPanel extends Panel{

	public TextBox email = new TextBox(240, 180, "Email", null);
	private TextBox password = new TextBox(240, 230, "Password", "*");
	private Button login = new Button("Login", 240, 280, Login.LOGIN);
	private Button quit = new Button("Quit",400,280,Login.QUIT);
	private Button newAccount = new Button("Register",320,330, Login.NEWACCOUNT);
	
	public LoginPanel(float x, float y, float w, float h) {
		super(x, y, w, h);
		addElement(email);
		addElement(password);
		addElement(login);
		addElement(quit);
		addElement(newAccount);
		login.w = 140;
		login.updateShape();
		
		quit.w = 140;
		quit.updateShape();
		
		newAccount.w = 140;
		newAccount.updateShape();
		
		defaultButton = login;
	}

	public void clearTextBoxes(){
		
		password.setToDefault();
		email.setToDefault();
	}

	public String[] getDetails() {
		return new String[]{email.truetext, password.truetext};
	}
	
	public boolean isFilled() {
		return !(email.isEmpty() || password.isEmpty());
	}
	
}
