package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

public class TurretData{
	
	private ArrayList<TurretType> turrettypes = new ArrayList<TurretType>(); // in the form of a line read from file
	private ArrayList<UpgradeBranch> upgrades = new ArrayList<UpgradeBranch>(); // in the form of a line read from file
	private ArrayList<BulletType> bullettypes = new ArrayList<BulletType>(); // form of a line read from file
	
	public TurretData(){
		loadUpgrades();
	}
	
	private void loadUpgrades(){
		try{
			Scanner scanner = new Scanner(new File("data/turretdata.DATA"));
			loadTurretTypes(scanner);
			loadUpgradeBranches(scanner);
			loadBulletTypes(scanner);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}		
	}
	
	private void loadTurretTypes(Scanner sc){//optimise load functions to take ArrayList to add to and String to look for
		
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<TURRETDATA>")){
				break;
			}
		}
		sc.nextLine();//skips layout info
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</TURRETDATA>")){
				break;
			}else{
				String[] splitline = line.split("\\t+");
				turrettypes.add(new TurretType(splitline));
			}
		}
	}
	
	private void loadUpgradeBranches(Scanner sc){//optimise load functions to take ArrayList to add to and String to look for
		
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<UPGRADES>")){
				break;
			}
		}
		sc.nextLine();//skips layout info
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</UPGRADES>")){
				break;
			}else{
				String[] splitline = line.split("\\t+");
				upgrades.add(new UpgradeBranch(splitline));
			}
		}
		
	}
	
	private void loadBulletTypes(Scanner sc){//optimise load functions to take ArrayList to add to and String to look for
		
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<BULLETTYPES>")){
				break;
			}
		}
		sc.nextLine(); //skips layout info
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</BULLETTYPES>")){
				break;
			}else{
				String[] splitline = line.split("\\t+");
				bullettypes.add(new BulletType(splitline));
			}
		}
		
	}

	
	public UpgradeBranch getUpgradeBranches(int turretID){
		return upgrades.get(turretID);
	}
	
	public TurretType getTurretType(int ID){
		return turrettypes.get(ID);
	}
	
	public BulletType getBulletType(int bulletID){
		return bullettypes.get(bulletID);
	}
	
}

class TurretType{
	
	public int ID, COST, bDamage, bRange, numofBULLETS; //lower case 'b' stands for base
	public float bRateOfFire;
	public String NAME;
	int BULLETTYPE;
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

class UpgradeBranch{
	
	public int ID;
	private int[] branch = new int[3];
	
	public UpgradeBranch(String[] line){ //optimise
		ID = Integer.parseInt(line[0]);
		branch[0] = Integer.parseInt(line[1]);
		branch[1] = Integer.parseInt(line[2]);
		branch[2] = Integer.parseInt(line[3]);
	}
	
	public int getBranch(int index){
		return branch[index];
	}
	
}

class BulletType{
	
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
