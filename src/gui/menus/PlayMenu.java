package gui.menus;

import game.MainMenu;
import gui.Button;
import gui.Menu;

public class PlayMenu extends Menu{
	
	public PlayMenu(float x, float y){
		super(x, y,"Play",false);
			
		addElement(new Button("New Level",20,50,MainMenu.NEW_LEVEL));
		addElement(new Button("Resume",20,100,MainMenu.RESUME));
		
	}
}
