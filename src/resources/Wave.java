package resources;

import java.util.ArrayList;

public class Wave{
	
	ArrayList<SpawnData> spawnData = new ArrayList<SpawnData>();
		
	public void addSpawnData(int id, int q, int f, int d){
		spawnData.add(new SpawnData(id,q,f,d));
	}
}