package gui.game;

import game.Launch;
import game.Play;
import gui.Menu;
import gui.Button;

public class LevelCompleteMenu extends Menu{

	public LevelCompleteMenu(String title, boolean complete) {
		super(0, 0,title,true);
		if(complete){
			//addButton(new Button("Next Level", 20, 100, Play.NEXTLEVEL));
			if(Launch.USERNAME!=""){
				addElement(new Button("Upload Score",20,150, Play.UPLOAD_SCORE));
			}
		}
		addElement(new Button("Restart Level",20,50,Play.RESTART));
		addElement(new Button("MainMenu",20,500,Play.MAINMENU));
		addElement(new Button("Quit",20,550,Play.QUIT));
		
	}

}
