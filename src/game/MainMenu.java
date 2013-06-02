package game;

import gui.menus.LevelSelect;
import gui.menus.LevelSelectButton;
import gui.menus.Levels;
import gui.menus.Menu;
import gui.menus.MenuButton;
import gui.menus.Options;
import gui.menus.Top;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.data.LevelListData;

public class MainMenu extends BasicGameState{

	public static final int QUIT = 0, PLAY = 1, OPTIONS = 2;
	public static final int NEW_LEVEL = 10, RESUME = 11;
	public static final int VIDEO = 20, AUDIO = 21;
	
	public static int width;
	public static int height;
	public static int my,mx;
	public static Point mousepoint = new Point(Mouse.getX(), Mouse.getY());
	public static LevelListData levellistdata = new LevelListData();
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	
	public MainMenu(){
		
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		width = gc.getWidth();
		
		menus.add(new Top(0,0));
		levellistdata.loadLevelData();
		
		//play = new MenuButton(new Image("play_button.png"), 100, 100, Launch.PLAY);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for(int i = 0; i < menus.size(); i++){
			menus.get(i).draw(g);
			menus.get(i).update(); //naughty naughty me
		}
				
		g.setColor(Color.white);
				
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Mouse.setGrabbed(false);
		Input in = new Input(0);
		mx = in.getMouseX();
		my = gc.getHeight() - Mouse.getY();
		mousepoint = new Point(mx,my);
		
		
	}

	@Override
	public int getID() {
		return 0;
	}

	//mouse clicks
	public void leftClick(StateBasedGame sbg) {
		for(int i = 0; i < menus.size(); i++){
			Menu m = menus.get(i);
			if(m.outline.contains(mousepoint)){
				MenuButton b = m.getClickedButton();
				if(b != null){
					if(b instanceof LevelSelectButton){
						System.out.println(b.levelpath);
						Launch.playLevel(b.levelpath);
					}else{
						checkEvent(b.getEvent());
					}
				}
			}
		}
		
	}

	private void checkEvent(int event) {
		System.out.println(event);
		switch(event){
			//top
			case(QUIT):Launch.quit();break;
			case(PLAY):
				removeMenus(1);
				menus.add(new Levels(menus.size()*200,0));
				break;
			case(OPTIONS):
				removeMenus(1);
				menus.add(new Options(menus.size()*200,0));
				break;
			case(NEW_LEVEL):
				removeMenus(2);
				menus.add(new LevelSelect(menus.size()*200,0));
				break;
			case(RESUME):
				break;
		}
		
	}


	private void removeMenus(int val) {
		ArrayList<Menu> newmenus = new ArrayList<Menu>();
		for(int i = 0; i < val; i++){
			newmenus.add(menus.get(i));
			
		}
		menus = newmenus;
	}
}