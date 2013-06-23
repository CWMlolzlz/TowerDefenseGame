package gui.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class LevelProgress extends ProgressBar{

	public LevelProgress(float x, float y, int max, float r) {
		super(x,y,max,r);
		this.color = new Color(50,50,255);
	}
	
	public void draw(Graphics g){
		g.setAntiAlias(true);
		g.setColor(Color.black);
		g.fill(new Circle(x,y,radius));
		for(int i = 0; i < progress;i++){
			Wedge w = wedges.get(i);
			//g.setColor(color);
			if(i > progress && i <= added){
				w.fill(g, Color.yellow);
			}else if(i > progress-missed){
				w.fill(g,Color.red);
			}else{
				w.fill(g,color);
			}
			if(progressend < 60){
				//g.setColor(Color.black);
				w.draw(g,Color.black);
			}
			
		}
		g.setColor(Color.gray);
		g.setLineWidth(2);
		g.draw(new Circle(x,y,radius));
		
	}
	
}
