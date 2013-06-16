package gui.menus;

import game.MainMenu;

public class Top extends Menu{

	public Top(float x, float y){
		super(x, y);
		this.x = x;
		this.tick = this.length;
		addButton(new MenuButton("Play",20,50,MainMenu.PLAY));
		addButton(new MenuButton("Options",20,100,MainMenu.OPTIONS));
		
		addButton(new MenuButton("Modding Tools",20,500,MainMenu.MODDING_TOOLS));
		addButton(new MenuButton("Quit",20,550,MainMenu.QUIT));
	}
	
}
