package gui.menus;

import game.MainMenu;

public class ModdingTools extends Menu{

	public ModdingTools(float newx, float newy) {
		super(newx, newy,"Modding Tools",false);
		addButton(new MenuButton("Turrets",220,50,MainMenu.MOD_TURRETS));
		addButton(new MenuButton("Enemies",220,100,MainMenu.MOD_ENEMY));
		addButton(new MenuButton("Levels",220,150,MainMenu.MOD_LEVEL));
	}
}
