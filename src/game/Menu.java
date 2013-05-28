package game;

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

public class Menu extends BasicGameState{

	public static int width;
	public static int height;
	public static int my,mx;
	public static Point mousepoint = new Point(Mouse.getX(), Mouse.getY());
	
	public static MenuButton play;
	
	public ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	public Menu(int menu) {
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		width = gc.getWidth();
		play = new MenuButton(new Image("play_button.png"), 100, 100, Launch.PLAY);
		
		buttons.add(play);
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for(int i = 0; i < buttons.size(); i++){
			MenuButton button = buttons.get(i);
			button.getImage().draw(button.getX(), button.getY());
		}
		
				
		g.setColor(Color.white);
		g.drawString("Mouse X: "+mx, 50, 50);
		g.drawString("Mouse Y: "+my, 50, 70);
		
		g.setColor(Color.green);
		g.draw(play.bounds);
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
				sbg.enterState(button.getEvent());
			}
		}
		
	}
	
}


//button class
class MenuButton{
	
	public final int NO_MOUSE = 0;
	public final int HOVER_MOUSE = 1;
	public final int CLICK_MOUSE = 2;
	
	private int BUTTONSTATE = NO_MOUSE;
	private int EVENT;
	
	private static final int height = 64;
	private static final int width = 256;
	
	private static Image[] subimgs = new Image[3];
	
	public Rectangle bounds;
	private static int xpos,ypos;
	
	public MenuButton(Image image, int x, int y, int e) {
		EVENT = e;
		xpos = x;
		ypos = y;
		bounds = new Rectangle(x, y, width, height);
		for(int i = 0; i < subimgs.length; i++){
			subimgs[i] = image.getSubImage(0, (i*height), width, height); 
		}
		// TODO Auto-generated constructor stub
	}

	public int getX(){return xpos;}
	public int getY(){return ypos;}
	
	public boolean mouseInButton(float x, float y){
		if((xpos<x && x<xpos+width) && (ypos<y && y<ypos+height)){
			return true;
		}else{return false;}
	}
	
	public Image getImage() {
		// TODO Auto-generated method stub
		return subimgs[BUTTONSTATE];
	}
	
	public void setButtonState(int state){
		BUTTONSTATE = state;
	}
	
	public int getEvent(){return EVENT;}
}