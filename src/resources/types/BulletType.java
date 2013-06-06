package resources.types;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

public class BulletType{
	
	public int ID, SPEED;
	public String GRAPHIC;
	public Shape SHAPE;
	public float ACCURACY;
	public Color COLOR;
	
	public BulletType(int id, String gName, String color, int speed, float accuracy){
		ID = id;
		GRAPHIC = gName; //GRAPHIC can be replaced with ID, may keep GRAPHIC for modding
		String[] colorline = color.split("-");
		
		int r = Integer.parseInt(colorline[0]);
		int g = Integer.parseInt(colorline[1]);
		int b = Integer.parseInt(colorline[2]);
		COLOR = new Color(r,g,b);
		
		
		SPEED = speed;
		ACCURACY = accuracy;
		
		switch(GRAPHIC){
		case("Line"):
			SHAPE = new Line(0,0,0,20);
			break;
		case("Beam"):
			//SHAPE = new RoundedRectangle();
			break;
		case("Drops"):
			//SHAPE = new Polygon();
			break;
		}
	}

	
}
