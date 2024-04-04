package cs.unicam.rna.parser.controller;

import java.util.ArrayList;
import java.util.List;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.service.RnamlDataLoader;
import cs.unicam.rna.parser.utility.FileSaver;
import cs.unicam.rna.parser.utility.RnaHandlerBuilder;
import cs.unicam.rna.parser.utility.XmlDocumentLoader;

public class RnamlParserController {
	
	private List<RnaMolecule> molecules;
	private XmlDocumentLoader xmlLoader;
	private RnaHandlerBuilder builder;
	private FileSaver saver;
	private boolean loaded;
	
	
	public RnamlParserController() {
		xmlLoader = new XmlDocumentLoader();
		builder = new RnaHandlerBuilder();
		saver = new FileSaver();
		loaded = false;
	}
	
	
	public void loadRna(String path) {
		molecules = new ArrayList<>();
		loaded = true;
		RnaDataLoader loader = builder.buildDataLoader(path);
		loadRnaFromXml(path, loader);
	}
	
	
	private void loadRnaFromXml(String path, RnaDataLoader loader) {
		Document doc = xmlLoader.load(path);
		if(doc == null) {
			loaded = false;
			return;
		}
		NodeList moleculeList = doc.getElementsByTagName("molecule");
		for (int i = 0; i < moleculeList.getLength(); i++) {
            Node node = moleculeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                loadMolecule(i, node, loader);
            }
		}
	}


	private void loadMolecule(int idMolecule, Object data, RnaDataLoader loader) {
		 RnaMolecule molecule = new RnaMolecule(idMolecule);
         loader.setMoleculeData(data);
         loader.setReceiver(molecule);
         if(!loader.loadData()) {
         	loaded = false;
         	return;
         }
         molecules.add(molecule);
	}
	
	
	public boolean isLoaded() {
		return loaded;
	}
	
	
	public boolean SaveLoadedData(String path) {
		if(path == null || (!loaded)) {
			return false;
		}
		RnaFileWriter writer = this.builder.buildFileWriter(path);
		String data = writer.write(molecules);
		return saver.save(path, data);
	}
	
	
}
