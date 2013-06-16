package resources;

import game.Play;
import gui.LevelGUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

import resources.data.LevelData;
import resources.data.SpawnData;

public class Level_Backup{
	
	private ConfigurableEmitter ce;
	
	public LevelData leveldata;
	private ArrayList<Wave> waves;
	private ArrayList<Spawner> spawners = new ArrayList<Spawner>();
	
	public static short gamestage;
	public static short BREAK = 0, WAVE = 1, COMPLETE = 2;
	private int breaktick = 0, breakTimeLimit = 300;
	
	public String levelname;
	public Polygon path = new Polygon(); //path is used strictly for drawing
	
		
	public int maximumWaves;
	public static int currentWave = 1;
	private int enemiesInWave;
	public static int enemiesKilled;
	public static LevelGUI LGUI;
	private Wave wave;
	
	public static int delay = 0;
		
	public void loadLevel(String levelpath){
		leveldata = new LevelData(levelpath);
		
		waves = leveldata.getWaveData();
		maximumWaves = waves.size();
		
		Play.enemies.clear();
		Play.turrets.clear();
				
		//create Path
		Play.pathPoints = leveldata.getPath();
		for(int i = 0; i < Play.pathPoints.size(); i++){
			PathPoint PathPoint = Play.pathPoints.get(i);
			path.addPoint(PathPoint.x, PathPoint.y);
		}
		path.setClosed(false);
		
		Play.edges = leveldata.getEdges();
		
		try{
			ce = ParticleIO.loadEmitter(new File("data/particles/glow.xml"));
			float[] p = path.getPoint(1);
			ce.setPosition(p[0]-2, p[1]);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		LGUI = new LevelGUI(Play.gamecont.getWidth(), Play.gamecont.getHeight());
		LGUI.setProgressBar(breakTimeLimit);
		LGUI.setLevelProgressBar(maximumWaves);
		
	}
	
	public void update(){
		if(delay == 60){delay = 0;}
		//wave
		if(gamestage == WAVE){
			waveStep();
		}else if(gamestage == BREAK){
			breaktick++;
			LGUI.updateWaveProgressBar(false);
			if(breaktick == breakTimeLimit){
				breaktick = 0;
				startWave();
			}
		}
		
		//make turrets shoot
		for(int i = 0; i < Play.turrets.size(); i++){
			Turret turret = Play.turrets.get(i);
			
			if(turret.placed == true){
				turret.fire();
			}
		}
		//AI step
		AIStep();
		
		LGUI.update();
		
		delay++;
	}
	
	private void AIStep(){
		for(int i = 0; i < Play.enemies.size(); i++){
			Enemy e = Play.enemies.get(i);
			e.step();
		}
		
		for(int j = 0; j < Play.bullets.size(); j++){
			Bullet b = Play.bullets.get(j);
			b.step();
			for(int k = 0; k < Play.enemies.size(); k++){
				Enemy e = Play.enemies.get(k);
				Shape s = b.getShape();
				if(e.getShape().intersects(s)){
					b.hit(e);
				}
			}
		}
	}
	
	public void startWave(){
		spawners.clear();
		gamestage = WAVE;
		enemiesKilled = 0;
		wave = waves.get(currentWave-1);
		enemiesInWave = 0;
		for(int s = 0; s < wave.spawnData.size(); s++){
			SpawnData sd = wave.spawnData.get(s);
			enemiesInWave += sd.QUANTITY;
			spawners.add(new Spawner(sd));
		}
		LGUI.setProgressBar(enemiesInWave);
	}
	
	private void waveStep(){
		
		//credit interest
		creditInterest();
		if(enemiesKilled == enemiesInWave){
			
			if(currentWave == maximumWaves){
				gamestage = COMPLETE;
				completeLevel();
			}else{
				LGUI.setProgressBar(breakTimeLimit);
				gamestage = BREAK;
			}
			LGUI.updateLevelProgress();
			currentWave++;
		}else{
			for(int i = 0; i < spawners.size(); i++){
				Spawner s = spawners.get(i);
				s.update();
				if(s.isEnabled()){
					if(s.doSpawn() != null){
						Play.enemies.add(new Enemy(Play.pathPoints,s.enemytype));
					}
				}else{
					spawners.remove(s);
				}
			}
		}
	}
	
	private void completeLevel() {
				
	}

	private void creditInterest(){
		Play.credits *= 1.0001f;
	}

	public static void enemyKilled(Enemy e){
		Play.enemies.remove(e);
		enemiesKilled++;
		LGUI.updateWaveProgressBar(false);
	}	
	
	public static void enemyReachedEnd(Enemy e){
		Play.enemies.remove(e);
		LGUI.updateWaveProgressBar(true);
	}
	public static void removeTurret(Turret t){Play.turrets.remove(t);}
	public static void removeBullet(Bullet b){Play.bullets.remove(b);}
	
	public Shape getPath(){return path;}
	public static ArrayList<Enemy> getEnemies(){return Play.enemies;}
	public static ArrayList<Turret> getTurrets(){return Play.turrets;}
	public static ArrayList<Bullet> getBullets() {return Play.bullets;}

	public static void draw(Graphics g) {
		LGUI.draw(g);
	}

	
}

