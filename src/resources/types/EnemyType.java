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
	
	public EnemyType(int id, String name, int health, int shield, float speed, float regen, int reward, int cost){
		ID = id;
		NAME = name;
		
		HEALTH = health;
		SHIELD = shield;
		SPEED = speed;
		REGEN = regen;
		
		REWARD = reward;
		COST = cost;
	}
	
}