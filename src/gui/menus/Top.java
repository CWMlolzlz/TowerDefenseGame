package gui.menus;

import game.MainMenu;
import gui.Menu;
import gui.MenuButton;

public class Top extends Menu{

	public Top(float x, float y){
		super(x, y);
		
		addButton(new MenuButton("Play",20,50,MainMenu.PLAY));
		addButton(new MenuButton("Options",20,100,MainMenu.OPTIONS));
		addButton(new MenuButton("Quit",20,550,MainMenu.QUIT));
		
	}
	
}
