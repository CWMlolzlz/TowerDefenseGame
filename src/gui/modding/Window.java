package gui.modding;

import game.Play;
import gui.Panel;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import resources.Enemy;
import resources.Turret;
import resources.types.TurretType;

public class Window extends Panel{

	static Turret turret;
	EditableEnemy enemy;
	
	TurretType tdata = new TurretType();
	
	public Window(float x, float y, float w, float h) {
		super(x, y, w, h);
		Play.enemies.add(new Enemy(x+50,y+30, Play.EDATA.getEnemyTypes().get(0)));
		preview();
	}	
	
	public void draw(Graphics g){
		g.draw(shape);
		g.fill(new Circle(turret.x,turret.y,turret.radius));
		g.draw(Play.enemies.get(0).getShape());
	}
	
	public void update(){
		turret.fire();
	}
	
	public void preview(){
		Specs.updateTurretTypeData();
		//TurretType tt = Specs.ttype;
		turret = new EditableTurret(Specs.ttype);
		turret.updatePlacement(x + 50,y + 50);
		Play.enemies.get(0).x = x + 60;
		Play.enemies.get(0).y = y + 60;
	}
}
