package gui.menus;

import game.MainMenu;
import gui.Button;
import gui.Menu;

public class Top extends Menu{

	public Top(float x, float y){
		super(x, y,"Main Menu",true);
		
		addElement(new Button("Play",20,50,MainMenu.PLAY));
		addElement(new Button("Options",20,100,MainMenu.OPTIONS));
		
		//addButton(new Button("Modding Tools",20,500,MainMenu.MODDING_TOOLS));
		addElement(new Button("Quit",20,550,MainMenu.QUIT));
	}
	
}
