package gui;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class ProgressBar{
	
	public Shape outline;
	public Shape fill;
	public String bottomtext;
	public String toptext;
	
	public float percent;	
	public float progress;
	public float progressend;
	
	private int x1 = 20, x2 = 760, y1 = 540, y2 = 6;
	public int textx = x1 + x2/2, texty = y1 + y2 + 5;
	
	public ProgressBar(int max){
		progress = 0;
		progressend = max;
		outline = new Rectangle(x1, y1, x2, y2);
		fill = new Rectangle(x1,y1,0,y2);
		update(0);
	}
	
	public void update(int val){
		progress += val;
		percent = progress/progressend;
		toptext = (int)(percent*100)+"%";
		fill = new Rectangle(x1,y1,(percent*x2),y2);
	}
	
}
