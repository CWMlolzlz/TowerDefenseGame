package resources;

import game.Play;

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
		if(tick == delay && canSpawn == false){
			canSpawn = true;
			tick = 0;
		}
		
		if(canSpawn){
			if(numSpawned == quantity){
				enabled = false;
			}
		}
		tick++;
		
	}
	
	public EnemyType doSpawn(){
		if(tick%interval == 0 && enabled && canSpawn){
			System.out.println(tick);
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
