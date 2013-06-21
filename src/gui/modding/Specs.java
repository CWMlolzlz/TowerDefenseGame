package gui.modding;

import game.Modding;
import gui.Panel;
import gui.Text;
import resources.types.TurretType;

public class Specs extends Panel{

	static TurretType ttype = new TurretType();
	
	static TextArea name = new TextArea(220,50, "Name",true,true,true);
	static TextArea cost = new TextArea(220,100, "Cost",true,false,false);
	static TextArea damage = new TextArea(220,150, "Damage",true,false,false);
	static TextArea range = new TextArea(220,200, "Range",true,false,false);
	
	
	public Specs(float x, float y, float w, float h) {
		super(x, y, w, h);

		addElement(new Text(230, 20, 50,"ID", Text.LEFT));
		
		addElement(name);
		addElement(cost);
		addElement(damage);
		addElement(range);
		addElement(new Button("Preview",220,500,Modding.PREVIEW));
		addElement(new Button("Save",380,500,Modding.SAVE_TURRET));
		
	}

	public static void updateTurretTypeData() {
		try{
			ttype.NAME = name.getText();
			ttype.COST = Integer.parseInt(cost.getText());
			ttype.bDamage = Integer.parseInt(damage.getText());
			ttype.bRange = Integer.parseInt(range.getText());
		}catch(Exception e){
			System.out.println("Cannot parse all information");
		}
	}
	

}
