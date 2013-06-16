package game;

import gui.LevelGUI;
import gui.PauseMenu;
import gui.menus.MenuButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.Bullet;
import resources.Button;
import resources.Enemy;
import resources.Explosion;
import resources.PathPoint;
import resources.Spark;
import resources.Spawner;
import resources.Turret;
import resources.TurretGUI;
import resources.Wave;
import resources.data.EnemyData;
import resources.data.LevelData;
import resources.data.SpawnData;
import resources.data.TurretData;

public class Play extends BasicGameState{

	
	public LevelData leveldata;
	
	public static ArrayList<Wave> waves;
	public static ArrayList<Spawner> spawners = new ArrayList<Spawner>();
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //optimise make static
	public static ArrayList<Turret> turrets = new ArrayList<Turret>(); //optimise make static
	public static ArrayList<PathPoint> pathPoints = new ArrayList<PathPoint>(); //optimise make static
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>(); //optimise make static
	public static ArrayList<Shape> edges = new ArrayList<Shape>();
	public static ArrayList<Spark> sparks = new ArrayList<Spark>();
	public static ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	
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
	
	public static final int RESUME = 0, QUIT = 9;
	public static TurretData TDATA = new TurretData();
	public static EnemyData EDATA = new EnemyData();
	public static ParticleSystem ps;
	public static File psXML = new File("data/particles/spark.xml");
	public static ConfigurableEmitter ce;
	
	
	private final int NONE = 0, BUILDINGTURRET = 1, EDITINGTURRET = 2, HOVERTURRET = 3;
	
	public static int screenWidth, screenHeight;
	public static GameContainer gamecont;
	public static StateBasedGame statebasedgame;
	
	//input
	public Input i;
	private PauseMenu pmenu;
	
	public static Circle mp = new Circle(Mouse.getX(), Mouse.getY(), 3f);
	
	private TurretGUI tGUI = null;
	private Turret tOnHand = null;
	private Turret selectedTurret = null;
	
	public static int selectstatus;
	public boolean paused = false;
	
	public static int baseHealth = 100;
	public static float credits = 50;
	
	//public Level level = new Level();
	
	//onscreen enitites
		
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

