package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EnemyData{

	private ArrayList<EnemyType> etypes = new ArrayList<EnemyType>();
	
	public EnemyData(){
		try{
			Scanner scanner = new Scanner(new File("data/enemydata.DATA"));
			loadEnemyTypes(scanner);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
	}
	
	private void loadEnemyTypes(Scanner sc){
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<ENEMYTYPE>")){
				break;
			}
		}
		sc.nextLine();//skips layout info
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</ENEMYTYPE>")){
				break;
			}else{
				String[] splitline = line.split("\\t+");
				etypes.add(new EnemyType(splitline));
			}
		}
	}
	
	public ArrayList<EnemyType> getEnemyTypes(){return etypes;}
	
}

class EnemyType{
	
	int ID;
	String NAME;
		
	float HEALTH;
	float SHEILD;
	float SPEED;
	float REGEN;
	
	int REWARD;
	int COST; //explicitly for multiplayer and level/swarm editor
	
	public EnemyType(String[] line){
		ID = Integer.parseInt(line[0]);
		NAME = line[1];
		
		HEALTH = Integer.parseInt(line[2]);
		SHEILD = Integer.parseInt(line[3]);
		SPEED = Float.parseFloat(line[4]);
		REGEN = Float.parseFloat(line[5]);
		
		REWARD = Integer.parseInt(line[6]);
		COST = Integer.parseInt(line[7]);
	}
	
}
