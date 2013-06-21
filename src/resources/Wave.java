package resources;

import java.util.ArrayList;

import resources.data.SpawnData;

public class Wave{
	
	public ArrayList<SpawnData> spawnData = new ArrayList<SpawnData>();
	
	public boolean save;
	
	public Wave(boolean s){
		save = s;
	}
	
	public void addSpawnData(int id, int q, int f, int d){
		spawnData.add(new SpawnData(id,q,f,d));
	}
}