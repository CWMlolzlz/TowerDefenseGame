package gui.menus;

import game.MainMenu;
import gui.Menu;
import gui.MenuButton;

public class LevelSelect extends Menu{

	public LevelSelect(float x, float y){
		super(x, y);
		
		addButton(new MenuButton("New Level",x + 20f,50,MainMenu.NEW_LEVEL));
		addButton(new MenuButton("Resume",x + 20f,100,MainMenu.RESUME));
		
	}
	
}
