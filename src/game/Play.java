package game;

import gui.LevelCompleteMenu;
import gui.LevelGUI;
import gui.PauseMenu;
import gui.menus.Menu;
import gui.menus.MenuButton;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.Bullet;
import resources.Button;
import resources.Enemy;
import resources.Explosion;
import resources.PathPoint;
import resources.Save;
import resources.Spark;
import resources.Spawner;
import resources.Turret;
import resources.TurretGUI;
import resources.Wave;
import resources.data.EnemyData;
import resources.data.LevelData;
import resources.data.SaveData;
import resources.data.SpawnData;
import resources.data.TurretData;

public class Play extends BasicGameState{
	
	public LevelData leveldata;
	public NetworkConnect net = new NetworkConnect();
	
	public static ArrayList<Wave> waves;
	public static ArrayList<Spawner> spawners;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Turret> turrets;
	public static ArrayList<PathPoint> pathPoints;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Shape> edges;
	public static ArrayList<Spark> sparks;
	public static ArrayList<Explosion> explosions;
	
	public static short gamestage;
	public static final int BREAK = 0, WAVE = 1, COMPLETE = 2;
	private int breaktick = 0, breakTimeLimit = 300;
	
	public String levelpath;
	public String levelname;
	public Polygon path;
			
	public int maximumWaves;
	public static int currentWave;
	private int enemiesInWave;
	public static int enemiesDone;
	public static LevelGUI LGUI;
	private Wave wave;
	public int delay = 0;
	
	public static final int RESUME = 0, RESTART = 1, NEXTLEVEL = 2, UPLOAD_SCORE = 3, SAVE = 4, LOADLASTCHECKPOINT = 5, MAINMENU = 8, QUIT = 9;
	public static TurretData TDATA;
	public static EnemyData EDATA;
	public static ParticleSystem ps;
	public static File psXML = new File("data/particles/spark.xml");
	
	public static int screenWidth, screenHeight;
	public static GameContainer gamecont;
	public static StateBasedGame statebasedgame;
	
	//input
	public Input i;
	private static Menu sidemenu;
	
	public static Circle mp = new Circle(Mouse.getX(), Mouse.getY(), 3f);
	
	private final int NONE = 0, BUILDINGTURRET = 1, EDITINGTURRET = 2, HOVERTURRET = 3;
	private TurretGUI tGUI;
	private Turret tOnHand;
	private Turret selectedTurret;
	
	public static int selectstatus;
	public boolean paused;
	
	public static int baseHealth;
	public static float credits;
	private static float score;
	
	private void reset(){
		
		waves = new ArrayList<Wave>();
		spawners = new ArrayList<Spawner>();
		bullets = new ArrayList<Bullet>();
		turrets = new ArrayList<Turret>();
		pathPoints = new ArrayList<PathPoint>();
		enemies = new ArrayList<Enemy>();
		edges = new ArrayList<Shape>();
		sparks = new ArrayList<Spark>();
		explosions = new ArrayList<Explosion>();
		
		baseHealth = 100;
		credits = 50; 
		score = 0;
		
		psXML = new File("data/particles/spark.xml");
		
		TDATA = new TurretData();
		EDATA = new EnemyData();
		
		LGUI = new LevelGUI(screenWidth, screenHeight);
		tGUI = null;
		tOnHand = null;
		selectedTurret = null;
		paused = false;
		sidemenu = null;
		
		currentWave = 1;
		delay = 0;
		breaktick = 0;
		breakTimeLimit = 300;
		gamestage = BREAK;
				
		path = new Polygon();
		
		waves.clear();
		spawners.clear();
		bullets.clear();
		turrets.clear();
		pathPoints.clear();
		enemies.clear();
		edges.clear();
		sparks.clear();
		explosions.clear();
				
		TDATA.loadUpgrades();
		EDATA.loadEnemyTypes();
	}
	
