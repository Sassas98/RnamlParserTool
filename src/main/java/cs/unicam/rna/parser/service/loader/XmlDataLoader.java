package cs.unicam.rna.parser.service.loader;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * classe astratta per ottenere il document xml da un path
 * e operare sui nodi
 */
public abstract class XmlDataLoader {
	
	protected Document loadXmlDocument(String path){
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

	/**
	 * se un nodo e' un elemento torna l'elemento, altrimenti null
	 * @param node
	 * @return
	 */
	protected Element getElement(Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			return (Element) node;
		} else {
			return null;
		}
	}

}
