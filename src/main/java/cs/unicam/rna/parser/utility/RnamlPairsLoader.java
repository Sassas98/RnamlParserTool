package cs.unicam.rna.parser.utility;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaChain;

/**
 * Classe utile al caricamento delle coppie di un file rnaml
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnamlPairsLoader {
    
    /**
	 * carica tutte le coppie
	 * @throws RnaParsingException
	 */
	public void loadPairs(Element chainData, RnaChain chain) throws RnaParsingException {
		NodeList pairs = chainData.getElementsByTagName("base-pair");
        for (int i = 0; i < pairs.getLength(); i++) {
            Element pair = getElement(pairs.item(i));
            if (pair != null)
                loadPair(pair, chain, isCanonical(pair));
        }
    }

	
	private boolean isCanonical(Element pair) {
		NodeList bol = pair.getElementsByTagName("bond-orientation");
		if(bol.getLength() == 0)
			return true;
		Element bo = getElement(bol.item(0));
		if(bo != null)
			return bo.getTextContent().equals("c");
		return true;
	}
	
	
	/**
	 * gestisce i dati della coppia
	 * @param pair
	 * @throws RnaParsingException
	 */
	private void loadPair(Element pair, RnaChain chain, boolean isCanonical) throws RnaParsingException {
		NodeList positions = pair.getElementsByTagName("position");
        if(positions.getLength() == 2) {
			String first = positions.item(0).getTextContent();
			String second = positions.item(1).getTextContent();
			addPair(first, second, chain, isCanonical);
        }
	}
	
	/**
	 * aggiunge la coppia
	 * @param a
	 * @param b
	 * @throws RnaParsingException
	 */
	private void addPair(String a, String b, RnaChain chain, boolean isCanonical) throws RnaParsingException {
		int first = Integer.parseInt( a );
    	int second = Integer.parseInt( b );
		if(isCanonical)
    		chain.addPair(first, second);
		else chain.addTertiaryPair(first, second);
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
