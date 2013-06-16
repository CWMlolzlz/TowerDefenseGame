package gui.menus;

import game.Launch;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Menu{

	public int width = 200, height = 600;
	
	public float x,y,endx,endy;
	
	public Shape outline;
	public ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	public int tick = 0;
	public int length = 45;
	float k;
	
	public Menu(float newx, float newy){
		endx = newx;
		endy = newy;
		k = (float)(Launch.gamecontainer.getWidth()-endx)/(length*length);
		update();
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fill(outline);
		g.setColor(Color.white);
		g.draw(outline);
		for(int j = 0; j < buttons.size(); j++){
			MenuButton button = buttons.get(j);
			float bx = x+button.xpos;
			float by = y+button.ypos;
			g.drawString(button.text, bx, by);
			g.draw(button.shape);
		}
	}
	
	public void addButton(MenuButton menuButton) {
		buttons.add(menuButton);
		
	}
	public void update(){
		if(tick < length){
			tick++;
			x = k*(float)Math.pow((tick-length),2)+endx;
				
		}
		y=endy;
		outline  = new Rectangle(x,y,width,height);
		for(int j = 0; j < buttons.size(); j++){
			MenuButton button = buttons.get(j);
			float bx = x+button.xpos;
			float by = y+button.ypos;
			button.shape = new Rectangle(bx,by,MenuButton.width,MenuButton.height);
		}
	}

	
	public MenuButton getClickedButton(Shape mp) {
		for(int i = 0; i < buttons.size(); i++){
			MenuButton b = buttons.get(i);
			if(b.shape.contains(mp)){
				return b;
			}
		}
		return null;
	}
	
}
