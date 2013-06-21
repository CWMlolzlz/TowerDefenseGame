package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class GUIElement{

	protected float x,y,w,h;
	
	public Shape shape;
	public String text = "";
	Color color = Color.white;
	
	public GUIElement(float newx, float newy, float width, float height){
		x = newx;
		y = newy;
		w = width;
		h = height;
		shape = new Rectangle(x,y,w,h);
	}

	public void draw(Graphics g){
		g.setColor(color);
		if(shape != null){
			g.draw(shape);
		}	
		g.drawString(text,x,y);
	}
}
