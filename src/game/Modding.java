package game;

import gui.GUIElement;
import gui.Panel;
import gui.menus.MenuButton;
import gui.modding.Button;
import gui.modding.ModdingMenu;
import gui.modding.Specs;
import gui.modding.TextArea;
import gui.modding.Window;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Modding extends BasicGameState implements KeyListener{

	public static final int QUIT = 0, TURRETS = 1, ENEMIES = 2;
	public static final int PREVIEW = 10, SAVE_TURRET = 11;
	
	public static Point mousepoint;
	Input i = new Input(0);
	
	Window win;
	
	private TextArea textAreaFocus = null;
	
	ArrayList<Panel> panels = new ArrayList<Panel>();
	ModdingMenu moddingmenu;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		panels.add(new Specs(200,0,300,300));
		moddingmenu = new ModdingMenu(0, 0);
		win  = new Window(500,0,300,300);
		//panels.add(win);
		win.preview();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setAntiAlias(true);
		moddingmenu.draw(g);
		for(int i = 0; i < panels.size();i++){
			panels.get(i).draw(g);
		}
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		mousepoint = new Point(Mouse.getX(),gc.getHeight() - Mouse.getY());
		win.update();
	}

	//mouse clicks
	public void leftClick(StateBasedGame sbg){
		if(textAreaFocus!=null){
			if(textAreaFocus.text == ""){
				textAreaFocus.setToDefault();
			}
		}
		if(moddingmenu.outline.contains(mousepoint)){
			MenuButton b = moddingmenu.getClickedButton(mousepoint);
			if(b != null){
				checkEvent(b.getEvent());
			}
		}else{
			for(int i = 0; i < panels.size();i++){
				Panel p = panels.get(i);
				if(p.outline.contains(mousepoint)){
					GUIElement e = p.getClickedElement(mousepoint);
					if(e instanceof Button){
						checkEvent(((Button) e).getEvent());
					}else if(e instanceof TextArea){
						((TextArea) e).select();
						textAreaFocus = (TextArea) e;
					}
					break;
				}
			}
		}
		
	}
		
	private void checkEvent(int event) {
		switch(event){
			case(TURRETS):
				//Launch.changeState(Launch.MENU);
				break;
			case(ENEMIES):
				//Launch.changeState(Launch.MENU);
				break;
			case(QUIT):
				Launch.changeState(Launch.MENU);
				break;
			case(PREVIEW):
				win.preview();
				break;
			}
		
	}
	
	@Override
    public void keyPressed(int k, char c){
		if(k == 14){
			backspace();
		}else{
			input(c);
		}
    }
	
	private void backspace() {
		if(textAreaFocus != null){
			textAreaFocus.backSpace();
		}	
	}

	private void input(char c) {
		if(textAreaFocus != null){
			if(Character.isLetter(c) && textAreaFocus.alpha){
				textAreaFocus.addChar(c); 
			}else if(Character.isDigit(c) && textAreaFocus.digits){
				textAreaFocus.addChar(c); 
			}else if(Character.isSpaceChar(c) && textAreaFocus.special){
				textAreaFocus.addChar(c); 
			}
		}		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Launch.MODDING;
	}
	
}