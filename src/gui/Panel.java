package gui;



import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Panel{

	protected float x,y;
	
	public Shape outline;
	static Color color = Color.white;
	
	ArrayList<GUIElement> guielements = new ArrayList<GUIElement>();
	
	public Panel(float x, float y,float w, float h){
		outline = new Rectangle(x,y,w,h);
		this.x = x;
		this.y = y;
	}
	
	public void addElement(GUIElement e){
		guielements.add(e);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.draw(outline);
		for(int i = 0; i < guielements.size(); i++){
			guielements.get(i).draw(g);
		}
	}

	public GUIElement getClickedElement(Point mousepoint) {
		for(int i = 0; i < guielements.size();i++){
			GUIElement guie = guielements.get(i);
			if(guie.shape !=null && guie.shape.contains(mousepoint)){
				return guie;
			}
		}
		return null;
	}

	
	
}
