package cs.unicam.rna.parser.utility;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.service.loader.XmlDataLoader;

/**
 * Classe utile al caricamento delle coppie di un file rnaml
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnamlPairsLoader extends XmlDataLoader {
    
    /**
	 * carica tutte le coppie
	 * @throws RnaParsingException
	 */
	public void loadPairs(Element moleculeData, RnaMolecule molecule) throws RnaParsingException {
		NodeList pairs = moleculeData.getElementsByTagName("base-pair");
        for (int i = 0; i < pairs.getLength(); i++) {
            Element pair = getElement(pairs.item(i));
            if (pair != null) {
                loadPair(pair, "position", molecule);
            }
        }
	}
	
	/**
	 * gestisce i dati della coppia
	 * @param pair
	 * @throws RnaParsingException
	 */
	private void loadPair(Element pair, String nodeName, RnaMolecule molecule) throws RnaParsingException {
		NodeList positions = pair.getElementsByTagName(nodeName);
        if(positions.getLength() == 2) {
        	if(!isNumber(positions.item(0).getTextContent())) {
        		loadPairAlt(pair, nodeName, molecule);
        	} else {
            	String first = positions.item(0).getTextContent();
            	String second = positions.item(1).getTextContent();
            	addPair(first, second, molecule);
        	}
        } else {
        	if(!nodeName.equals("base-id"))
        		loadPair(pair, "base-id", molecule);
        }
	}

	private boolean isNumber(String s){
		if(s == null || s.length() == 0)
			return false;
		for(char c : s.toCharArray()){
			if(c < '0' || c > '9')
				return false;
		}
		return true;
	}
	
	/**
	 * gestisce i dati nel caso della dicitura alternativa
	 * @param pair
	 * @throws RnaParsingException
	 */
	private void loadPairAlt(Element pair, String nodeName, RnaMolecule molecule) throws RnaParsingException {
		NodeList baseList = pair.getElementsByTagName(nodeName);
		Element base1 = getElement(baseList.item(0)), base2 = getElement(baseList.item(1));
		if(base1 != null && base2 != null) {
	    	String first = base1.getAttribute("base-id");
	    	String second = base2.getAttribute("base-id");
	    	addPair(first, second, molecule);
		}
	}
	
	/**
	 * aggiunge la coppia
	 * @param a
	 * @param b
	 * @throws RnaParsingException
	 */
	private void addPair(String a, String b, RnaMolecule molecule) throws RnaParsingException {
		int first = Integer.parseInt( a );
    	int second = Integer.parseInt( b );
    	molecule.addPair(first, second);
	}

}
