package gui;

import game.Launch;
import game.Play;
import gui.menus.Menu;
import gui.menus.MenuButton;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class PauseMenu extends Menu{
	
	public PauseMenu(){
		super(0,0);
		addButton(new MenuButton("Resume",20,50,Play.RESUME));
		addButton(new MenuButton("Quit",20,540,Play.QUIT));
	}
	
}
