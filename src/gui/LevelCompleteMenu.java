package gui;

import game.Play;
import gui.menus.Menu;
import gui.menus.MenuButton;

public class LevelCompleteMenu extends Menu{

	public LevelCompleteMenu(String title, boolean complete) {
		super(0, 0,title,true);
		if(complete){
			//addButton(new MenuButton("Next Level", 20, 100, Play.NEXTLEVEL));
			//addButton(new MenuButton("Upload Score",20,150, Play.UPLOAD_SCORE));
		}
		addButton(new MenuButton("Restart Level",20,50,Play.RESTART));
		addButton(new MenuButton("MainMenu",20,500,Play.MAINMENU));
		addButton(new MenuButton("Quit",20,550,Play.QUIT));
		
	}

}
