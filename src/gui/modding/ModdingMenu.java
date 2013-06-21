package gui.modding;

import game.Modding;
import gui.menus.Menu;
import gui.menus.MenuButton;

public class ModdingMenu extends Menu{

	public ModdingMenu(float newx, float newy) {
		super(newx, newy,"Modding",true);
		
		addButton(new MenuButton("Turrets", 20, 50, Modding.TURRETS));
		addButton(new MenuButton("Enemies", 20, 100, Modding.ENEMIES));
		addButton(new MenuButton("Back", 20, 550, Modding.QUIT));
	}
}