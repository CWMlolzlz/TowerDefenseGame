package gui;

import game.Play;
import gui.menus.Menu;
import gui.menus.MenuButton;

public class PauseMenu extends Menu{
	
	public PauseMenu(){
		super(0,0);
		this.x = 0;
		this.tick = this.length;
		addButton(new MenuButton("Resume",20,50,Play.RESUME));
		addButton(new MenuButton("Quit",20,540,Play.QUIT));
		update();
	}
	
}
