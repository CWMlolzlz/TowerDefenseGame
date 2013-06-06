package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resources.PathPoint;
import resources.Wave;
import resources.types.TurretType;

public class LevelData{

	private String levelpath;
	
	private ArrayList<PathPoint> path = new ArrayList<PathPoint>();
	//private ArrayList<Node> edgeA = new ArrayList<Node>();
	//private ArrayList<Node> edgeB = new ArrayList<Node>();
	private ArrayList<Shape> edges = new ArrayList<Shape>();
	
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	
	public LevelData(String p){
		levelpath = p;
		loadLevelData();
	}
	
	private void loadLevelData(){
		edges.clear();
		path.clear();
		waves.clear();
		try{
			File fXmlFile = new File(levelpath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			//PathPoints
			Node pathPointsNode = doc.getElementsByTagName("PathPoints").item(0); //<PathPoints> Node
			NodeList pathNodes = pathPointsNode.getChildNodes(); //<Path> <Edge> nodes
			for(int i = 0; i < pathNodes.getLength(); i++){
				Node pathNode = pathNodes.item(i); //<Path> or <Edge> node
				if(pathNode.getNodeType() == Node.ELEMENT_NODE){
					NodeList points = pathNode.getChildNodes(); //<Point> nodes
					Polygon edgepoly = new Polygon();
					edgepoly.setClosed(false);
					for(int j = 0; j < points.getLength(); j++){
						Node point = points.item(j);
						if(point.getNodeType() == Node.ELEMENT_NODE){
							//System.out.println(point.getNodeName() + ", " + point.)
							Element e = (Element) point;
							float xcoord = Float.parseFloat(e.getAttribute("x"));
							float ycoord = Float.parseFloat(e.getAttribute("y"));
							if(pathNode.getNodeName() == "Path"){
								path.add(new PathPoint(xcoord, ycoord));
							}else{
								edgepoly.addPoint(xcoord, ycoord);
							}
						}					
					}
					if(edgepoly.getPointCount() != 0){edges.add(edgepoly);}
				}
			}
			
			//WaveData
			Node waveDataNode = doc.getElementsByTagName("WaveData").item(0);
			NodeList waveNodes = waveDataNode.getChildNodes(); //<Wave> nodes
			for(int i = 0; i < waveNodes.getLength(); i++){
				Node waveNode = waveNodes.item(i); //<Wave> node
				if(waveNode.getNodeType() == Node.ELEMENT_NODE){
					NodeList points = waveNode.getChildNodes(); //<Spawn> nodes
					Wave newWave = new Wave();
					for(int j = 0; j < points.getLength(); j++){
						Node point = points.item(j); //<Spawn> node
						if(point.getNodeType() == Node.ELEMENT_NODE){
							Element e = (Element) point;
							int eID = Integer.parseInt(e.getAttribute("enemyID"));
							int quantity = Integer.parseInt(e.getAttribute("quantity"));
							int freq = Integer.parseInt(e.getAttribute("freq"));
							int delay = Integer.parseInt(e.getAttribute("delay"));
							newWave.addSpawnData(eID, quantity, freq,delay);
						}					
					}
					waves.add(newWave);
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

	public ArrayList<PathPoint> getPath() {return path;}
	//public ArrayList<Node> getEdgeA() {return edgeA;}
	//public ArrayList<Node> getEdgeB() {return edgeB;}
	public ArrayList<Shape> getEdges(){return edges;}
	public ArrayList<Wave> getWaveData(){return waves;}
}


