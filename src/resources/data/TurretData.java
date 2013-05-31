package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import resources.types.BulletType;
import resources.types.TurretType;
import resources.types.UpgradeBranch;

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






