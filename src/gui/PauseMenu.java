package gui;

import game.Play;
import gui.menus.Menu;
import gui.menus.MenuButton;

public class PauseMenu extends Menu{
	
	public PauseMenu(){
		super(0,0,"Paused",true);
		
		addButton(new MenuButton("Resume",20,50,Play.RESUME));
		addButton(new MenuButton("Restart Level",20,100,Play.RESTART));
		addButton(new MenuButton("Load Last Checkpoint",20,150,Play.LOADLASTCHECKPOINT));
		addButton(new MenuButton("MainMenu",20,500,Play.MAINMENU));
		addButton(new MenuButton("Quit",20,550,Play.QUIT));
		update();
	}
	
}
