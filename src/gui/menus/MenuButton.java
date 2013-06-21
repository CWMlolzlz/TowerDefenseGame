package gui.menus;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

//button class
public class MenuButton{
	
	public final int NO_MOUSE = 0;
	public final int HOVER_MOUSE = 1;
	public final int CLICK_MOUSE = 2;
	
	private int BUTTONSTATE = NO_MOUSE;
	private int EVENT;
	
	public static final int height = 30;
	public static final int width = 160;
	
	private Image[] subimgs = new Image[3];
	public String text;
	
	public Rectangle shape;
	public float xpos, ypos;
	
	public String levelpath;
	
	public MenuButton(String string, float x, float y, int e) {
		text = string;
		EVENT = e;
		xpos = x;
		ypos = y;
		shape = new Rectangle(x, y, width, height);
	}

	public MenuButton(String string, String path, float x, float y) {
		text = string;
		xpos = x;
		ypos = y;
		levelpath = path;
		shape = new Rectangle(x, y, width, height);
	}

	public boolean mouseInButton(float x, float y){
		if(shape.contains(x, y)){
			return true;
		}else{
			return false;
		}
	}
	
	public Image getImage() {
		// TODO Auto-generated method stub
		return subimgs[BUTTONSTATE];
	}
	
	public void setButtonState(int state){
		BUTTONSTATE = state;
	}
	
	public int getEvent(){
		return EVENT;
	}
}