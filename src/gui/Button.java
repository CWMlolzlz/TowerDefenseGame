package gui;

public class Button extends GUIElement{

	private int EVENT;
		
	public float originalx, originaly;
	
	public Button(String t, float newx, float newy, int e) {
		super(newx, newy,160,30);
		originalx = newx;
		originaly = newy;
		EVENT = e;
		text = t;
	}
	
	public int getEvent(){
		return EVENT;
	}

}
