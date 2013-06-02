package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import resources.Node;
import resources.Wave;

public class LevelData{

	private String levelpath;
	
	private ArrayList<Node> path = new ArrayList<Node>();
	private ArrayList<Node> edgeA = new ArrayList<Node>();
	private ArrayList<Node> edgeB = new ArrayList<Node>();
	
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	
	public LevelData(String p){
		levelpath = p;
		loadLevelData();
	}
	
	private void loadLevelData(){
		try{
			Scanner scanner = new Scanner(new File(levelpath));
			loadNodes(scanner);
			loadSpawnWaves(scanner);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private void loadNodes(Scanner sc){
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			if(nextLine.contains("<NODES>")){
				break;
			}
		}
		readNodes(sc, path, "PATH");
		readNodes(sc, edgeA, "EDGEA");
		readNodes(sc, edgeB, "EDGEB");	
	}
	
	private void readNodes(Scanner sc, ArrayList<Node> list, String tag){
		while(sc.hasNextLine()){
			String nextLine = sc.nextLine();
			if(nextLine.contains("<" + tag + ">")){
				break;
			}
		}
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</"+tag+">")){
				break;
			}else{
				String[] splitline = line.split("\\s+");
				int xcoord = Integer.parseInt(splitline[0]);
				int ycoord = Integer.parseInt(splitline[1]);
				list.add(new Node(xcoord, ycoord));
			}
		}
	}
	
	private void loadSpawnWaves(Scanner sc){
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<WAVES>")){
				break;
			}
		}
		sc.nextLine();
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</WAVES>")){
				break;
			}
			if(!line.matches("\\s*")){
				Wave newWave = new Wave();
				while(!line.matches("END")){
					String[] splitline = line.split("\\s+");
					newWave.addSpawnData(splitline);
					line = sc.nextLine();
				}
				waves.add(newWave);
			}
			
		}
		
	}

	public ArrayList<Node> getPath() {return path;}
	public ArrayList<Node> getEdgeA() {return edgeA;}
	public ArrayList<Node> getEdgeB() {return edgeB;}
	public ArrayList<Wave> getWaveData(){return waves;}
}


