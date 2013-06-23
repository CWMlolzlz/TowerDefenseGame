package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resources.data.SaveData;

public class Save{

	static File file = new File("data/save.xml");
	
	public static void newSave(String levelpath, String levelname, int wave,int basehealth, float c, float s, ArrayList<Turret> t){
		file.setWritable(true);
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element save = doc.createElement("Save");
			Element levelstatus = doc.createElement("Level");
			
			Attr path = doc.createAttribute("path");
			path.setValue(levelpath);
			Attr name = doc.createAttribute("name");
			name.setValue(levelpath);
			Attr wavenum = doc.createAttribute("wavenum");
			wavenum.setValue(String.valueOf(wave));
			Attr bh = doc.createAttribute("basehealth");
			bh.setValue(String.valueOf(basehealth));
			Attr credits = doc.createAttribute("credits");
			credits.setValue(String.valueOf(c));
			Attr score = doc.createAttribute("score");
			score.setValue(String.valueOf(s));
			
			levelstatus.setAttributeNode(path);
			levelstatus.setAttributeNode(name);
			levelstatus.setAttributeNode(wavenum);
			levelstatus.setAttributeNode(credits);
			levelstatus.setAttributeNode(score);
			levelstatus.setAttributeNode(bh);
			
			Element turrets = doc.createElement("Turrets");
			for(int i = 0; i < t.size(); i++){
				Turret turret = t.get(i);
				int ID = turret.ID;
				float x = turret.x;
				float y = turret.y;
				int value = turret.value;
				Element e = doc.createElement("Turret");
				e.setAttribute("id", String.valueOf(ID));
				e.setAttribute("x", String.valueOf(x));
				e.setAttribute("y", String.valueOf(y));
				e.setAttribute("value", String.valueOf(value));
				turrets.appendChild(e);
			}
					
			doc.appendChild(save);
			save.appendChild(levelstatus);
			save.appendChild(turrets);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
	 		transformer.transform(source, result);
	 		
		}catch(Exception e) {
			e.printStackTrace();
		}
		file.setReadOnly();
	}
	
	public static SaveData loadSave(){
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			Node levelNode = doc.getElementsByTagName("Level").item(0); //<Level> Node
			if(levelNode.getNodeType() == Node.ELEMENT_NODE){
			Element e = (Element) levelNode;
			int basehealth = Integer.parseInt(e.getAttribute("basehealth"));
			float credits = Float.parseFloat(e.getAttribute("credits"));
			String name = e.getAttribute("name");
			String path = e.getAttribute("path");
			float score = Float.parseFloat(e.getAttribute("score"));
			int wavenum = Integer.parseInt(e.getAttribute("wavenum"));
			
			ArrayList<Turret> turrets = new ArrayList<Turret>();
			NodeList turretNodeList = doc.getElementsByTagName("Turrets").item(0).getChildNodes(); // <Turret> nodes
			for(int i = 0; i < turretNodeList.getLength(); i++){
				Node contentsNode = turretNodeList.item(i); //<Turret> node
				if(contentsNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) contentsNode;
					int id = Integer.parseInt(eElement.getAttribute("id"));
					float x = Float.parseFloat(eElement.getAttribute("x"));
					float y = Float.parseFloat(eElement.getAttribute("y"));
					int value = Integer.parseInt(eElement.getAttribute("value"));
					Turret t = new Turret();
					t.place(value);
					t.updatePlacement(x, y);
					t.upgrade(id);
					turrets.add(t);
				}
			}
			return new SaveData(path,name, wavenum, basehealth,credits, score, turrets);
			}
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean saveExists(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			return (br.readLine() != null);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}


