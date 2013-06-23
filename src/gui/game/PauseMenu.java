package gui.game;

import game.Play;
import gui.Menu;
import gui.Button;

public class PauseMenu extends Menu{
	
	public PauseMenu(){
		super(0,0,"Paused",true);
		
		addElement(new Button("Resume",20,50,Play.RESUME));
		addElement(new Button("Restart Level",20,100,Play.RESTART));
		addElement(new Button("Load Last Checkpoint",20,150,Play.LOADLASTCHECKPOINT));
		addElement(new Button("MainMenu",20,500,Play.MAINMENU));
		addElement(new Button("Quit",20,550,Play.QUIT));
		update();
	}
	
}
