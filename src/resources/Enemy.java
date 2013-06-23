package resources;

import game.Play;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import resources.types.EnemyType;

public class Enemy {

	//enemy data
	private String name;
	private float starthealth, health, shield, speed, regen;
	private int ID,reward,cost;
	
	private float xoff, yoff;
	
	public int radius;
	public float x, y;
	public int currentsegment = -1;
	public float currentdistanceonsegment = 0;
	private float currentsegmentlength = 0;
	private int totalsegments;
	private double segmentangle;
	
	private HealthBar healthbar = new HealthBar(30, 4, 1, 1); //declared here to stop bugs
		
	private static ArrayList<PathPoint> PathPoints;
	private Circle shape;
	private boolean alive;
	
	public Enemy(ArrayList<PathPoint> n, EnemyType etype){
		
		Random r = new Random();
		
		xoff = (r.nextFloat()-.5f)*10;
		yoff = (r.nextFloat()-.5f)*10;
		
		float m = (float) Math.pow(1.12f,Play.currentWave-1);
		
		ID = etype.ID;
		name = etype.NAME;
		
		starthealth = m*etype.HEALTH;
		health = starthealth;
		radius = etype.SIZE;
		shield = m*etype.SHIELD;
		speed = etype.SPEED;
		regen =	m*etype.REGEN;
		
		reward = etype.REWARD;
		cost = etype.COST;
		
		alive = true;
		healthbar = new HealthBar(3*radius,2, health, shield);
		shape = new Circle(x+xoff,y+yoff,radius);
		PathPoints = n;
		x = PathPoints.get(0).x;
		y = PathPoints.get(0).y;
		totalsegments = PathPoints.size()-2; //may cause issues (-2 is fine)
		
	}
	
	public Enemy(float f, float g, EnemyType etype) {
		ID = etype.ID;
		name = etype.NAME;
		
		starthealth = etype.HEALTH;
		health = starthealth;
		shield = etype.SHIELD;
		speed = etype.SPEED;
		regen = etype.REGEN;
		
		reward = etype.REWARD;
		cost = etype.COST;
		
		alive = true;
		healthbar = new HealthBar(30,2, health, shield);
		shape = new Circle(x+xoff,y+yoff,radius);
		x = f;
		y = g;
	}

	public Shape getShape(){return shape;}
	public boolean isAlive(){return alive;}
	
	public void step(){
		
		if(health <= 0){
			new Explosion(x+xoff,y+yoff);
			Play.pay(reward);
			Play.enemyKilled(this);
		}else{
			if(health < starthealth){
				health += regen;
				if(health > starthealth){
					health = starthealth;
				}
			}
			
			if(currentdistanceonsegment >= currentsegmentlength){
				if(currentsegment == totalsegments){
					Play.baseHealth -= radius;
					Play.LGUI.updateBaseHealthBar();
					Play.enemyReachedEnd(this);
				}else{
					newSegment();
				}
			}else{
				currentdistanceonsegment += speed;
			}
				x = (float) (PathPoints.get(currentsegment).x + currentdistanceonsegment*Math.cos(segmentangle)); //x coordinate
				y = (float) (PathPoints.get(currentsegment).y + currentdistanceonsegment*Math.sin(segmentangle)); //y coordinate
			
			x += xoff;
			y += yoff;
			shape = new Circle(x,y,radius);
			healthbar.update(x - 1.5f*radius, y - (radius + 5), health, shield);
		}
	}
	
	private void newSegment(){
		currentsegment++;
		currentdistanceonsegment = 0;
		PathPoint p1 = PathPoints.get(currentsegment);
		PathPoint p2 = PathPoints.get(currentsegment+1);
		
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
		if(shield < d){
			health -= (d - shield);
			shield = 0;
		}else{
			shield -= d;
		}
		
		if(beam){
			//beam particles effects
		}else{
			new Spark(x,y);
		}
	}
	
	public Shape getHealthBarShape(){return healthbar.getShape();}
	public Color getHealthBarColor(){return healthbar.getColor();}
	public Shape getShieldBarShape(){return healthbar.getShieldShape();}
	
}

class HealthBar{
	private Shape shieldshape;
	private Shape shape;
	private float height;
	private float width;
	private Color color;
	private float startHealth;
	private float startshield;
	public HealthBar(int w, int h, float health, float shield){
		width = w;
		height = h;
		startHealth = health;
		startshield = shield;
	}
	
	public void update(float x, float y, float health, float shield){
		int r = (int) (512- (health / startHealth*512));
		int g = (int) (health / startHealth*512);
		int b = 0;
		color = new Color(r,g,b);
		shape = new Rectangle(x, y, (health/startHealth)*width, height);
		shieldshape = new Rectangle(x,y-height,(shield/startshield)*width,height);
	}
	
	public Color getColor(){return color;}
	public Shape getShape(){return shape;}
	public Shape getShieldShape(){return shieldshape;}
	
}
