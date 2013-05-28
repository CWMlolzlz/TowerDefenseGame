package resources;

import game.Play;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Circle;

public class Turret {

	private static final TurretData tdata = Play.TDATA;
	private static final float splitshot = 0.2f;
	
	public boolean placed;
	private float x, y, radius = 12;
	private Circle area;
	private Circle rangecircle;
	
	private int bDamage, bRange;
	private float bRateOfFire;
	
	private int damage, range;
	private float rateoffire;
	
	private int numofBullets;
	
	private int bullettypeID;
	private BulletType bullet;
	
	private int value = 100;
	private boolean active;
	
	private int ID = 0;
	
	public Turret(){
		placed = false;
		area = new Circle(x,y,radius);
		
		updateSpecs();
	}

	private void updateSpecs() {
		TurretType data = tdata.getTurretType(ID);
		numofBullets = data.numofBULLETS;
		bDamage = data.bDamage;
		bRange = data.bRange;
		bRateOfFire = data.bRateOfFire;
		range = bRange; //make return statement range+bRange
		damage = bDamage; //keep base and offest independant;
		rateoffire = bRateOfFire;// blah blah blah
		
		value = (int) (data.COST*.8);
		
		bullettypeID = data.BULLETTYPE;
		bullet = tdata.getBulletType(bullettypeID);
		
		rangecircle = new Circle(area.getCenterX(),area.getCenterY(),range);
	}

	public void place(){
		placed = true;
		updateSpecs();
	}
	
	public void upgrade(int IDval){
		ID = IDval;
		updateSpecs();
	}
	
	public void updatePlacement(float newx, float newy){
		x = newx;
		y = newy;
		area = new Circle(x,y,radius);
		rangecircle = new Circle(x,y,range);
	}	

	public void fire() {
		Random generator = new Random();
		ArrayList<Enemy> enemies = Level.getEnemies();
		for(int i = 0; i < enemies.size(); i++){
			Enemy enemy = enemies.get(i);
			float ex = enemy.x;
			float ey = enemy.y;
			float dx = ex - x;
			float dy = ey - y;
			int ymult = -1;
			if(ey > y){ymult = 1;}
			if(rangecircle.intersects(enemy.getShape()) && enemy.isAlive()){
				float random = generator.nextFloat();
				float distance = (float) Math.sqrt((dx*dx) + (dy*dy));
				float angle = (float) (ymult*(Math.acos(dx/distance)) + Math.PI) + ((2*random)-1)*bullet.ACCURACY;
				
				float even = 0;
				if(numofBullets%2 == 0){
					even = 0.5f;
				}
				for(int j = 0; j< numofBullets; j++){
					newBullet(angle + (j - numofBullets/2 + even)*splitshot);
				}
				break;
			}
		}	
	}
	
	private void newBullet(float theta){
		Level.bullets.add(new Bullet(bullet, damage, theta,x,y));
	}

	public int getID(){return ID;}
	public int getValue(){return value;}
	public Circle getCircle(){return area;}	
	public Circle getRangeCircle(){return rangecircle;}	
	public float getX(){return x;}
	public float getY() {return y;}	
	public float getRateOfFire(){return rateoffire;}
	public boolean isActive(){return active;}
		
}