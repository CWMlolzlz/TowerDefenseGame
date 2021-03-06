package resources;

import game.Play;
import resources.data.SpawnData;
import resources.types.EnemyType;

public class Spawner {
		
	public EnemyType enemytype;
	private int quantity, delay;
	private int interval;
	
	private int tick = 0;
	private int numSpawned = 0;
	
	private boolean canSpawn = false;
	private boolean enabled = true;

	public Spawner(SpawnData sd){
		enemytype = Play.EDATA.getEnemyTypes().get(sd.ENEMYID);
		quantity = sd.QUANTITY;
		interval = sd.INTERVAL;
		delay = sd.DELAY;
	}
	
	public void update(){
		if(tick == delay && !canSpawn){
			canSpawn = true;
			tick = 0;
		}else if(canSpawn){
			if(numSpawned == quantity){
				enabled = false;
			}
		}
		tick++;
		
	}
	
	public EnemyType doSpawn(){
		if((tick%interval == 0)&& canSpawn){
			numSpawned++;
			return enemytype;
		}else{
			return null;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}	
}
