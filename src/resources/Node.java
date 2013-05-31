package resources;

import java.util.ArrayList;

public class Node{
	public float x,y;
	public Node(float newx, float newy){
		x = newx;
		y = newy;
	}	
}

class SpawnData{
	
	public int ENEMYID, QUANTITY, DELAY;
	public int INTERVAL;
	
	public SpawnData(int id, int q, int i, int d){
		ENEMYID = id;
		QUANTITY = q;
		INTERVAL = i;
		DELAY = d;
	}
	
}
