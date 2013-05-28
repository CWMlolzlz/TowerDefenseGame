package resources;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Button{
	
	private float x, y;
	private float textx, texty;
	
	public final int NO_MOUSE = 0;
	public final int HOVER_MOUSE = 1;
	public final int CLICK_MOUSE = 2;
	
	private int EVENT;
	private int COST;
	
	private Shape shape;
	private String text;
	
	public Button(float xpos, float ypos, float width, float height, String string, int e, int cost){
		x = xpos;
		y = ypos;
		textx = x+5;
		texty = y+2;		
		
		shape = new Rectangle(x,y,width,height);
		text = string;
		EVENT = e;
		COST = cost;
		
	}
	
	public String getText(){
		return text;
	}
	
	public int getEvent(){
		return EVENT;
	}
	
	public Shape getShape(){
		return shape;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getTextX(){
		return textx;
	}
	
	public float getTextY(){
		return texty;
	}
	
	public int getCost(){
		return COST;
	}
	
}
