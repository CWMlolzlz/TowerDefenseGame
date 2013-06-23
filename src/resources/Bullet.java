package resources;

import game.Play;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

import resources.types.BulletType;

public class Bullet{

	private float x,y;
	private float shellradius = 2;
	
	private String graphic;
	private Shape shape;
	private float angle;
	public int DAMAGE;
	private int speed;
	private boolean alive;
	private Color color;
	public float decay = 1; //visible
	
	public Bullet(BulletType bt, int damage, float theta, float newx, float newy){
		alive = true;
		DAMAGE = damage;
		shape = bt.SHAPE;
		speed = bt.SPEED;
		graphic = bt.GRAPHIC;
		color = bt.COLOR;
		x = newx;
		y = newy;
		angle = theta;
		createShape();
	}
	
	public void step(){
		if(x < -10 || y < -10 || x > 1000 || y > 1000){
			Play.removeBullet(this);
		}else{
			x -= speed*cos(angle);
			y -= speed*sin(angle);
			updateShape();
		}
	}
	
	private void createShape(){
		switch(graphic){
		case("Tracer"):
			shape = new Tracer(x,y,gettailx(10),gettaily(10));
			break;
		case("Beam"):
			shape = new Beam(x,y,gettailx(500),gettaily(500));
			break;
		case("Shell"):
			shape = new Shell(x,y,shellradius);
			break;
		}
	}
	
	private void updateShape(){
		switch(graphic){
		case("Tracer"):
			shape = new Tracer(x,y,gettailx(10),gettaily(10));
			break;
		case("Beam"):
			decay -= 0.02;
			break;
		case("Shell"):
			shape = new Shell(x,y,shellradius);
		}
	}
	
	public float gettailx(float length){return x - length*cos(angle);}	
	public float gettaily(float length){return y - length*sin(angle);}
	public float sin(float f){return (float) Math.sin(f);}
	public float cos(float f){return (float) Math.cos(f);}
	public Shape getShape(){return shape;}
	public String getGraphic(){return graphic;}
	public Color getColor(){return color;}
	public void setInactive(){alive = false;}
	public boolean isAlive(){return alive;}

	public void hit(Enemy e) {
		if(decay > 0.5f){
			e.damage(DAMAGE, graphic.contains("Beam"));
		}else if(decay <= 0){
			Play.removeBullet(this);
		}
		
		if(!graphic.contains("Beam")){
			Play.removeBullet(this);
		}
		
		
		
	}	
	
}

class Tracer extends Line{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Tracer(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
	}
}

class Beam extends Line{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public Beam(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
	}
}

class Shell extends Circle{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	public Shell(float centerPointX, float centerPointY,
			float radius) {
		super(centerPointX, centerPointY, radius);
		// TODO Auto-generated constructor stub
	}
}
