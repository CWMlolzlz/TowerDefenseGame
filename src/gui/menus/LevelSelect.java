package gui.menus;

import game.MainMenu;
import gui.Menu;

import java.util.ArrayList;

import resources.data.LevelInformation;

public class LevelSelect extends Menu{

	private ArrayList<LevelInformation> levelinfo = MainMenu.levellistdata.levelinfo;
		
	public LevelSelect(float x, float y){
		super(x, y,"Level Select",false);
		for(int i = 0; i < levelinfo.size(); i++){
			LevelInformation linfo = levelinfo.get(i);
			String text = linfo.NAME;
			String description = linfo.DESCRIPTION;
			String path = linfo.PATH;
			addElement(new LevelSelectButton(text, path, 20, ((i+1)*50)));
		}	
	}	
}