	public void loadLevel(String lp, String name){
		levelpath = lp;
		levelname = name;
		leveldata = new LevelData(levelpath);
		reset();
		net.uploadScore(100, "Connor", levelpath);
		
		waves = leveldata.getWaveData();
		maximumWaves = waves.size();
		
		//create Path
		pathPoints = leveldata.getPath();
		for(int i = 0; i < pathPoints.size(); i++){
			PathPoint PathPoint = pathPoints.get(i);
			path.addPoint(PathPoint.x, PathPoint.y);
		}
		path.setClosed(false);
		
		edges = leveldata.getEdges();
		
		ps.removeAllEmitters();
		
		LGUI = new LevelGUI(gamecont.getWidth(), gamecont.getHeight());
		LGUI.setProgressBar(breakTimeLimit);
		LGUI.setLevelProgressBar(maximumWaves);

		Mouse.setGrabbed(true);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		gamecont = gc;
		statebasedgame = sbg;
		reset();
		
		
		ps = new ParticleSystem(new Image("/data/particles/particle.png"), 10000);
		ps.setRemoveCompletedEmitters(true);
		ps.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		ps.setPosition(0,0);
		
		
		
		//input
		i = gamecont.getInput();
		//font = new AngelCodeFont("fonts/squared_12.fnt", new Image("fonts/squared_12_0.png"));
		screenWidth = gamecont.getWidth();
		screenHeight = gamecont.getHeight();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setAntiAlias(true);
		
		/**
		//debug info
		g.drawString("Select state:" + selectstatus, 10, 30);
		g.drawString("Number of Turrets:" + turrets.size(), 10, 50);
		g.drawString("Number of Enemies:" + enemies.size(), 200,30 );
		g.drawString("Number Of Emitters:" + ps.getEmitterCount(), 200, 50);
		g.drawString("Number of Particles:" + ps.getParticleCount(), 400, 30);
		g.drawString("Current Wave:" + currentWave, 400, 50);
		**/
		
		g.drawString("MEM in use: " + (Runtime.getRuntime().totalMemory()/1000000) + "(MB)", 150, 580);
				
		//path
		g.setColor(Color.cyan);
		g.draw(path);
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
			ArrayList<Button> buttons = tGUI.getButtons();
			for(int b = 0; b < buttons.size(); b++){
				Button button = buttons.get(b);
				if(button.getCost() > credits){
					g.setColor(Color.gray);
				}else{
					g.setColor(Color.green);
				}
				g.draw(button.getShape());
				g.drawString(button.getText(), button.getTextX(), button.getTextY());
			}
		}
		
