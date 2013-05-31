package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import resources.types.EnemyType;

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
				String[] splitline = line.split("\\s+");
				etypes.add(new EnemyType(splitline));
			}
		}
	}
	
	public ArrayList<EnemyType> getEnemyTypes(){return etypes;}
	
}
