package game;

import gui.Button;
import gui.GUIElement;
import gui.Menu;
import gui.menus.LevelSelect;
import gui.menus.LevelSelectButton;
import gui.menus.Options;
import gui.menus.PlayMenu;
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

import resources.Save;
import resources.data.LevelListData;

public class MainMenu extends BasicGameState{

	public static final int QUIT = 0, PLAY = 1, OPTIONS = 2,MODDING_TOOLS = 3;
	public static final int NEW_LEVEL = 10, RESUME = 11;
	public static final int VIDEO = 20, AUDIO = 21;
	public static final int MOD_TURRETS = 30, MOD_ENEMY = 31, MOD_LEVEL = 32;
		
	public static int width;
	public static int height;
	public static int my,mx;
	public static Point mousepoint;
	public static LevelListData levellistdata = new LevelListData();
	public ArrayList<Menu> menus = new ArrayList<Menu>();
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		menus.clear();
		width = gc.getWidth();
		menus.add(new Top(0,0));
		levellistdata.loadLevelData();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for(int i = 0; i < menus.size(); i++){
			menus.get(i).draw(g);
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
		for(int i = 0; i < menus.size(); i++){
			menus.get(i).update();
		}
		
	}

	@Override
	public int getID() {
		return Launch.MENU;
	}

	//mouse clicks
	public void leftClick(StateBasedGame sbg) {
		for(int i = 0; i < menus.size(); i++){
			Menu m = menus.get(i);
			if(m.shape.contains(mousepoint)){
				GUIElement ge = m.getClickedElement(mousepoint);
				if(ge != null && ge instanceof Button){
					Button b = (Button) ge;
					if(b instanceof LevelSelectButton){
						LevelSelectButton lsb = (LevelSelectButton)b;
						try {Launch.playLevel(lsb.levelpath, lsb.text);}catch(SlickException e){e.printStackTrace();}
					}else{
						checkEvent(b.getEvent());
					}
				}
				break;
			}
		}
		
	}

	private void checkEvent(int event) {
		switch(event){
			//top
			case(QUIT):
				Launch.quit();
				break;
			case(PLAY):
				removeMenus(PLAY);
				menus.add(new PlayMenu(menus.size()*200,0));
				break;
			case(OPTIONS):
				removeMenus(OPTIONS);
				menus.add(new Options(menus.size()*200,0));
				break;
			case(MODDING_TOOLS):
				removeMenus(MODDING_TOOLS);
				Launch.changeState(Launch.MODDING);
				break;
			case(MOD_TURRETS):
				break;
			case(NEW_LEVEL):
				removeMenus(NEW_LEVEL);
				menus.add(new LevelSelect(menus.size()*200,0));
				break;
			case(RESUME):
				if(Save.saveExists()){
					Launch.play.loadSave();
					Launch.changeState(Launch.PLAY);
				}
				break;
		}
		
	}


	private void removeMenus(int val) {
		ArrayList<Menu> newmenus = new ArrayList<Menu>();
		int num = (""+val).length();
		for(int i = 0; i < num; i++){
			newmenus.add(menus.get(i));
			
		}
		menus = newmenus;
	}
}