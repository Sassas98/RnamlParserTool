package cs.unicam.rna.parser.controller;

import java.util.ArrayList;
import java.util.List;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs.unicam.rna.parser.model.RnaFileType;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.service.RnamlDataLoader;
import cs.unicam.rna.parser.service.XmlDocumentLoader;

public class RnamlParserController {
	
	private List<RnaMolecule> molecules;
	private XmlDocumentLoader xmlLoader;
	private RnamlDataLoader rnamlLoader;
	private boolean loaded;
	
	
	public RnamlParserController() {
		xmlLoader = new XmlDocumentLoader();
		rnamlLoader = new RnamlDataLoader();
		loaded = false;
	}
	
	
	public void loadRnaml(String path) {
		molecules = new ArrayList<>();
		Document doc = xmlLoader.load(path);
		if(doc == null) {
			loaded = false;
			return;
		}
		NodeList moleculeList = doc.getElementsByTagName("molecule");
		for (int i = 0; i < moleculeList.getLength(); i++) {
            Node node = moleculeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                loadMolecule(i, (Element) node);
            }
		}
		loaded = true;
	}
	
	
	
	private void loadMolecule(int idMolecule, Element data) {
		 RnaMolecule molecule = new RnaMolecule(idMolecule);
         rnamlLoader.setMoleculeData(data);
         rnamlLoader.setReceiver(molecule);
         if(!rnamlLoader.loadData()) {
         	loaded = false;
         	return;
         }
         molecules.add(molecule);
	}
	
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public void SaveLoadedData(String path, RnaFileType type) {
		//TODO
	}
	
	
}
