package gui.menus;

import game.MainMenu;

public class Video extends Menu{
	
	public Video(float x, float y){
		super(x, y,"Video",false);
			
		addButton(new MenuButton("Video",20,50,MainMenu.VIDEO));
		addButton(new MenuButton("Audio",20,100,MainMenu.AUDIO));
		
	}
	
}