		//cursor
		if(sidemenu != null){
			sidemenu.draw(g);
		}else{
			g.setColor(Color.white);
			g.draw(mp);
		}
			
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		mp = new Circle(Mouse.getX(), gc.getHeight()-Mouse.getY(), 3f);
		if(!(sidemenu instanceof PauseMenu)){
			//AI step
			gameUpdate();
			
			if(sidemenu == null){
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
				else if(selectstatus == EDITINGTURRET){
					//mouse is editing
				}else if(collidingturret != null){
					selectstatus = HOVERTURRET;
					selectedTurret = collidingturret;
				}else{
					selectstatus = NONE;
					selectedTurret = null;
				}
				LGUI.update();
				
			}
			
			ps.update(17);
			for(int s = 0; s < sparks.size(); s++){
				sparks.get(s).updateDecay();
			}
			for(int e = 0; e < explosions.size(); e++){
				explosions.get(e).updateDecay();
			}
		}
	}
	
	public void gameUpdate(){
		//wave
		if(gamestage == WAVE){
			waveStep();
		}else if(gamestage == BREAK){
			breaktick++;
			LGUI.wavepbar.update();
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
		
		
		AIStep();
		
	
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
				if(s.intersects(e.getShape()) || e.getShape().contains(s)){
					b.hit(e);
				}
			}
		}
	}
	
	public void startWave(){
		spawners.clear();
		LGUI.updateLevelProgress();
		gamestage = WAVE;
		enemiesDone = 0;
		wave = waves.get(currentWave-1);
		currentWave++;
		if(wave.save){
			makeSave();
		}
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
		if(enemiesDone == enemiesInWave){
			if(currentWave == maximumWaves){
				gamestage = COMPLETE;
				completeLevel();
			}else{
				LGUI.setProgressBar(breakTimeLimit);
				gamestage = BREAK;
			}
			
		}else{
			for(int i = 0; i < spawners.size(); i++){
				Spawner s = spawners.get(i);
				if(s.isEnabled()){
					if(s.doSpawn() != null){
						LGUI.wavepbar.spawn();
						enemies.add(new Enemy(pathPoints,s.enemytype));
					}
				}else{
					spawners.remove(s);
				}
				s.update();
			}
		}
	}
	
	private void completeLevel(){
		Mouse.setGrabbed(false);
		sidemenu = new LevelCompleteMenu("Level Complete!",true);
	}
	
	private static void failedLevel(){
		Mouse.setGrabbed(false);
		sidemenu = new LevelCompleteMenu("Level Failed!",false);
	}

	private void creditInterest(){
		credits *= 1.00012f;
	}

	public static void enemyKilled(Enemy e){
		enemies.remove(e);
		enemiesDone++;
		LGUI.wavepbar.killed();
	}	
	
	public static void enemyReachedEnd(Enemy e){
		enemies.remove(e);
		enemiesDone++;
		LGUI.wavepbar.reachedEnd();
		if(baseHealth <= 0){
			failedLevel();
		}
	}
	public static void removeTurret(Turret t){turrets.remove(t);}
	public static void removeBullet(Bullet b){bullets.remove(b);}
	
	public void leftClick(StateBasedGame sbg) {
		
		if(sidemenu != null){
			clickedPauseMenu();
		}else{
			clickedLevel();
		}
	}
	
	private void clickedPauseMenu() {
		MenuButton mb = sidemenu.getClickedButton(new Point(mp.getCenterX(),mp.getCenterY()));
		if(mb != null){
			int e = mb.getEvent();
			switch(e){
			case(RESUME):
				togglePause();
				break;
			case(RESTART):
				loadLevel(levelpath, levelname);
				break;
			case(NEXTLEVEL):
				break;
			case(UPLOAD_SCORE):
				net.uploadScore(score+(credits*2),levelname,levelpath);
				break;
			case(LOADLASTCHECKPOINT):
				loadSave();
				break;
			case(MAINMENU):
				enterState(Launch.MENU);
				break;
			case(QUIT):
				Launch.quit();
				break;
			}
		}

	}

	public void makeSave(){
		Save.newSave(levelpath, levelname, currentWave, credits, score, turrets);
	}
	
	public void loadSave(){
		SaveData sd = Save.loadSave();
		loadLevel(sd.path, sd.levelname);
		currentWave = sd.wave - 1;
		credits = sd.credits;
		score = sd.score;
		turrets = sd.turrets;
		for(int i = 0; i < currentWave; i++){
			LGUI.updateLevelProgress();
		}
		LGUI.updateTurretCostText();
	}
	
	private void clickedLevel(){
		switch(selectstatus){
		case(NONE): //NONE ==> BUILDING
			tOnHand = new Turret(Play.turrets.size());
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
		if(object.intersects(path)){
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
		LGUI.updateTurretCostText();
	}
	
	public void sellTurret(Turret target) {
		System.out.println("Solf for: " + target.value);
		score -= target.value;
		credits += target.value*.75f;
		turrets.remove(target);
		
		LGUI.updateTurretCostText();
	}
	
	public static int getNextTurretCost() {
		return (int) (30*(Math.pow(1.5, turrets.size())));
	}
	
	@Override
	public int getID(){return Launch.PLAY;}
	public static void enterState(int val){Launch.changeState(val);}	
	
	public static void pay(int val){credits += val;}

	public void togglePause(){
		if(sidemenu == null || sidemenu instanceof PauseMenu){
			paused = !paused;
			Mouse.setGrabbed(!paused);
			if(paused){
				sidemenu = new PauseMenu();
			}else{
				sidemenu = null;
			}
		}
	}
		
}
