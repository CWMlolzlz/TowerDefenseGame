package gui.modding;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import resources.Enemy;
import resources.types.EnemyType;

public class EditableEnemy extends Enemy{

	float x,y;
	
	public EditableEnemy(float f, float g, EnemyType etype) {
		super(f, g, etype);
		x = f;
		y = g;
	}

	public Shape getShape(){
		return new Circle(x,y,radius);
	}
	
}
