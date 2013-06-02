package resources.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resources.types.BulletType;
import resources.types.TurretType;
import resources.types.UpgradeBranch;

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
			System.out.println(upgradeNodes.getLength() + " upgrade Nodes");
			
			for(int i = 0; i < upgradeNodes.getLength();i++){
				
				Node upgradeNode = upgradeNodes.item(i);
				System.out.println("Current Element: " + upgradeNode.getNodeName());
				if(upgradeNode.getNodeName() == "Upgrade"){
					Element eUpgrade = (Element) upgradeNode;
					int ID = Integer.parseInt(eUpgrade.getAttribute("id"));
					System.out.println(ID);
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
			NodeList bulletTypeList = doc.getElementsByTagName("BulletTypes");
			
				
			
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println(e);
		}
		
		
		
		
		
		
		
		
		
		try{
			Scanner scanner = new Scanner(new File("data/turretdata.DATA"));
			//loadTurretTypes(scanner);
			//loadUpgradeBranches(scanner);
			loadBulletTypes(scanner);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}		
	}
	
	private String getNodeVal(NodeList nl, int index){
		Node node = nl.item(index);
		if(node.getNodeType() == Node.ELEMENT_NODE){
			Element e = (Element) node;
			return e.getAttribute("val");
		}
		return null;
	}
	
	private void loadTurretTypes(Scanner sc){//optimise load functions to take ArrayList to add to and String to look for
		
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<TURRETDATA>")){
				break;
			}
		}
		sc.nextLine();//skips layout info
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</TURRETDATA>")){
				break;
			}else{
				String[] splitline = line.split("\\s+");
				//turrettypes.add(new TurretType(splitline));
			}
		}
	}
	
	private void loadUpgradeBranches(Scanner sc){//optimise load functions to take ArrayList to add to and String to look for
		
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<UPGRADES>")){
				break;
			}
		}
		sc.nextLine();//skips layout info
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</UPGRADES>")){
				break;
			}else{
				String[] splitline = line.split("\\s+");
				//upgrades.add(new UpgradeBranch(splitline));
			}
		}
		
	}
	
	private void loadBulletTypes(Scanner sc){//optimise load functions to take ArrayList to add to and String to look for
		
		while(sc.hasNextLine()){
			if(sc.nextLine().contains("<BULLETTYPES>")){
				break;
			}
		}
		sc.nextLine(); //skips layout info
		while(sc.hasNextLine()){
			String line = sc.nextLine();
			if(line.contains("</BULLETTYPES>")){
				break;
			}else{
				String[] splitline = line.split("\\s+");
				bullettypes.add(new BulletType(splitline));
			}
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






