package gui.menus;

import gui.Button;

public class LevelSelectButton extends Button{

	public String levelpath;
	
	public LevelSelectButton(String string, String path, float x, float y){
		super(string,x,y,-2);
		levelpath = path;		
	}
}