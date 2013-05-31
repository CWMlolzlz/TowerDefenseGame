package resources.types;

public class EnemyType{
	
	public int ID;
	public String NAME;
		
	public float HEALTH;
	public float SHIELD;
	public float SPEED;
	public float REGEN;
	
	public int REWARD;
	public int COST; //explicitly for multiplayer and level/swarm editor
	
	public EnemyType(String[] line){
		ID = Integer.parseInt(line[0]);
		NAME = line[1];
		
		HEALTH = Integer.parseInt(line[2]);
		SHIELD = Integer.parseInt(line[3]);
		SPEED = Float.parseFloat(line[4]);
		REGEN = Float.parseFloat(line[5]);
		
		REWARD = Integer.parseInt(line[6]);
		COST = Integer.parseInt(line[7]);
	}
	
}