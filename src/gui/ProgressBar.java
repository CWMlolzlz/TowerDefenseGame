package gui;

import game.Play;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;


public class ProgressBar{
	
	protected Color color = new Color(Color.green);
	
	int missed = 0;
	
	public Shape outline;
	public Shape fill;
	public String bottomtext;
	public String toptext;
	
	public float percent;	
	public int progress;
	public float progressend;
	
	float radius = 20;
	int multsegments = 3;
	
	protected float x,y;
	
	protected ArrayList<Wedge> wedges = new ArrayList<Wedge>();
	
	public ProgressBar(float newx, float newy, int max, float r){
		x = newx;
		y = newy;
		radius = r;
		
		multsegments = (int)(30/max)+1;
		
		double angle = (2*Math.PI/(max));
		double smallangle = angle/multsegments;
		
		for(int i = 0; i < max; i++){
			Polygon p = new Polygon();
			p.addPoint(x,y);
			for(int j = 0; j < 1+multsegments; j++){
				p.addPoint(x+radius*(float)Math.cos(j*smallangle + i*angle), y+radius*(float)Math.sin(j*smallangle + i*angle));
			}
			wedges.add(new Wedge(p));
		}
		
		progress = 0;
		progressend = max;
	}
	
	public void update(boolean bad){
		progress++;
		if(bad){
			missed++;
		}
	}

	public void draw(Graphics g){
		
		g.setAntiAlias(true);
		g.setColor(Color.black);
		g.fill(new Circle(x,y,radius));
		for(int i = 0; i < progress;i++){
			Wedge w = wedges.get(i);
			//g.setColor(color);
			if(i > progress-missed){
				w.fill(g,Color.red);

			}else{
				w.fill(g,color);
			}
			if(progressend < 60){
				//g.setColor(Color.black);
				w.draw(g,Color.black);
			}
			
		}
		
	}
	
}

class Wedge{
	
	Shape s;
	
	public Wedge(Polygon p){
		s = p;
	}
	
	public void draw(Graphics g, Color c){
		g.setColor(Color.black);
		g.draw(s);
	}
	
	public void fill(Graphics g, Color c){
		g.setColor(c);
		g.fill(s);
	}
}
