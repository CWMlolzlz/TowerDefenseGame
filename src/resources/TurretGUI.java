package resources;

import game.Play;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class TurretGUI {

	private static TurretData tdata = Play.TDATA;
	
	//events
		
	private Turret target;
	
	private float GUIx, GUIy;
	private float turretx, turrety;
	//private int points, damage, t;
	//private int upgradecost;
	
	private Shape shape;
	private float GUIwidth = 128, GUIheight = 150;
	private int left = 1;
	
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public TurretGUI(Turret turret, GameContainer gc) {
		target = turret;
		loadTurretData(target, gc);
				
		//appearance
		shape = new Rectangle(GUIx, GUIy, GUIwidth, GUIheight);
				
		loadUpgrades();
	}

	public void loadTurretData(Turret t, GameContainer gc){
		turretx = t.getX();
		turrety = t.getY();
		
		GUIy = turrety - 20;
		
		if(gc.getWidth() - turretx <= GUIwidth + 10){
			GUIx = turretx - GUIwidth - 20;
		}else{
			GUIx = turretx + 20;
		}
		
		if(gc.getHeight() - turrety <= GUIheight + 10){
			GUIy = turrety - GUIheight + 20;
		}else{
			GUIy = turrety - 20;
		}
	}
	
	public void loadUpgrades(){
		buttons.clear();
		//upgrade options
		UpgradeBranch branch = tdata.getUpgradeBranches(target.getID());
		TurretType turrettype;
		for(int b = 0; b < 3; b++){ //three options available at max
			int upgradeID = branch.getBranch(b);
			if(upgradeID >= 0){
				turrettype = tdata.getTurretType(upgradeID);
				int cost = turrettype.COST;
				String text = turrettype.NAME + " (" + cost + ")";
				buttons.add(new Button(GUIx + left*(10), GUIy + left*(10 + b*30), GUIwidth-20, 20, text, upgradeID, cost));
			}	
		}
		
		//typical buttons
		
		buttons.add(new Button(GUIx+10,GUIy+GUIheight-30,GUIwidth-20,20,"Sell",-1,-target.getValue()));
	}
	
	public Shape getShape(){
		return shape;
	}
	
	public Turret getTarget(){
		return target;
	}
	
	public ArrayList<Button> getButtons(){
		return buttons;
	}

	public Button click(Shape mp) { //returns EVENT id (int)
		for(int i = 0; i < buttons.size(); i++){ //optimise with function
			Button button = buttons.get(i);
			if(button.getShape().intersects(mp)){
				return button;
			}
		}
		return null;		
	}
	
	public void mouseOver(Shape mp){
		
	}
	
	public void setTurretData(TurretData td){
		tdata = td;
	}
	
}
