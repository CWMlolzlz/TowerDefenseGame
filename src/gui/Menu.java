package gui;

import game.MainMenu;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Menu{

	public static int width = 200, height = 600;
	
	public float x,y;
	
	public Shape outline;
	public ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	public Menu(float newx, float newy){
		x = newx;
		y = newy;
		update();
	}
	
	
	public void draw(Graphics g) {
		g.draw(outline);
		for(int j = 0; j < buttons.size(); j++){
			MenuButton button = buttons.get(j);
			g.drawString(button.text, button.xpos, button.ypos);
			g.draw(button.shape);
		
		}
	}
	
	public void addButton(MenuButton menuButton) {
		buttons.add(menuButton);
	}
	public void update() {
		outline  = new Rectangle(x,y,width,height);
	}


	public MenuButton getClickedButton() {
		Shape mp = MainMenu.mousepoint;
		for(int i = 0; i < buttons.size(); i++){
			MenuButton b = buttons.get(i);
			if(b.shape.contains(mp)){
				return b;
			}
		}
		return null;
	}
	
}
