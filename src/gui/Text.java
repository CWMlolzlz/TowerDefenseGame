package gui;

import org.newdawn.slick.Color;


public class Text extends GUIElement{

	public static final int LEFT = 0, CENTER = 1, RIGHT = 2;
	public int alignment;
	private static float height = 30;
	
	public Text(float newx, float newy, float w, String t, int a) {
		super(newx, newy, w, height);
		
		shape = null;
		text = t;
		setAlignment(a);
	}

	public void setColor(Color c){
		this.color = c;
	}
	
	public void setAlignment(int val){
		int len = text.length();
		alignment = val;
		if(alignment == RIGHT){
			x = origx + w - len*8;
		}else if(alignment == CENTER){
			x = origx + w/2 - (len*4);
		}else if(alignment == LEFT){
			x = origx;
		}
	}
	
	public void setText(String s){
		text = s;
		setAlignment(alignment);
	}
	
}
