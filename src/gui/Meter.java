package gui;

import org.newdawn.slick.Color;

public class Meter extends Text{
	
	public float x,y;
	
	public Meter(float newx, float newy, Color c){
		super(newx-1, newy, 0, "", Text.CENTER);
		x = newx;
		y = newy;
		color = c;
		//font = f;
	}
	
}
