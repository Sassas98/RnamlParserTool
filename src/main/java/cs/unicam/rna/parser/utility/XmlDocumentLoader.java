package cs.unicam.rna.parser.utility;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XmlDocumentLoader {
	
	public Document load(String path){
		try {
			File fXmlFile = new File(path);
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(fXmlFile);
		    doc.getDocumentElement().normalize();
		    return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
