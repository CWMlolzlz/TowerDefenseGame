package gui.menus;

import game.MainMenu;

public class PlayMenu extends Menu{
	
	public PlayMenu(float x, float y){
		super(x, y,"Play",false);
			
		addButton(new MenuButton("New Level",20,50,MainMenu.NEW_LEVEL));
		addButton(new MenuButton("Resume",20,100,MainMenu.RESUME));
		
	}
}
