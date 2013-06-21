package gui.modding;

import java.util.Random;

import org.newdawn.slick.geom.Circle;

import resources.Turret;
import resources.types.TurretType;

public class EditableTurret extends Turret{

	public EditableTurret(TurretType data){
		super(data.COST);
		numofBullets = data.numofBULLETS;
		bDamage = data.bDamage;
		bRange = data.bRange;
		bRateOfFire = data.bRateOfFire;
		range = bRange; //make return statement range+bRange
		damage = bDamage; //keep base and offest independant;
		rateoffire = bRateOfFire; // blah blah blah
		color = data.COLOR;
		value = (int) (data.COST*.8);
		
		bullettypeID = data.BULLETTYPE;
		bullet = tdata.getBulletType(bullettypeID);
		area =  new Circle(area.getCenterX(),area.getCenterY(),radius);
		rangecircle = new Circle(area.getCenterX(),area.getCenterY(),range);
	}
	
	public void fire(){
		
		if(tick >= 60){
			tick -= 60;
			
			Random generator = new Random();
			float ex = x + 50;
			float ey = y + 50;
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
	
		
		tick+=rateoffire;
		
		
	}
	
}
