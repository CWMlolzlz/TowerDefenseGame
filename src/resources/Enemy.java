package resources;

import game.Play;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Enemy {

	//enemy data
	private String name;
	private float health, sheild, speed, regen;
	private int ID,reward,cost;
	
	private int radius = 10;
	public float x, y;
	private int currentsegment = -1;
	private float currentdistanceonsegment = 0;
	private float currentsegmentlength = 0;
	private int totalsegments;
	private double segmentangle;
	
	private HealthBar healthbar = new HealthBar(30,2, 1); //declared here to stop bugs
		
	private static ArrayList<Node> nodes;
	private Circle shape;
	private boolean alive;
	
	public Enemy(ArrayList<Node> n, EnemyType etype){
		
		ID = etype.ID;
		name = etype.NAME;
		
		health = etype.HEALTH;
		sheild = etype.SHEILD;
		speed = etype.SPEED;
		regen = etype.REGEN;
		
		reward = etype.REWARD;
		cost = etype.COST;
		
		alive = true;
		healthbar = new HealthBar(30,2, health);
		shape = new Circle(x,y,radius);
		nodes = n;
		x = nodes.get(0).x;
		y = nodes.get(0).y;
		totalsegments = nodes.size()-2; //may cause issues (-2 is fine)
		
	}
	
	public Shape getShape(){return shape;}
	public boolean isAlive(){return alive;}
	
	public void step(){
		
		if(health <= 0){
			new Explosion(x,y);
			Play.pay(reward);
			Level.enemiesKilled++;
			Level.removeEnemy(this);
		}
		
		if(currentdistanceonsegment >= currentsegmentlength){
			if(currentsegment == totalsegments){
				//destroy
				Level.removeEnemy(this);
			}else{
				newSegment();
			}
		}else{
			currentdistanceonsegment += speed;
			x = (float) (nodes.get(currentsegment).x + currentdistanceonsegment*Math.cos(segmentangle)); //x coordinate
			y = (float) (nodes.get(currentsegment).y + currentdistanceonsegment*Math.sin(segmentangle)); //y coordinate
		}
		
		shape = new Circle(x,y,radius);
		healthbar.update(x - 15, y-18, health);
	}
	
	private void newSegment(){
		currentsegment++;
		currentdistanceonsegment = 0;
		Node p1 = nodes.get(currentsegment);
		Node p2 = nodes.get(currentsegment+1);
		
		Line segmentline = new Line(p1.x, p1.y,p2.x,p2.y);
		currentsegmentlength = segmentline.length();
		
		float rise = p2.y - p1.y;
		int multiplier = 1;
		double offset = 0;
		if(p1.x > p2.x){
			multiplier = -1;
			offset = Math.PI;
		}
		segmentangle = Math.asin(multiplier*rise/currentsegmentlength) + offset;
		
	}

	public void damage(int d, boolean beam){
		health -= d;
		if(beam){
			//beam particles effects
		}else{
			new Spark(x,y);
		}
	}
	
	public Shape getHealthBarShape(){return healthbar.getShape();}
	public Color getHealthBarColor(){return healthbar.getColor();}
	
}

class HealthBar{
	
	private Shape shape;
	private float height;
	private float width;
	private Color color;
	private float startHealth;
	
	public HealthBar(int w, int h, float health){
		width = w;
		height = h;
		startHealth = health;
	}
	
	public void update(float x, float y, float health){
		int r = (int) (511- 2*(health / startHealth*255));
		int g = (int) (2*health / startHealth*255);
		int b = 0;
		color = new Color(r,g,b);
		
		shape = new Rectangle(x, y, (health/startHealth)*width, height);
	}
	
	public Color getColor(){return color;}
	public Shape getShape(){return shape;}
	
}
