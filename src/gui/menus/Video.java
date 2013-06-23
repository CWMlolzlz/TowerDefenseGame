package gui.menus;

import game.MainMenu;
import gui.Button;
import gui.Menu;

public class Video extends Menu{
	
	public Video(float x, float y){
		super(x, y,"Video",false);
			
		addElement(new Button("Video",20,50,MainMenu.VIDEO));
		addElement(new Button("Audio",20,100,MainMenu.AUDIO));
		
	}
	
}

