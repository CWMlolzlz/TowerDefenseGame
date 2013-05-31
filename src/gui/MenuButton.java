package gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

//button class
public class MenuButton{
	
	public final int NO_MOUSE = 0;
	public final int HOVER_MOUSE = 1;
	public final int CLICK_MOUSE = 2;
	
	private int BUTTONSTATE = NO_MOUSE;
	private int EVENT;
	
	private static final int height = 30;
	private static final int width = 60;
	
	private Image[] subimgs = new Image[3];
	public String text;
	
	public Rectangle bounds;
	private static int xpos,ypos;
	
	public MenuButton(String string, int x, int y, int e) {
		text = string;
		EVENT = e;
		xpos = x;
		ypos = y;
		bounds = new Rectangle(x, y, width, height);
	}

	public int getX(){return xpos;}
	public int getY(){return ypos;}
	
	public boolean mouseInButton(float x, float y){
		if((xpos<x && x<xpos+width) && (ypos<y && y<ypos+height)){
			return true;
		}else{return false;}
	}
	
	public Image getImage() {
		// TODO Auto-generated method stub
		return subimgs[BUTTONSTATE];
	}
	
	public void setButtonState(int state){
		BUTTONSTATE = state;
	}
	
	public int getEvent(){return EVENT;}
}