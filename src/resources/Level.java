package resources;

import game.Play;
import gui.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

public class Level {
	
	private ConfigurableEmitter ce;
	
	public LevelData leveldata;
	private ArrayList<Wave> waves;
	private ArrayList<Spawner> spawners = new ArrayList<Spawner>();
	
	public static int gamestage;
	public static int BREAK = 0, WAVE = 1, COMPLETE = 2;
	private int breaktick = 0, breakTimeLimit = 300;
	
	public String levelname;
	public Polygon path = new Polygon(); //path is used strictly for drawing
	public Polygon pathedgeA = new Polygon();
	public Polygon pathedgeB = new Polygon();
		
	public int maximumWaves;
	public int currentWave = 1;
	private int enemiesInWave;
	public static int enemiesKilled;
	public static ProgressBar pb;
	//private boolean waveStarted = false;
	//private boolean waveComplete = false;
	private Wave wave;
	
	public static int delay = 0;
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //optimise make static
	private static ArrayList<Turret> turrets = new ArrayList<Turret>(); //optimise make static
	private static ArrayList<Node> nodes = new ArrayList<Node>(); //optimise make static
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //optimise make static
	
	public void newLevel(){
		enemies.clear();
		nodes.clear();
		turrets.clear();
		bullets.clear();
	}
	
	public void loadLevel(String levelnum){
		leveldata = new LevelData(levelnum);
		pb = new ProgressBar(breakTimeLimit);
		waves = leveldata.getWaveData();
		maximumWaves = waves.size();
		
		//create Path
		nodes = leveldata.getPath();
		for(int i = 0; i < nodes.size(); i++){
			Node node = nodes.get(i);
			path.addPoint(node.x, node.y);
		}
		path.setClosed(false);
		
		ArrayList<Node> edgeA = leveldata.getEdgeA();
		for(int A = 0; A < edgeA.size(); A++){
			Node n = edgeA.get(A);
			pathedgeA.addPoint(n.x, n.y);
		}
		pathedgeA.setClosed(false);
		
		ArrayList<Node> edgeB = leveldata.getEdgeB();
		for(int B = 0; B < edgeB.size(); B++){
			Node n = edgeB.get(B);
			pathedgeB.addPoint(n.x, n.y);
		}
		pathedgeB.setClosed(false);
		
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
		shoot();
		//AI step
		AIStep();
		
		delay++;
	}
	
	private void shoot(){
		for(int i = 0; i < turrets.size(); i++){
			Turret turret = turrets.get(i);
			if(turret.placed == true){
				float rof = turret.getRateOfFire();
				if(delay%(60*(1/rof)) == 0){
					turret.fire();
				}
			}
		}
		
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
		System.out.println("new wave");
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
						enemies.add(new Enemy(nodes,s.enemytype));
					}
				}else{
					spawners.remove(s);
				}
			}
		}
	}
	
	private void completeLevel() {
		// TODO Auto-generated method stub
		
	}

	private void creditInterest(){
		float c = Play.credits;
		Play.credits *= 1.0002f;
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
	public Shape getEdgeA(){return pathedgeA;}
	public Shape getEdgeB(){return pathedgeB;}
	public static ArrayList<Enemy> getEnemies(){return enemies;}
	public static ArrayList<Turret> getTurrets(){return turrets;}
	public static ArrayList<Bullet> getBullets() {return bullets;}

	
}

