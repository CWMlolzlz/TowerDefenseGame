package gui.game;

import game.Play;
import gui.GUIElement;
import gui.Meter;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class LevelGUI extends GUIElement{
	
	//Font font = new Font("Salaryman",Font.PLAIN,12);
	//TrueTypeFont unifont = new TrueTypeFont(font, false);
	
	public WaveProgress wavepbar;
	public LevelProgress lpbar;
	public BaseHealthBar bhb;
	
	float x1=55,y1=545;
	float x2=110,y2=575;
	float x3=120,y3=540;
	
	float wavepbarradius = 40;
	float lpbarradius = 50;
	float bhbradius = 18;
	
	
	Meter credits;
	Meter score;
	Meter nextTurretCost;
	
	public LevelGUI(float width, float height){
		super(height, height, height, height);
		credits = new Meter(x1, y1-12,new Color(200,100,0));
		score = new Meter(x3,y3-9,new Color(200,100,100));
		nextTurretCost = new Meter(x1,y1,new Color(0,50,200));
		
		bhb = new BaseHealthBar(x2,y2, bhbradius);
		updateTurretCostText();
	}
	
	public void update(){
		credits.setText(String.valueOf((int) Play.credits));
		score.setText(String.valueOf((int) Play.score));
	}
	
	public void draw(Graphics g){
		//g.setFont(unifont);
		g.setLineWidth(4);
		
		g.setColor(Color.black);
		g.fill(new Circle(x3,y3,18));
		g.setColor(Color.gray);
		g.setLineWidth(2);
		g.draw(new Circle(x3,y3,18));
		//health
		bhb.draw(g);
		
				
		//level progression
		lpbar.draw(g);
		wavepbar.draw(g);
		
		g.setColor(Color.black);
		g.fill(new Circle(x1,y1,25));
			
		
		g.setColor(Color.gray);
		g.setLineWidth(2);
		g.draw(new Circle(x1,y1,25));
			
		//text
		credits.draw(g);
		nextTurretCost.draw(g);
		score.draw(g);
	}
	
	public void setProgressBar(int val){
		wavepbar = new WaveProgress(x1,y1,val,wavepbarradius);
	}
	
	public void updateLevelProgress() {
		lpbar.update();
	}
	
	public void setLevelProgressBar(int val){
		lpbar = new LevelProgress(x1,y1,val,lpbarradius);
	}
	
	public void updateBaseHealthBar(){
		bhb.update();
	}
	
	public void updateTurretCostText(){
		nextTurretCost.setText(String.valueOf(Play.getNextTurretCost()));
	}
}
