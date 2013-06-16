package gui.modding;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class GUIElement{

	float x,y;
	public Shape shape;
	public String text = "";
	
	public GUIElement(float newx, float newy, float w, float h){
		x = newx;
		y = newy;
		shape = new Rectangle(x,y,w,h);
	}

	public void draw(Graphics g) {
		if(shape != null){
			g.draw(shape);
		}	
		g.drawString(text,x,y);
	}
}
