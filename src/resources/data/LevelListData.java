package resources.data;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LevelListData{

	public ArrayList<LevelInformation> levelinfo = new ArrayList<LevelInformation>();
	
	public void loadLevelData(){
		levelinfo.clear();
		try{
			File fXmlFile = new File("data/levellistdata.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Level");
			for(int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					String levelID = eElement.getAttribute("path");
					String name = eElement.getAttribute("name");
					String description = eElement.getAttribute("description");
					levelinfo.add(new LevelInformation(levelID, name,description));
				}
			}
				
		}catch(Exception e) {
			System.out.println("Uh oh");
		}
	}
	
}



