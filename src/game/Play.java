package game;

import gui.ProgressBar;

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
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.Bullet;
import resources.Button;
import resources.Enemy;
import resources.EnemyData;
import resources.Explosion;
import resources.Level;
import resources.Spark;
import resources.Turret;
import resources.TurretData;
import resources.TurretGUI;

public class Play extends BasicGameState{

	public static TurretData TDATA = new TurretData();
	public static EnemyData EDATA = new EnemyData();
	public static ParticleSystem ps;
	public static File psXML = new File("data/particles/spark.xml");
	public static ConfigurableEmitter ce;
	public static ArrayList<Spark> sparks = new ArrayList<Spark>();
	public static ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	
	private final int NONE = 0, BUILDINGTURRET = 1, EDITINGTURRET = 2, HOVERTURRET = 3;
	
	public int screenWidth, screenHeight;
	
	public GameContainer gamecont;
	public static StateBasedGame statebasedgame;
	
	//input
	public Input i;
	public InputProvider provider;
	
	public static Circle mp = new Circle(Mouse.getX(), Mouse.getY(), 3f);
	
	private TurretGUI tGUI = null;
	private Turret tOnHand = null;
	private Turret selectedTurret = null;
	
	public static int selectstatus;
	
	public static float credits = 150;
	
	public Level level;
	
	//onscreen enitites
		
	public Play(int state){
		level = new Level();
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		gamecont = gc;
		statebasedgame = sbg;
		System.out.println("new level");
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
		level.loadLevel("01");
		//font = new AngelCodeFont("fonts/squared_12.fnt", new Image("fonts/squared_12_0.png"));
		screenWidth = gc.getWidth();
		screenHeight = gc.getHeight();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setAntiAlias(true);
				
		//debug info
		g.drawString("Select state:" + selectstatus, 10, 30);
		g.drawString("Number of Turrets:" + Level.getTurrets().size(), 10, 50);
		g.drawString("Number of Enemies:" + Level.getEnemies().size(), 200,30 );
		g.drawString("Number Of Emitters:" + ps.getEmitterCount(), 200, 50);
		g.drawString("Number of Particles:" + ps.getParticleCount(), 400, 30);
		g.drawString("Current Wave:" + level.currentWave, 400, 50);

		g.drawString("MEM in use: " + (Runtime.getRuntime().totalMemory()/1000000) + "(MB)", 10, 580);
		g.drawString("Credits: " + (int)(credits), 10, 560);
		
		//path
		//g.setColor(Color.cyan);
		//g.draw(level.getPath());
		g.setColor(Color.blue);
		g.draw(level.getEdgeA());
		g.draw(level.getEdgeB());
		
		//enemies
		g.setColor(Color.white);
		ArrayList<Enemy> enemies = Level.getEnemies(); //optimise drawing. onefunction????
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
		ArrayList<Turret> turrets = Level.getTurrets(); //optimise drawing. onefunction????
		for(int j = 0; j < turrets.size(); j++){
			Turret t = turrets.get(j);
			
			g.draw(t.getCircle());
		}
		g.setColor(Color.green);
		//selectedturret
		if(selectedTurret != null){
			g.draw(selectedTurret.getRangeCircle());
		}
		
		//draw bullets
		//g.setLineWidth(3);
		ArrayList<Bullet> bullets = Level.getBullets();
		for(int b = 0; b < bullets.size(); b++){
			Bullet bullet = bullets.get(b);
			Color c = new Color(bullet.getColor());
			float alpha = bullet.decay;
			g.setColor(new Color(c.r,c.g,c.b,alpha));
			g.draw(bullet.getShape());
		}
		//g.setLineWidth(1);
		
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
		
		//progressbar
		ProgressBar p = level.pb;
		g.setColor(Color.green);
		g.fill(p.fill);
		g.drawString(p.toptext, p.textx, p.texty);
		g.setColor(Color.white);
		g.draw(p.outline);			
		
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
		g.setColor(Color.white);
		g.draw(mp);
			
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Mouse.setGrabbed(true);
		
		Turret collidingturret = getCollidingTurret(mp);
		mp = new Circle(Mouse.getX(), gc.getHeight()-Mouse.getY(), 3f);
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
		level.update();
		ps.update(delta);
		for(int s = 0; s < sparks.size(); s++){
			sparks.get(s).updateDecay();
		}
		for(int e = 0; e < explosions.size(); e++){
			explosions.get(e).updateDecay();
		}
		
	}
		
	public void leftClick(StateBasedGame sbg) {
		switch(selectstatus){
			case(NONE): //NONE ==> BUILDING
				tOnHand = new Turret();
				selectstatus = BUILDINGTURRET;
				break;
			case(BUILDINGTURRET): //BUILDING ==> NONE
				if(tOnHand.valid && credits >= 100){ //optimise detection for both cases
					//can place turret
					level.placeTurret(tOnHand);
					credits -= 100;
				}else{
					//cant place turret
					selectstatus = NONE;
					
				}
				tOnHand = null;
				selectstatus = NONE;
				break;
			case(HOVERTURRET): //HOVERING ==> EDITING
				System.out.println("Click3");
				if(tOnHand == null){
					selectstatus = EDITINGTURRET;
					selectedTurret = getCollidingTurret(mp);
					tGUI = new TurretGUI(selectedTurret, gamecont);
				}
				break;
			case(EDITINGTURRET):
				System.out.println("Click4");
				selectObject();
				break;
		}		
	}
	
	public void selectObject(){
		Turret ct = getCollidingTurret(mp);
		if(isColliding(mp, tGUI.getShape())){
			//clicked GUI
			System.out.println("GUI clicked");
			Button button = tGUI.click(mp);
			if(button != null){
				selectedTurret = tGUI.getTarget();
				int cost = button.getCost();
				int eventval = button.getEvent();
				switch(eventval){
					case(-1):
						level.sellTurret(tGUI.getTarget());
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
		ArrayList<Turret> turrets = Level.getTurrets();
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
		if(	object.intersects(level.getEdgeA()) || 
			object.intersects(level.getEdgeB()) || 
			object.intersects(level.getPath())
			){
			return true;
		}else{
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
		System.out.println("Right click");
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
	
	@Override
	public int getID(){return 1;}
	
	
	public static void enterState(){
		statebasedgame.enterState(0);
	}
	
	public static void pay(int val){credits += val;}
}
