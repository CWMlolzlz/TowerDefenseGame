package resources.types;

import org.newdawn.slick.Color;

public class TurretType{
	
	public int ID, COST, bDamage, bRange, numofBULLETS; //lower case 'b' stands for base
	public float bRateOfFire;
	public String NAME;
	public int BULLETTYPE;
	public Color COLOR;
	
	private String colornumbers;
	private int r,g,b;
	
	public TurretType(int id, String name, int cost, int damage, int range, int rof, int bullets, int bulletID, String colornumbers){//optimise graphic and color datatypes
		ID = id;
		NAME = name;
		COST = cost;
		bDamage = damage;
		bRange = range;
		bRateOfFire = rof;
		numofBULLETS = bullets;
		BULLETTYPE = bulletID;
		//color
		String[] colorline = colornumbers.split("-");
		
		r = Integer.parseInt(colorline[0]);
		g = Integer.parseInt(colorline[1]);
		b = Integer.parseInt(colorline[2]);
		COLOR = new Color(r,g,b);
	}	
}