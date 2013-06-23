package gui.modding;

import game.Modding;
import gui.Menu;
import gui.Button;

public class ModdingMenu extends Menu{

	public ModdingMenu(float newx, float newy) {
		super(newx, newy,"Modding",true);
		
		addElement(new Button("Turrets", 20, 50, Modding.TURRETS));
		addElement(new Button("Enemies", 20, 100, Modding.ENEMIES));
		addElement(new Button("Back", 20, 550, Modding.QUIT));
	}
}