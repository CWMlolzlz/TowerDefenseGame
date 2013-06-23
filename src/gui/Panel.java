package gui;

import org.newdawn.slick.geom.Point;

public class Panel extends GUIElement{

	public Button defaultButton;
	
	public Panel(float newx, float newy,float w, float h){
		super(newx, newy, w, h);
		x = newx;
		y = newy;
	}
	
	/**
	public void draw(Graphics g){
		g.setColor(color);
		g.draw(outline);
		for(int i = 0; i < guielements.size(); i++){
			guielements.get(i).draw(g);
		}
	}
	**/

	public GUIElement getClickedElement(Point mousepoint) {
		for(int i = 0; i < guielements.size();i++){
			GUIElement guie = guielements.get(i);
			if(guie.shape !=null){
				if(guie.shape.contains(mousepoint)){
					return guie;
				}
			}
		}
		return null;
	}

	public TextBox nextTextBox(TextBox tb){
		int textBoxIndex = guielements.indexOf(tb);
		TextBox nextTB = null;
		for(int i = textBoxIndex + 1; i <= guielements.size(); i++){
			if(i == guielements.size()){
				i = 0;
			}
			GUIElement ge = guielements.get(i);
			if(ge instanceof TextBox){
				nextTB = (TextBox)ge;
				break;
			}
		}
		return nextTB;
	}
	
}
