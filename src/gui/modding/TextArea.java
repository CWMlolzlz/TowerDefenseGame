package gui.modding;

import gui.GUIElement;

public class TextArea extends GUIElement{

	private static float width = 180, height = 30;
	String defaulttext;
	boolean focused = false;
	
	public boolean alpha = true;
	public boolean digits = true;
	public boolean special = true;
	
	public TextArea(float newx, float newy, String t, boolean a, boolean d, boolean s){
		super(newx, newy,width,height);
		defaulttext = t;
		
		alpha = a;
		digits = d;
		special = s;
		
		setToDefault();
	}

	public TextArea(int newx, int newy, String string, String character){
		super(newx, newy,width,height);
	}

	public void select(){
		if(isDefaultText()){
			text = "";
		}
	}
	
	public void addChar(char c){
		text += String.valueOf(c);
	}

	public void backSpace() {
		if(!(text.length() == 0 || text == defaulttext)){
			text = text.substring(0, text.length()-1);
		}else{
			text = defaulttext;
		}
		
	}
	
	public void setToDefault(){
		text = defaulttext;
	}
	
	public boolean isDefaultText(){return text==defaulttext;}
	public String getText(){return text;}
}
