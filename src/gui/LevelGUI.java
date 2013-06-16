package gui;

import java.awt.Font;

import game.Play;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;

import resources.Fonts;

public class LevelGUI{
	
	//Font font = new Font("Salaryman",Font.PLAIN,12);
	//TrueTypeFont unifont = new TrueTypeFont(font, false);
	
	ProgressBar wavepbar;
	LevelProgress lpbar;
	static BaseHealthBar bhb;
	
	float x1=55,y1=545;
	float x2=115,y2=565;
	
	float wavepbarradius = 40;
	float lpbarradius = 50;
	float bhbradius = 15;
	
	
	Meter credits;
	static Meter nextTurretCost;
	
	public LevelGUI(float width, float height){
		credits = new Meter(x1, y1-6, 30, new Color(200,100,0), Fonts.salaryman_bold);
		nextTurretCost = new Meter(x1,y1+6,0,new Color(0,50,200), Fonts.salaryman_bold);
		bhb = new BaseHealthBar(x2,y2, bhbradius);
		updateTurretCostText();
	}
	
	public void update(){
		credits.setValue((int) Play.credits);
	}
	
	public void draw(Graphics g){
		//g.setFont(unifont);
		g.setLineWidth(4);
		//health
		bhb.draw(g);
		g.setColor(Color.gray);
		g.setLineWidth(2);
		g.draw(new Circle(x2,y2,bhbradius));
				
		//level progression
		lpbar.draw(g);
		wavepbar.draw(g);
		
		g.setColor(Color.black);
		//g.fill(new Circle(x1,y1,pbarradius));
		g.fill(new Circle(x1,y1,25));
		
		//g.fill(new Circle(x2,y2,bhbradius));
		
		g.setColor(Color.gray);
		g.setLineWidth(2);
		
		g.draw(new Circle(x1,y1,wavepbarradius));
		g.draw(new Circle(x1,y1,lpbarradius));
		g.draw(new Circle(x1,y1,25));
		
		
		
		
		
		//text
		credits.draw(g);
		nextTurretCost.draw(g);
	}
	
	public void updateWaveProgressBar(boolean bad){
		wavepbar.update(bad);
	}
	
	public void setProgressBar(int val){
		wavepbar = new ProgressBar(x1,y1,val,wavepbarradius);
	}
	
	public void updateLevelProgress() {
		lpbar.update(false);
	}
	
	public void setLevelProgressBar(int val){
		lpbar = new LevelProgress(x1,y1,val,lpbarradius);
	}
	
	public static void updateBaseHealthBar(){
		bhb.update();
	}
	
	public static void updateTurretCostText(){
		nextTurretCost.setValue(Play.getNextTurretCost());
	}
}
