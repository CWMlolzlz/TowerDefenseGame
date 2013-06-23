package gui.menus;

import game.MainMenu;
import gui.Button;
import gui.Menu;

public class ModdingTools extends Menu{

	public ModdingTools(float newx, float newy) {
		super(newx, newy,"Modding Tools",false);
		addElement(new Button("Turrets",220,50,MainMenu.MOD_TURRETS));
		addElement(new Button("Enemies",220,100,MainMenu.MOD_ENEMY));
		addElement(new Button("Levels",220,150,MainMenu.MOD_LEVEL));
	}
}
