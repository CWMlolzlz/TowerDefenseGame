package gui.modding;

import gui.GUIElement;

import org.newdawn.slick.Graphics;

public class Button extends GUIElement{

	private int EVENT;
		
	static float width = 140, height = 30;
	
	public Button(String t,float newx, float newy,int e) {
		super(newx, newy,width,height);
		EVENT = e;
		text = t;
	}
	
	public void draw(Graphics g){
		g.draw(shape);
		g.drawString(text,x,y);
	}
	
	public int getEvent(){
		return EVENT;
	}

}
