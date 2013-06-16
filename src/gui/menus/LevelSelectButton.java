package gui.menus;

import org.newdawn.slick.geom.Rectangle;

public class LevelSelectButton extends MenuButton{

	public String levelpath;
	
	public String text;
	
	public Rectangle shape;
	public float xpos, ypos;
	
	public LevelSelectButton(String string, String path, float x, float y){
		super(string,path,x,y);
		text = string;
		levelpath = path;
		xpos = x;
		ypos = y;
		
	}
	
	public String getLevelPath(){return levelpath;}
}