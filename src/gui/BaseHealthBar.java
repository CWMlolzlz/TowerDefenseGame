package gui;

import game.Play;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class BaseHealthBar extends ProgressBar{

	private float start = Play.baseHealth;
	
	public BaseHealthBar(float newx, float newy,float r) {
		super(newx, newy, (int) Play.baseHealth, r);
		this.progress = (int) Play.baseHealth;
		update();
	}
	
	public void update(){
		progress = Play.baseHealth;
		
		int r = (int) (512-((float)(progress/start))*512);
		int g = (int) (((float)(progress/start))*512);
		
		color = new Color(r,g,0);
		
	}

	public void draw(Graphics g){
		//System.out.println(Play.baseHealth);
		g.setAntiAlias(true);
		g.setColor(Color.black);
		g.fill(new Circle(x,y,radius));
		g.setColor(color);
		for(int i = 0; i < progress;i++){
			
			wedges.get(i).fill(g,color);
		}
		
	}
	
}
