package gui.game;

import game.Play;
import gui.GUIElement;
import gui.Panel;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import resources.Turret;
import resources.types.TurretType;

public class TurretGUI extends Panel{

	//events
	public Turret target;
	
	private float turretx, turrety;
	
	private static float GUIwidth = 120, GUIheight;
	
	public TurretGUI(Turret turret){
		super(turret.x+20, turret.y-20,GUIwidth,GUIheight);
		target = turret;
		color = Color.orange;
		loadUpgrades();
	}

	public void checkPlace(){
		turretx = target.getX();
		turrety = target.getY();
		
		if(Play.screenWidth - turretx <= GUIwidth + 10){ //if turret goes off right side
			x = turretx - GUIwidth - 20;
		}else{
			x = turretx + 20;
		}
		
		if(Play.screenHeight - turrety <= GUIheight + 10){ //if turret goes off bottom
			y = turrety - GUIheight - 10;
		}else{
			y = turrety - 20;
		}
	}
	
	public void loadUpgrades(){
		guielements.clear();
		//upgrade options
		int[] branches = Play.TDATA.getUpgradeBranches(target.getID());
		GUIheight = branches.length*30 +10;
		checkPlace();
		TurretType turrettype;
		for(int b = 0; b < branches.length; b++){
			int upgradeID = branches[b];
			turrettype = Play.TDATA.getTurretType(upgradeID);
			int cost = turrettype.COST;
			String text = turrettype.NAME + " (" + cost + ")";
			addElement(new LevelGUIButton(text,x +10, y + b*30+10, upgradeID, cost));
		}
		
		addElement(new LevelGUIButton("Sell", x +10, y+GUIheight, -1, 0));
		shape = new Rectangle(x,y,GUIwidth,GUIheight+30);
	}
	
	public void checkAffordability(int budget){
		for(int i = 0; i < guielements.size(); i++){
			GUIElement ge = guielements.get(i);
			if(ge instanceof LevelGUIButton){
				((LevelGUIButton)ge).updateAfforcability(budget);
			}
		}
	}
}
