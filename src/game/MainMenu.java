package game;

import gui.Branch;
import gui.MenuButton;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState{

	private final int QUIT = 0, PLAY = 1, RESUME = 2, OPTIONS = 3;
	
	public static int width;
	public static int height;
	public static int my,mx;
	public static Point mousepoint = new Point(Mouse.getX(), Mouse.getY());
	
	public static MenuButton play;
	
	public ArrayList<Branch> branches = new ArrayList<Branch>();
	public ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		width = gc.getWidth();
		
		//play = new MenuButton(new Image("play_button.png"), 100, 100, Launch.PLAY);
		
		buttons.add(new MenuButton("Play",20,50,PLAY));
		buttons.add(new MenuButton("QUIT",20,200,QUIT));
		
		branches.add(new Branch(buttons));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for(int i = 0; i < branches.size(); i++){
			Branch b = branches.get(i);
			for(int j = 0; j < b.buttons.size(); j++){
				MenuButton button = b.buttons.get(j);
				g.drawString(button.text, button.getX(), button.getY());
				g.draw(button.bounds);
				//button.getImage().draw(button.getX(), button.getY());
			}
		}
		
				
		g.setColor(Color.white);
		g.drawString("Mouse X: "+mx, 50, 50);
		g.drawString("Mouse Y: "+my, 50, 70);
		
		g.draw(mousepoint);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Mouse.setGrabbed(false);
		Input in = new Input(0);
		mx = in.getMouseX();
		my = gc.getHeight() - Mouse.getY();
		mousepoint = new Point(mx,my);
		for(int i = 0; i < buttons.size(); i++){
			MenuButton button = buttons.get(i);
			if(button.bounds.contains(mousepoint)){
				button.setButtonState(button.HOVER_MOUSE);
			}else{
				button.setButtonState(button.NO_MOUSE);
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}

	//mouse clicks
	public void leftClick(StateBasedGame sbg) {
		for(int i = 0; i < buttons.size(); i++){
			MenuButton button = buttons.get(i);
			if(button.bounds.contains(mousepoint)){
				button.setButtonState(button.CLICK_MOUSE);
				checkEvent(button.getEvent());
			}
		}
		
	}

	private void checkEvent(int event) {
		System.out.println(event);
		switch(event){
		
		case(QUIT):Launch.quit();
		case(PLAY):Launch.changeState(Launch.PLAY);
		case(OPTIONS):
		case(RESUME):
		}
		
	}
	
}


