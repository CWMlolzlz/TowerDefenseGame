package resources;

import game.Play;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

import resources.data.TurretData;
import resources.types.BulletType;
import resources.types.TurretType;

public class Turret {

	protected static final TurretData tdata = Play.TDATA;
	protected static final float splitshot = 0.2f;
	
	public boolean placed;
	public boolean valid;
	public float x, y, radius = 12;
	protected Circle area;
	protected Circle rangecircle;
	
	protected int bDamage, bRange;
	protected float bRateOfFire;
	
	protected int damage, range;
	protected float rateoffire;
	protected Color color;
	protected int numofBullets;
	
	protected int bullettypeID;
	protected BulletType bullet;
	
	public int value;
	protected boolean active;
	
	protected int tick = 0;
	
	protected int ID = 0;
	
	public Turret(int val){
		placed = false;
		valid = false;
		area = new Circle(x,y,radius);
		value = val;
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
		rateoffire = bRateOfFire; // blah blah blah
		color = data.COLOR;
		
		
		
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

	public void fire(){
		
		if(tick >= 60){
			tick -= 60;
			
			Random generator = new Random();
			ArrayList<Enemy> enemies = Play.enemies;
			Enemy enemy = getNearestEnemy(enemies);
			if(enemy != null){
				float ex = enemy.x;
				float ey = enemy.y;
				float dx = ex - x;
				float dy = ey - y;
				int ymult = -1;
				if(ey > y){ymult = 1;}
			
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
			}
		}
		
		tick+=rateoffire;
		
		
	}
	
	private Enemy getNearestEnemy(ArrayList<Enemy> enemies){
		ArrayList<Enemy> targets = new ArrayList<Enemy>();
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			if(rangecircle.intersects(e.getShape())){
				targets.add(e);
			}
		}
		
		if(targets.size() == 0){
			return null;
		}else{
			Enemy target = targets.get(0);
			for(int i = 1; i < targets.size(); i++){				//MERGE BOTH FOR LOOPS FOR OPTIMISATION
				Enemy t = targets.get(i);
				if(target.currentsegment == t.currentsegment){
					if(target.currentdistanceonsegment < t.currentdistanceonsegment){
						target = t;
					}
				}else if(target.currentsegment < t.currentsegment){
					target = t;
				}
			}
			return target;
		}
	}

	protected void newBullet(float theta){
		Play.bullets.add(new Bullet(bullet, damage, theta,x,y));
	}

	public int getID(){return ID;}
	public int getValue(){return value;}
	public Circle getCircle(){return area;}	
	public Circle getRangeCircle(){return rangecircle;}	
	public float getX(){return x;}
	public float getY() {return y;}	
	public float getRateOfFire(){return rateoffire;}
	public boolean isActive(){return active;}
	public Color getColor(){return color;}
		
}