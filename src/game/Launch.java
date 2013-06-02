package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;
import org.newdawn.slick.state.StateBasedGame;

public class Launch extends StateBasedGame implements InputProviderListener{

	//controls
	private Command leftclick = new BasicCommand("leftclick");
	private Command rightclick = new BasicCommand("rightclick");
	private Command enter = new BasicCommand("enter");
	private Command escape = new BasicCommand("escape");
	private InputProvider provider;
	
	public static final String gamename = "TowerDefense";
	public static final int MENU = 0; 
	public static final int PLAY = 1;
	
	private static Play play = new Play();
	private static MainMenu menu = new MainMenu();
	
	public static GameContainer gamecontainer;
	private static StateBasedGame sbg;
	
	public Launch(String gamename){
		super(gamename);
		this.addState(menu);
		this.addState(play);		
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		gamecontainer = gc;
		
		
		provider = new InputProvider(gc.getInput());
		provider.addListener(this);
		provider.bindCommand(new MouseButtonControl(0), leftclick);
		provider.bindCommand(new MouseButtonControl(1), rightclick);
		provider.bindCommand(new KeyControl(Input.KEY_ENTER), enter);
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), escape);
		
		this.getState(MENU).init(gc,this);
		this.getState(PLAY).init(gc,this);
		
		this.enterState(MENU);
		sbg = this;
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Launch(gamename));
		app.setDisplayMode(800,600,false);
		//app.setVSync(true);
		app.setTargetFrameRate(60);
		app.start();
	}

	@Override
	public void controlPressed(Command arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlReleased(Command command) {
		
		if(command == leftclick){
			if(this.getCurrentState() == menu){
				menu.leftClick(this);
			}else if(isPlaying()){
				play.leftClick(this);
			}
		}else if(command == rightclick){
			if(isPlaying()){
				play.rightClick(this);
			}
		}else if(command == enter){
			if(isPlaying()){
				play.level.startWave();
			}
		}else if(command == escape){
			this.getContainer().exit();
		}
		
	}
	
	public static void quit(){gamecontainer.exit();}
	
	public static void changeState(int val){
		sbg.enterState(val);
	}
	
	

	public boolean isPlaying(){
		if(this.getCurrentState() == play){
			return true;
		}else{
			return false;
		}
	}

	public static void playLevel(String levelpath) {
		play.loadLevel(levelpath);
		changeState(PLAY);
	}	
}
