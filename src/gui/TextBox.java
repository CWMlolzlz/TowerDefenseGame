package gui;


public class TextBox extends GUIElement{

	private static float width = 300, height = 30;
	String defaulttext;
	boolean focused = false;
	public String truetext = "";
	
	//public String text;
	String character;
	
	public TextBox(int newx, int newy, String string, String c){
		super(newx,newy,width,height);
		character = c;
		text = string;
		defaulttext = string;
	}

	public void select(){
		if(isDefaultText()){
			truetext = "";
			text = "";
		}
	}
	
	public void addChar(char c){
		truetext += String.valueOf(c);
		if(character == null){
			text += String.valueOf(c);
		}else{
			text += character;
		}
	}

	public void backSpace() {
		if(!(text.length() == 0 || text == defaulttext)){
			text = text.substring(0, text.length()-1);
			truetext = truetext.substring(0, truetext.length()-1);
		}else{
			truetext = "";
			text = defaulttext;
		}
		
	}
	
	public void setToDefault(){
		text = defaulttext;
	}
	
	public boolean isEmpty(){
		return (truetext.isEmpty());
	}
	
	public boolean isDefaultText(){return text==defaulttext;}
	public String getText(){return text;}
}