		Mouse.setGrabbed(true);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		gamecont = gc;
		statebasedgame = sbg;
		try {
			ce = ParticleIO.loadEmitter(psXML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ps = new ParticleSystem(new Image("/data/particles/particle.png"), 10000);
		ps.setRemoveCompletedEmitters(true);
		ps.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		ps.setPosition(0,0);
			
		//input
		i = gc.getInput();
		//font = new AngelCodeFont("fonts/squared_12.fnt", new Image("fonts/squared_12_0.png"));
		screenWidth = gc.getWidth();
		screenHeight = gc.getHeight();
		
		enemies.clear();
		pathPoints.clear();
		turrets.clear();
		bullets.clear();
		TDATA.loadUpgrades();
		EDATA.loadEnemyTypes();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setAntiAlias(true);
				
		//debug info
		g.drawString("Select state:" + selectstatus, 10, 30);
		g.drawString("Number of Turrets:" + turrets.size(), 10, 50);
		g.drawString("Number of Enemies:" + enemies.size(), 200,30 );
		g.drawString("Number Of Emitters:" + ps.getEmitterCount(), 200, 50);
		g.drawString("Number of Particles:" + ps.getParticleCount(), 400, 30);
		g.drawString("Current Wave:" + currentWave, 400, 50);

		g.drawString("MEM in use: " + (Runtime.getRuntime().totalMemory()/1000000) + "(MB)", 10, 580);
				
		//path
		//g.setColor(Color.cyan);
		//g.draw(level.getPath());
		g.setColor(Color.blue);
		for(int i = 0; i < edges.size(); i++){
			g.draw(edges.get(i));
		}
				
		//enemies
		g.setColor(Color.white);
 		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			g.setColor(new Color(0,196,255));
			g.fill(e.getShieldBarShape());
			g.setColor(e.getHealthBarColor());
			g.fill(e.getHealthBarShape());
			g.setColor(Color.white);
			g.draw(e.getShape());
		}
		
		//built turrets
		g.setColor(Color.yellow);
		for(int j = 0; j < turrets.size(); j++){
			Turret t = turrets.get(j);
			g.setColor(t.getColor());
			g.draw(t.getCircle());
		}
		g.setColor(Color.green);
		
		//selectedturret
		if(selectedTurret != null){
			g.draw(selectedTurret.getRangeCircle());
		}
		
		//draw bullets
		for(int b = 0; b < bullets.size(); b++){
			Bullet bullet = bullets.get(b);
			Color c = new Color(bullet.getColor());
			float alpha = bullet.decay;
			g.setColor(new Color(c.r,c.g,c.b,alpha));
			g.draw(bullet.getShape());
		}
		
		LGUI.draw(g);
		
		g.setLineWidth(1);
		
		//turret on hand
		if(tOnHand != null){
			if(tOnHand.valid){
				g.setColor(Color.green);
			}else{
				g.setColor(Color.red);
			}
			g.draw(tOnHand.getCircle());
		}
		
		if(ps != null){
			ps.render();
		}
		
		
		
		//turret gui
		if(tGUI != null){
			g.setColor(Color.black);
			g.fill(tGUI.getShape());
			g.setColor(Color.orange);
			g.draw(tGUI.getShape());
			g.setColor(Color.gray);
			ArrayList<Button> buttons = tGUI.getButtons();
			for(int b = 0; b < buttons.size(); b++){
				Button button = buttons.get(b);
				if(button.getCost() > credits){
					g.setColor(Color.red);
				}else{
					g.setColor(Color.gray);
				}
				g.draw(button.getShape());
				g.drawString(button.getText(), button.getTextX(), button.getTextY());
			}
		}
		
		//cursor
		if(pmenu != null){
			pmenu.draw(g);
		}else{
			g.setColor(Color.white);
			g.draw(mp);
		}
			
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		
		mp = new Circle(Mouse.getX(), gc.getHeight()-Mouse.getY(), 3f);
		if(!paused){
		Turret collidingturret = getCollidingTurret(mp);
		//TURRET PLACING POSITION
		if(tOnHand != null){
			tOnHand.updatePlacement(mp.getCenterX(), mp.getCenterY());
			selectedTurret = tOnHand;
			if(getCollidingTurret(tOnHand.getCircle()) != null || isCollidingPath(tOnHand.getCircle())){
				tOnHand.valid = false;
			}else{
				tOnHand.valid = true;
			}
		}
		//checks mouse position
		else if(selectstatus == EDITINGTURRET){						//&& tGUI != null){
			//mouse is editing
		}else if(collidingturret != null){
			selectstatus = HOVERTURRET;
			selectedTurret = collidingturret;
		}else{
			selectstatus = NONE;
			selectedTurret = null;
		}
				
		//AI
		gameUpdate();
		ps.update(delta);
		for(int s = 0; s < sparks.size(); s++){
			sparks.get(s).updateDecay();
		}
		for(int e = 0; e < explosions.size(); e++){
			explosions.get(e).updateDecay();
		}
		}
		
	}
	
	
	
	

	
	public void gameUpdate(){
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
	public static void removeTurret(Turret t){turrets.remove(t);}
	public static void removeBullet(Bullet b){bullets.remove(b);}
	
	public Shape getPath(){return path;}
	//public static ArrayList<Enemy> getEnemies(){return enemies;}
	//public static ArrayList<Turret> getTurrets(){return turrets;}
	//public static ArrayList<Bullet> getBullets() {return bullets;}

	public static void draw(Graphics g) {
		LGUI.draw(g);
	}

	



	
	public void leftClick(StateBasedGame sbg) {
		
		if(paused){
			clickedPauseMenu();
		}else{
			clickedLevel();
		}
	}
	
	private void clickedPauseMenu() {
		MenuButton mb = pmenu.getClickedButton(new Point(mp.getCenterX(),mp.getCenterY()));
		if(mb != null){
			int e = mb.getEvent();
			switch(e){
			case(RESUME):
				togglePause();
				break;
			case(QUIT):
				Launch.quit();
			}
		}

	}

	private void clickedLevel(){
		switch(selectstatus){
		case(NONE): //NONE ==> BUILDING
			tOnHand = new Turret();
			selectstatus = BUILDINGTURRET;
			break;
		case(BUILDINGTURRET): //BUILDING ==> NONE
			int tcost = getNextTurretCost();
			if(tOnHand.valid && credits >= tcost){ //optimise detection for both cases
				//can place turret
				placeTurret(tOnHand);
				credits -= tcost;
			}else{
				//cant place turret
				selectstatus = NONE;
			}
			tOnHand = null;
			selectstatus = NONE;
			break;
		case(HOVERTURRET): //HOVERING ==> EDITING
			
			if(tOnHand == null){
				selectstatus = EDITINGTURRET;
				selectedTurret = getCollidingTurret(mp);
				tGUI = new TurretGUI(selectedTurret, gamecont);
			}
			break;
		case(EDITINGTURRET):
			selectObject();
			break;
		}
	}
	
	public void selectObject(){
		Turret ct = getCollidingTurret(mp);
		if(isColliding(mp, tGUI.getShape())){
			//clicked GUI
			
			Button button = tGUI.click(mp);
			if(button != null){
				selectedTurret = tGUI.getTarget();
				int cost = button.getCost();
				int eventval = button.getEvent();
				switch(eventval){
					case(-1):
						sellTurret(tGUI.getTarget());
						credits -= cost;
						tGUI = null;
						selectedTurret = null;
						selectstatus  = NONE;
						break;
					default:
						if(credits - cost >= 0){
							tGUI.getTarget().upgrade(eventval);
							credits -= cost;
							tGUI.loadUpgrades();
						}else{
							//cannot afford upgrade
						}	
						break;
				}
							
			}
		}else if(ct != null && ct != tGUI.getTarget()){
			//clicked turret not associated with GUI
			selectedTurret = ct;
			tGUI = new TurretGUI(ct, gamecont);
		}else{
			//creates GUI, none exist
			tGUI = null;
			selectstatus = NONE;
		}
	}
	
	public Turret getCollidingTurret(Shape object){
		for(int a = 0; a < turrets.size(); a++){ //optimise
			Turret turret = turrets.get(a);
			Circle turretcircle = turret.getCircle();
			if(turretcircle.intersects(object) || turretcircle.contains(object)){
				return turret;
			}
		}
		return null;
	}
	
	public boolean isClickingGUI(){
		if(tGUI.getShape().contains(mp)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean isCollidingPath(Shape object){
		if(object.intersects(getPath())){
			return true;		
		}else{
			for(int i = 0; i < edges.size(); i++){
				if(edges.get(i).intersects(object)){
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean isColliding(Shape sa, Shape sb){
		if(sa.intersects(sb) || sb.intersects(sa)){ 
			return true;
		}else{
			return false;
		}
	}
	
	public void rightClick(Launch launch) {
		
		switch(selectstatus){
		case(NONE):
			//right click with nothing on hand
			break;
		case(BUILDINGTURRET):
			tOnHand = null;
			selectstatus = NONE;
			break;
		case(EDITINGTURRET):
			tGUI = null;
			selectstatus = NONE;
		}
	}
	
	public void placeTurret(Turret turret){
		turrets.add(turret);
		turret.place();
		LevelGUI.updateTurretCostText();
	}
	
	public void sellTurret(Turret target) {
		turrets.remove(target);
		LevelGUI.updateTurretCostText();
	}
	
	public static int getNextTurretCost() {
		return (int) (30*(Math.pow(1.5, turrets.size())));
	}
	
	@Override
	public int getID(){return 1;}
	
	
	public static void enterState(){statebasedgame.enterState(0);}
	public static void pay(int val){credits += val;}

	public void togglePause(){
		paused = !paused;
		Mouse.setGrabbed(!Mouse.isGrabbed());
		if(paused){
			pmenu = new PauseMenu();
		}else{
			pmenu = null;
		}
	}
}
