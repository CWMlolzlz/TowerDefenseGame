package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelData{

	private String levelnum;
	
	private ArrayList<Node> path = new ArrayList<Node>();
	private ArrayList<Node> edgeA = new ArrayList<Node>();
	private ArrayList<Node> edgeB = new ArrayList<Node>();
	
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	
	public LevelData(String p){
		levelnum = p;
		loadLevelData();
	}
	
	private void loadLevelData(){
		try{
			Scanner scanner = new Scanner(new File("data/levels/level_"+levelnum+".DATA"));
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
		System.out.println("Path done");
		readNodes(sc, edgeA, "EDGEA");
		System.out.println("A done");
		readNodes(sc, edgeB, "EDGEB");
		System.out.println("B done");
		
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
				String[] splitline = line.split("\\s");
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
			System.out.println(line);
			if(line.contains("</WAVES>")){
				break;
			}
			if(!line.matches("\\s*")){
				Wave newWave = new Wave();
				while(!line.matches("END")){
					String[] splitline = line.split("\\t+");
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

class Node{
	public float x,y;
	public Node(float newx, float newy){
		x = newx;
		y = newy;
	}	
}

class Wave{
	
	ArrayList<SpawnData> spawnData = new ArrayList<SpawnData>();
		
	public void addSpawnData(String[] line){
		int id = Integer.parseInt(line[0]);
		int q = Integer.parseInt(line[1]);
		
		int i = Integer.parseInt(line[2]);
		int d = Integer.parseInt(line[3]);
		
		spawnData.add(new SpawnData(id,q,i,d));
	}
}

class SpawnData{
	
	public int ENEMYID, QUANTITY, DELAY;
	public int INTERVAL;
	
	public SpawnData(int id, int q, int i, int d){
		ENEMYID = id;
		QUANTITY = q;
		INTERVAL = i;
		DELAY = d;
	}
	
}
