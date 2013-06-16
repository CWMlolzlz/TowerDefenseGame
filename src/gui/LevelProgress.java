package gui;

import org.newdawn.slick.Color;

public class LevelProgress extends ProgressBar{

	public LevelProgress(float x, float y, int max, float r) {
		super(x,y,max,r);
		this.color = new Color(50,50,255);
	}
	
	
}
