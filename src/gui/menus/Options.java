package gui.menus;

import game.MainMenu;

public class Options extends Menu{
	
	public Options(float x, float y){
		super(x, y);
			
		addButton(new MenuButton("Video",x + 20f,50,MainMenu.VIDEO));
		addButton(new MenuButton("Audio",x + 20f,100,MainMenu.AUDIO));
		
	}
	
}
