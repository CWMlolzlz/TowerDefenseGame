package resources;

import game.Play;
import gui.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

import resources.data.LevelData;

public class Level {
	
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
	public int currentWave = 1;
	private int enemiesInWave;
	public static int enemiesKilled;
	public static ProgressBar pb;
	private Wave wave;
	
	public static int delay = 0;
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //optimise make static
	private static ArrayList<Turret> turrets = new ArrayList<Turret>(); //optimise make static
	private static ArrayList<PathPoint> PathPoints = new ArrayList<PathPoint>(); //optimise make static
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //optimise make static
	public static ArrayList<Shape> edges = new ArrayList<Shape>();
		
	public void loadLevel(String levelpath){
		enemies.clear();
		PathPoints.clear();
		turrets.clear();
		bullets.clear();
		leveldata = new LevelData(levelpath);
		pb = new ProgressBar(breakTimeLimit);
		waves = leveldata.getWaveData();
		maximumWaves = waves.size();
		
		//create Path
		PathPoints = leveldata.getPath();
		for(int i = 0; i < PathPoints.size(); i++){
			PathPoint PathPoint = PathPoints.get(i);
			path.addPoint(PathPoint.x, PathPoint.y);
		}
		path.setClosed(false);
		
		edges = leveldata.getEdges();
		
		try{
			ce = ParticleIO.loadEmitter(new File("data/particles/glow.xml"));
			float[] p = path.getPoint(1);
			ce.setPosition(p[0]-2, p[1]);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void update(){
		if(delay == 60){delay = 0;}
		//wave
		if(gamestage == WAVE){
			waveStep();
		}else if(gamestage == BREAK){
			breaktick++;
			pb.update(1);
			if(breaktick == breakTimeLimit){
				breaktick = 0;
				startWave();
			}
		}
		
		//make turrets shoot
		for(int i = 0; i < turrets.size(); i++){
			Turret turret = turrets.get(i);
			
			if(turret.placed == true){
				turret.fire();
			}
		}
		//AI step
		AIStep();
		
		delay++;
	}
	
	private void AIStep(){
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.step();
		}
		
		for(int j = 0; j < bullets.size(); j++){
			Bullet b = bullets.get(j);
			b.step();
			for(int k = 0; k < enemies.size(); k++){
				Enemy e = enemies.get(k);
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
		pb = new ProgressBar(enemiesInWave);
	}
	
	private void waveStep(){
		
		//credit interest
		creditInterest();
		if(enemiesKilled == enemiesInWave){
			if(currentWave == maximumWaves){
				//Play.enterState();
				//completeLevel();
				gamestage = COMPLETE;
				
			}else{
				pb = new ProgressBar(breakTimeLimit);
				gamestage = BREAK;
				currentWave++;
			}
		}else{
			for(int i = 0; i < spawners.size(); i++){
				Spawner s = spawners.get(i);
				s.update();
				if(s.isEnabled()){
					if(s.doSpawn() != null){
						enemies.add(new Enemy(PathPoints,s.enemytype));
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

	public void placeTurret(Turret turret) {
		turrets.add(turret);
		turret.place();
	}
	
	public void sellTurret(Turret target) {
		turrets.remove(target);
	}
	
	public static void removeEnemy(Enemy e){enemies.remove(e);enemiesKilled++;pb.update(1);}	
	public static void removeTurret(Turret t){turrets.remove(t);}
	public static void removeBullet(Bullet b){bullets.remove(b);}
	
	public Shape getPath(){return path;}
	//public Shape getEdgeA(){return pathedgeA;}
	//public Shape getEdgeB(){return pathedgeB;}
	public static ArrayList<Enemy> getEnemies(){return enemies;}
	public static ArrayList<Turret> getTurrets(){return turrets;}
	public static ArrayList<Bullet> getBullets() {return bullets;}

	
}

