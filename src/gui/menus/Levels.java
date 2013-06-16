package gui.menus;

import game.MainMenu;

public class Levels extends Menu{
	
	public Levels(float x, float y){
		super(x, y);
			
		addButton(new MenuButton("New Level",20,50,MainMenu.NEW_LEVEL));
		addButton(new MenuButton("Resume",20,100,MainMenu.RESUME));
		
	}
}
