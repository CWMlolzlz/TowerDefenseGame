package gui.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class WaveProgress extends ProgressBar{

	public int missed = 0;
	public int added = 0;
	public int progress = 0;
	
	public WaveProgress(float x, float y, int max, float r) {
		super(x,y,max,r);
		this.color = new Color(50,255,50);
	}
	
	public void update(){
		progress++;
	}
	
	public void draw(Graphics g){
		g.setAntiAlias(true);
		g.setColor(Color.black);
		g.fill(new Circle(x,y,radius));
		for(int i = 0; i < progress+added+missed;i++){
			Wedge w = wedges.get(i);
			//g.setColor(color);
			if(i < progress){
				w.fill(g,color);
			}else if(i < added+progress){
				w.fill(g,Color.yellow);
			}else{
				w.fill(g, Color.red);
			}
			if(progressend < 60){
				w.draw(g,Color.black);
			}
			
		}
		g.setColor(Color.gray);
		g.setLineWidth(2);
		g.draw(new Circle(x,y,radius));
	}

	public void spawn(){
		added++;
	}
	
	public void reachedEnd() {
		missed++;
		added--;
	}
	
	public void killed() {
		progress++;
		added--;
	}
	
	
}
