package game;

import gui.Button;
import gui.GUIElement;
import gui.Menu;
import gui.game.HighScoresMenu;
import gui.game.LevelCompleteMenu;
import gui.game.LevelGUI;
import gui.game.PauseMenu;
import gui.game.TurretGUI;

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
import resources.Enemy;
import resources.Explosion;
import resources.PathPoint;
import resources.Save;
import resources.Spark;
import resources.Spawner;
import resources.Turret;
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
	private static HighScoresMenu extendedmenu;
	
	public static Point mp;
	public static Circle cursor;
	
	private final int NONE = 0, BUILDINGTURRET = 1, EDITINGTURRET = 2, HOVERTURRET = 3;
	private static TurretGUI tGUI;
	private Turret tOnHand;
	private Turret selectedTurret;
	
	public static int selectstatus;
	public boolean paused;
	
	public static float baseHealth;
	public static float credits;
	public static float score;
	
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
		extendedmenu = null;
		
		currentWave = 1;
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
		//g.setColor(Color.cyan);
		//g.draw(path);
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
		
		//turret on hand
		if(tOnHand != null){
			if(tOnHand.valid){
				g.setColor(Color.green);
			}else{
				g.setColor(Color.red);
			}
			g.draw(tOnHand.getCircle());
		}
		
		if(tGUI !=null){
			tGUI.draw(g);
		}
		
		if(ps != null){
			ps.render();
		}
		
		LGUI.draw(g);	
		g.setLineWidth(1);
		//turret gui
		if(tGUI != null){
			tGUI.draw(g);
			
		}
		
		//cursor
		if(sidemenu != null){
			sidemenu.draw(g);
			if(extendedmenu != null){
				extendedmenu.update();
				extendedmenu.draw(g);
			}
		}else{
			g.setColor(Color.white);
			g.draw(cursor);
		}
			
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		mp = new Point(Mouse.getX(), gc.getHeight()-Mouse.getY());
		cursor = new Circle(mp.getCenterX(), mp.getCenterY(), 3);
		if(!(sidemenu instanceof PauseMenu)){
			//AI step
			gameUpdate();
			
			if(sidemenu == null){
				Turret collidingturret = getCollidingTurret(cursor);
				//TURRET PLACING POSITION
				if(tOnHand != null){
					tOnHand.updatePlacement(cursor.getCenterX(), cursor.getCenterY());
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
			
			ps.update(15);
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
			if(currentWave == maximumWaves+1){
				gamestage = COMPLETE;
				completeLevel();
			}else{
				if(wave.save){
					makeSave();
				}
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
		pay(credits *0.00012f);
	}

	public static void enemyKilled(Enemy e){
		score += e.radius;
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
		GUIElement ge = sidemenu.getClickedElement(new Point(mp.getCenterX(),mp.getCenterY()));
		if(ge instanceof Button){
			Button mb = (Button)ge;
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
				String[] scores = net.uploadScore((int)(score+(credits*1.5)*(baseHealth/100)),levelpath);
				extendedmenu = new HighScoresMenu(200, 0, scores);
				
				break;
			case(LOADLASTCHECKPOINT):
				if(Save.saveExists()){
					loadSave();
				}
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

	}

	public void makeSave(){
		Save.newSave(levelpath, levelname, currentWave, (int)baseHealth, credits, score, turrets);
	}
	
	public void loadSave(){
		SaveData sd = Save.loadSave();
		loadLevel(sd.path, sd.levelname);
		currentWave = sd.wave - 1;
		credits = sd.credits;
		score = sd.score;
		turrets = sd.turrets;
		for(int i = 1; i < currentWave; i++){
			LGUI.updateLevelProgress();
		}
		LGUI.updateTurretCostText();
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
					placeTurret(tOnHand, tcost);
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
					selectedTurret = getCollidingTurret(cursor);
					tGUI = new TurretGUI(selectedTurret);
					tGUI.checkAffordability((int)credits);
				}
				break;
			case(EDITINGTURRET):
				selectObject();
				break;
		}
	}
	
	public void selectObject(){
		Turret ct = getCollidingTurret(cursor);
		if(isColliding(cursor, tGUI.shape)){
			//clicked GUI
			Button button = (Button) tGUI.getClickedElement(mp);
			if(button != null){
				selectedTurret = tGUI.target;
				int eventval = button.getEvent();
				switch(eventval){
					case(-1):
						sellTurret(selectedTurret);
						tGUI = null;
						selectedTurret = null;
						selectstatus  = NONE;
						break;
					default:
						int cost = TDATA.getTurretType(eventval).COST;
						if(credits - cost >= 0){
							tGUI.target.upgrade(eventval);
							credits -= cost;
							tGUI = new TurretGUI(selectedTurret);
							tGUI.checkAffordability((int)credits);
						}else{
							//cannot afford upgrade
						}	
						break;
				}
							
			}
		}else if(ct != null && ct != tGUI.target){
			//clicked turret not associated with GUI
			selectedTurret = ct;
			tGUI = new TurretGUI(selectedTurret);
			tGUI.checkAffordability((int)credits);
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
		return tGUI.shape.contains(mp);
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
	
	public void placeTurret(Turret turret,int cost){
		turrets.add(turret);
		turret.place(cost);
		LGUI.updateTurretCostText();
	}
	
	public void sellTurret(Turret target) {
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
	
	public static void pay(float f){
		credits += f;
		if(tGUI != null){
			tGUI.checkAffordability((int)credits);
		}
	}

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
