package gui.modding;

public class Text extends GUIElement{

	private static float width = 180, height = 30;
	
	public Text(float newx, float newy, String t) {
		super(newx, newy, width, height);
		this.shape = null;
		text = t;
	}

	
	
}
