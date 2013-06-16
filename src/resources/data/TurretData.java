package resources.data;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resources.types.BulletType;
import resources.types.TurretType;

public class TurretData{
	
	private ArrayList<TurretType> turrettypes = new ArrayList<TurretType>(); // in the form of a line read from file
	private ArrayList<int[]> upgrades = new ArrayList<int[]>(); // in the form of a line read from file
	private ArrayList<BulletType> bullettypes = new ArrayList<BulletType>(); // form of a line read from file
	
	public void loadUpgrades(){
		turrettypes.clear();
		upgrades.clear();
		bullettypes.clear();
		try{
			File fXmlFile = new File("data/turretdata.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			//TURRET DATA
			Node turretDataNode = doc.getElementsByTagName("TurretData").item(0); //<TurretData> Node
			
			NodeList turretNodes = turretDataNode.getChildNodes(); // <Turret> nodes
			for(int i = 0; i < turretNodes.getLength(); i++){
				Node contentsNode = turretNodes.item(i); //<Turret> node
				if(contentsNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) contentsNode;
					int id = Integer.parseInt(eElement.getAttribute("id"));
					String name = eElement.getAttribute("name");
					int cost = Integer.parseInt(eElement.getAttribute("cost"));
					int bDamage = Integer.parseInt(eElement.getAttribute("bDamage"));
					int bRange = Integer.parseInt(eElement.getAttribute("bRange"));
					int bRateOfFire = Integer.parseInt(eElement.getAttribute("bRateOfFire"));
					int bullets = Integer.parseInt(eElement.getAttribute("bullets"));
					int bulletTypeID = Integer.parseInt(eElement.getAttribute("bulletType"));
					String color = eElement.getAttribute("color");
					
					turrettypes.add(new TurretType(id,name,cost,bDamage,bRange,bRateOfFire,bullets,bulletTypeID,color));
					
				}
			}
			//upgrade branches			
			NodeList upgradeNodes = doc.getElementsByTagName("UpgradeBranches").item(0).getChildNodes();//get <Upgrade>...</Upgrade> nodes
						
			for(int i = 0; i < upgradeNodes.getLength();i++){
				
				Node upgradeNode = upgradeNodes.item(i);
				if(upgradeNode.getNodeName() == "Upgrade"){
					Element eUpgrade = (Element) upgradeNode;
					int ID = Integer.parseInt(eUpgrade.getAttribute("id"));
					NodeList contents = upgradeNode.getChildNodes();
					ArrayList<Integer> branchResults = new ArrayList<Integer>();
					for(int j = 0; j < contents.getLength(); j++){
						Node data = contents.item(j);
						String nodeName = data.getNodeName();
						
						if(nodeName!="#text"){
							Element e = (Element) data;
							branchResults.add(Integer.parseInt(e.getAttribute("result")));
						}
						
					}
					
					int[] results = new int[branchResults.size()];
					for(int k = 0; k < branchResults.size();k++){
						results[k] = branchResults.get(k);
					}
					
					upgrades.add(ID, results);
					
				}
				
			}
			
			
			//bullet types
			Node bulletTypeNode = doc.getElementsByTagName("BulletTypes").item(0);
						
			NodeList bulletNodes = bulletTypeNode.getChildNodes(); // <Turret> nodes
			for(int i = 0; i < bulletNodes.getLength(); i++){
				Node contentsNode = bulletNodes.item(i); //<Turret> node
				if(contentsNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) contentsNode;
					int id = Integer.parseInt(eElement.getAttribute("id"));
					String gName = eElement.getAttribute("graphicName");
					String color = eElement.getAttribute("color");
					int speed = Integer.parseInt(eElement.getAttribute("speed"));
					float accuracy = Float.parseFloat(eElement.getAttribute("accuracy"));
										
					bullettypes.add(new BulletType(id,gName,color,speed,accuracy));
					
				}
			}
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	
	
	
	public int[] getUpgradeBranches(int turretID){
		return upgrades.get(turretID);
	}
	
	public TurretType getTurretType(int ID){
		return turrettypes.get(ID);
	}
	
	public BulletType getBulletType(int bulletID){
		return bullettypes.get(bulletID);
	}
	
}






