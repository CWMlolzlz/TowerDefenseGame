package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;

public class Meter {
	
	private Color color;
	private TrueTypeFont font;
	public String text;
	
	public int value;
	public int lengthofvalue;
	
	public float x,y;
	
	public float width;
	
	public Meter(float newx, float newy, float w, Color c, TrueTypeFont f){
		x = newx;
		y = newy;
		width = w;
		color = c;
		font = f;
	}
	
	public void draw(Graphics g){
		//g.setFont(font);
		g.setColor(color);
		g.drawString(""+value, x-(lengthofvalue*4)-1, y-6);
	}
	
	public void setValue(int val){
		value = val;
		String s = "" + value;
		lengthofvalue = s.length();
	}
	
}
