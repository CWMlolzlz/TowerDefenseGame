package gui.menus;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import resources.data.LevelInformation;

import game.MainMenu;

public class LevelSelect extends Menu{

	//private ArrayList<LevelSelectButton> buttons = new ArrayList<LevelSelectButton>();
	private ArrayList<LevelInformation> levelinfo = MainMenu.levellistdata.levelinfo;
	
	
	public LevelSelect(float x, float y){
		super(x, y);
		for(int i = 0; i < levelinfo.size(); i++){
			LevelInformation linfo = levelinfo.get(i);
			String text = linfo.NAME;
			String description = linfo.DESCRIPTION;
			String path = linfo.PATH;
			System.out.println(path);
			addButton(new LevelSelectButton(text, path, x + 20, +((i+1)*50)));
			//buttons.add(new LevelSelectButton(text, id, x + 20, +((i+1)*50)));
		}	
	}
	
	public void draw(Graphics g){
		g.draw(outline);
		for(int j = 0; j < buttons.size(); j++){
			MenuButton button = buttons.get(j);
			g.drawString(button.text, button.xpos, button.ypos);
			g.draw(button.shape);
		}
	}
	
}
