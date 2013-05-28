package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelDataList{

	private ArrayList<LevelData> levelData = new ArrayList<LevelData>();
	
	public LevelDataList(){
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

