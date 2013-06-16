package resources.data;

public class SpawnData{
	
	public int ENEMYID, QUANTITY, DELAY;
	public int INTERVAL;
	
	public SpawnData(int id, int q, int i, int d){
		ENEMYID = id;
		QUANTITY = q;
		INTERVAL = i;
		DELAY = d;
	}
	
}