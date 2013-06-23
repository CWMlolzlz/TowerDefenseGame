package resources.data;

import java.util.ArrayList;

import resources.Turret;

public class SaveData{
	
	public String path;
	public String levelname;
	public int wave;
	public int basehealth;
	public float credits;
	public float score;
	public ArrayList<Turret> turrets = new ArrayList<Turret>();
	
	public SaveData(String levelpath, String name, int w, int b, float c, float s, ArrayList<Turret> t){
		path = levelpath;
		levelname = name;
		wave = w;
		basehealth = b;
		credits = c;
		score = s;
		turrets = t;
	}
	
}