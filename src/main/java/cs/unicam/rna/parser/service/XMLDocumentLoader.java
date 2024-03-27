package cs.unicam.rna.parser.service;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLDocumentLoader {
	
	public Document load(String path) {
		try {
			File fXmlFile = new File(path);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory
	                .newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(fXmlFile);
	        doc.getDocumentElement().normalize();
	        return doc;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
