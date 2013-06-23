package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class GUIElement{

	public float x,y,w,h, origx, origy;
		
	public Shape shape;
	public String text = "";
	public Color color = Color.white;
	
	public ArrayList<GUIElement> guielements = new ArrayList<GUIElement>();
	
	public GUIElement(float newx, float newy, float width, float height){
		x = newx;
		y = newy;
		origx = x;
		origy = y;
		w = width;
		h = height;
		shape = new Rectangle(x,y,w,h);
	}

	public void addElement(GUIElement e){
		guielements.add(e);
	}
	
	public void draw(Graphics g){
		if(shape != null){
			g.setColor(Color.black);
			g.fill(shape);
			g.setColor(color);
			g.draw(shape);
		}
		for(int i = 0; i < guielements.size(); i++){
			guielements.get(i).draw(g);
		}
		g.setColor(color);
		g.drawString(text,x,y);
	}
	
	public void updateShape(){
		shape = new Rectangle(x,y,w,h);
	}
}
