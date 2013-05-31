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
	
	public TurretType(String[] line){//optimise graphic and color datatypes
		ID = Integer.parseInt(line[0]); //fix line reading to exclude tabs
		NAME = line[1];
		COST = Integer.parseInt(line[2]);
		bDamage = Integer.parseInt(line[3]);
		bRange = Integer.parseInt(line[4]);
		bRateOfFire = Float.parseFloat(line[5]);
		numofBULLETS = Integer.parseInt(line[6]);
		BULLETTYPE = Integer.parseInt(line[7]);
		colornumbers = line[8];
		//color
		String[] colorline = colornumbers.split("-");
		
		r = Integer.parseInt(colorline[0]);
		g = Integer.parseInt(colorline[1]);
		b = Integer.parseInt(colorline[2]);
		COLOR = new Color(r,g,b);
	}	
}