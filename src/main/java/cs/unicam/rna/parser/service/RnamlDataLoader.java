package cs.unicam.rna.parser.service;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs.unicam.rna.parser.abstraction.RnaReceiver;
import cs.unicam.rna.parser.exception.RnaParsingException;

public class RnamlDataLoader {
	
	RnaReceiver receiver;
	Element moleculeData;
	
	public void setReceiver(RnaReceiver receiver) {
		this.receiver = receiver;
	}

	public void setMoleculeData(Element moleculeData) {
		this.moleculeData = moleculeData;
	}
	
	/**
	 * carica i dati
	 * @return true se va a buon fine, false altrimenti
	 */
	public boolean loadData() {
		try {
			loadSequence();
			loadPairs();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * carica la sequenza
	 * @throws RnaParsingException
	 */
	private void loadSequence() throws RnaParsingException {
		String sequence = moleculeData.getElementsByTagName("sequence")
				.item(0).getTextContent();
		for(char c : sequence.toCharArray()) {
			receiver.addRibonucleotide(c);
		}
	}
	
	/**
	 * carica tutte le coppie
	 * @throws RnaParsingException
	 */
	private void loadPairs() throws RnaParsingException {
		NodeList pairs = moleculeData.getElementsByTagName("base-pair");
        for (int i = 0; i < pairs.getLength(); i++) {
            Element pair = getElement(pairs.item(i));
            if (pair != null) {
                loadPair(pair);
            }
        }
	}
	
	/**
	 * gestisce i dati della coppia
	 * @param pair
	 * @throws RnaParsingException
	 */
	private void loadPair(Element pair) throws RnaParsingException {
		NodeList positions = pair.getElementsByTagName("position");
        if(positions.getLength() == 2) {
        	String first = positions.item(0).getTextContent();
        	String second = positions.item(1).getTextContent();
        	addPair(first, second);
        } else {
        	loadPairAlt(pair);
        }
	}
	
	/**
	 * gestisce i dati nel caso della dicitura alternativa
	 * @param pair
	 * @throws RnaParsingException
	 */
	private void loadPairAlt(Element pair) throws RnaParsingException {
		NodeList baseList = pair.getElementsByTagName("base-id");
		Element base1 = getElement(baseList.item(0)), base2 = getElement(baseList.item(0));
		if(base1 != null && base2 != null) {
	    	String first = base1.getAttribute("base-id");
	    	String second = base2.getAttribute("base-id");
	    	addPair(first, second);
		}
	}
	
	/**
	 * aggiunge la coppia
	 * @param a
	 * @param b
	 * @throws RnaParsingException
	 */
	private void addPair(String a, String b) throws RnaParsingException {
		int first = Integer.parseInt( a );
    	int second = Integer.parseInt( b );
    	this.receiver.addPair(first, second);
	}
	
	/**
	 * se un nodo e' un elemento torna l'elemento, altrimenti null
	 * @param node
	 * @return
	 */
	private Element getElement(Node node) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			return (Element) node;
		} else {
			return null;
		}
	}
	
}
