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
	
	public BulletType(String[] line){
		ID = Integer.parseInt(line[0]);
		GRAPHIC = line[1]; //GRAPHIC can be replaced with ID, may keep GRAPHIC for modding
		String[] colorline = line[2].split("-");
		
		int r = Integer.parseInt(colorline[0]);
		int g = Integer.parseInt(colorline[1]);
		int b = Integer.parseInt(colorline[2]);
		COLOR = new Color(r,g,b);
		
		SPEED = Integer.parseInt(line[3]);
		ACCURACY = Float.parseFloat(line[4]);
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
