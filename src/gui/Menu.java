package gui;

import game.Launch;

public class Menu extends Panel{

	public static float width = 200, height = 600;
	
	public float endx,endy;
	
	public int tick = 0;
	public int length = 45;
	float k = 1;
	
	public Menu(float newx, float newy, String t, boolean still){
		super(newx, newy, width, height);
		if(still){
			x = newx;
			tick = length;
		}else{
			endx = newx;
			endy = newy;
			k = (float)(Launch.gamecontainer.getWidth()-endx)/(length*length);
		}
		addElement(new Text(20,20,50,t,Text.LEFT));
		update();
	}
	
	public void update(){
		if(tick < length){
			tick++;
			x = k*(float)Math.pow((tick-length),2)+endx;
		}
		y=endy;
		updateShape();
		for(int j = 0; j < guielements.size(); j++){
			GUIElement ge = guielements.get(j);
			if(ge instanceof Button){
				Button button = (Button) ge;
				button.x = x+button.origx;
				button.y = y+button.origy;
				button.updateShape();
				
			}
			if(ge instanceof Text){
				Text t = (Text)ge;
				t.x = x+t.origx;
				t.y = y+t.origy;
			}
		}
	}
}
