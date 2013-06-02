package gui.menus;

import org.newdawn.slick.geom.Rectangle;

public class LevelSelectButton extends MenuButton{

	public String levelpath;
	
	public String text;
	
	public Rectangle shape;
	public float xpos, ypos;
	
	private float width = 160, height = 30;
	
	public LevelSelectButton(String string, String path, float x, float y){
		super(string,path,x,y);
		text = string;
		levelpath = path;
		xpos = x;
		ypos = y;
		shape = new Rectangle(xpos,ypos,width,height);
	}
	
	public String getLevelPath(){
		return levelpath;
	}
}