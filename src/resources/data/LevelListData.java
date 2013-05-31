package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelListData{

	private ArrayList<LevelData> levelData = new ArrayList<LevelData>();
	
	public LevelListData(){
		loadLevelData();
	}
	
	private void loadLevelData(){
		try{
			Scanner scanner = new Scanner(new File("data/leveldatalist.DATA"));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
}

