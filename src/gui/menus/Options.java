package gui.menus;

import game.MainMenu;

public class Options extends Menu{
	
	public Options(float x, float y){
		super(x, y);
			
		addButton(new MenuButton("Video",20,50,MainMenu.VIDEO));
		addButton(new MenuButton("Audio",20,100,MainMenu.AUDIO));
		
	}
	
}
