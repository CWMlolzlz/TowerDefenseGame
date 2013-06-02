package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resources.types.EnemyType;
import resources.types.TurretType;

public class EnemyData{

	private ArrayList<EnemyType> etypes = new ArrayList<EnemyType>();
	
	public void loadEnemyTypes(){
		etypes.clear();
		try{
			
			File fXmlFile = new File("data/enemydata.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			//TURRET DATA
			Node turretDataNode = doc.getElementsByTagName("EnemyType").item(0); //<TurretData> Node
			
			NodeList turretNodes = turretDataNode.getChildNodes(); // <Turret> nodes
			for(int i = 0; i < turretNodes.getLength(); i++){
				Node contentsNode = turretNodes.item(i); //<Turret> node
				if(contentsNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) contentsNode;
					int id = Integer.parseInt(eElement.getAttribute("id"));
					String name = eElement.getAttribute("name");
					int health = Integer.parseInt(eElement.getAttribute("health"));
					int shield = Integer.parseInt(eElement.getAttribute("shield"));
					float speed = Float.parseFloat(eElement.getAttribute("speed"));
					float regen = Float.parseFloat(eElement.getAttribute("regeneration"));
					int reward = Integer.parseInt(eElement.getAttribute("reward"));
					int cost = Integer.parseInt(eElement.getAttribute("cost"));
										
					etypes.add(new EnemyType(id,name,health,shield,speed,regen,reward,cost));
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<EnemyType> getEnemyTypes(){return etypes;}
	
}
