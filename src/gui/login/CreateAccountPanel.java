package gui.login;

import game.Login;
import gui.Panel;
import gui.TextBox;
import gui.modding.Button;

public class CreateAccountPanel extends Panel{

	private TextBox username = new TextBox(240,230,"Username",null);
	private TextBox email = new TextBox(240,180,"Email",null);
	private TextBox password = new TextBox(240,280,"New Password","*");
	private TextBox passwordCheck = new TextBox(240,330,"Retype Password","*");
	
	private Button createAccount = new Button("Create Account", 240, 380, Login.CREATEACCOUNT);
	private Button backtologin = new Button("Back",400,380,Login.BACKTOLOGIN);
	
	public CreateAccountPanel(float x, float y, float w, float h) {
		super(x, y, w, h);
		addElement(username);
		addElement(email);
		addElement(password);
		addElement(passwordCheck);
		addElement(createAccount);
		addElement(backtologin);

	}

	public boolean passwordMatch() {
		return password.truetext.equals(passwordCheck.truetext);
	}
	
	public void clearTextBoxes(){
		username.setToDefault();
		email.setToDefault();
		password.setToDefault();
		passwordCheck.setToDefault();
	}

	public String[] getDetails() {
		return new String[]{username.truetext,email.truetext,password.truetext};
	}

	public boolean isFilled() {
		return !(username.isEmpty() || email.isEmpty() || password.isEmpty());
	}
	
}
