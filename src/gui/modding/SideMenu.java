package gui.modding;

import game.Modding;
import gui.menus.Menu;
import gui.menus.MenuButton;

public class SideMenu extends Menu{

	public SideMenu(float newx, float newy) {
		super(newx, newy);
		this.tick = this.length;
		this.x = newx;
		this.update();
		addButton(new MenuButton("Turrets", 20, 50, Modding.TURRETS));
		addButton(new MenuButton("Enemies", 20, 100, Modding.ENEMIES));
		addButton(new MenuButton("Quit", 20, 550, Modding.QUIT));
	}
}