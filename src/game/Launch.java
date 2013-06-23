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
	public static final int MENU = 1; 
	public static final int PLAY = 2;
	public static final int MODDING = 3;
	public static final int LOGIN = 0;
	
	public static Play play = new Play();
	public static MainMenu menu = new MainMenu();
	public static Modding modding = new Modding();
	public static Login login = new Login();
	
	public static GameContainer gamecontainer;
	private static StateBasedGame sbg;
	
	public static String USERNAME = "";
	
	public Launch(String gamename){
		super(gamename);
		this.addState(menu);
		this.addState(play);		
		this.addState(modding);
		this.addState(login);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		gamecontainer = gc;
		sbg = this;
		
		provider = new InputProvider(gc.getInput());
		provider.addListener(this);
		provider.bindCommand(new MouseButtonControl(0), leftclick);
		provider.bindCommand(new MouseButtonControl(1), rightclick);
		provider.bindCommand(new KeyControl(Input.KEY_ENTER), enter);
		provider.bindCommand(new KeyControl(Input.KEY_ESCAPE), escape);
		
		this.enterState(LOGIN);
		
	}
	
	
	@Override
	public void controlPressed(Command arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlReleased(Command command) {
		
		if(command == leftclick){
			if(this.getCurrentState() == login){
				login.leftClick(this);
			}else if(this.getCurrentState() == menu){
				menu.leftClick(this);
			}else if(isPlaying()){
				play.leftClick(this);
			}else if(isModding()){
				modding.leftClick(this);
			}
		}else if(command == rightclick){
			if(isPlaying()){
				play.rightClick(this);
			}
		}else if(command == escape){
			if(isPlaying()){
				play.togglePause();
			}else{
				this.getContainer().exit();
			}
		}
		
	}
	
	public static void quit(){gamecontainer.exit();}
	
	public static void changeState(int val){
		sbg.enterState(val);
	}
	
	private boolean isModding() {
		if(this.getCurrentState() == modding){
			return true;
		}else{
			return false;
		}
	}

	public boolean isPlaying(){
		if(this.getCurrentState() == play){
			return true;
		}else{
			return false;
		}
	}

	public static void playLevel(String levelpath, String name) throws SlickException {
		play.loadLevel(levelpath, name);
		sbg.enterState(PLAY);
	}	
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new Launch(gamename));
		app.setDisplayMode(800,600,false);
		app.setVSync(true);
		app.setTargetFrameRate(60);
		app.start();
	}

}
