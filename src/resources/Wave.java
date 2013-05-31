package resources;

import java.util.ArrayList;

public class Wave{
	
	ArrayList<SpawnData> spawnData = new ArrayList<SpawnData>();
		
	public void addSpawnData(String[] line){
		int id = Integer.parseInt(line[0]);
		int q = Integer.parseInt(line[1]);
		
		int i = Integer.parseInt(line[2]);
		int d = Integer.parseInt(line[3]);
		
		spawnData.add(new SpawnData(id,q,i,d));
	}
}