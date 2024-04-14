package cs.unicam.rna.parser.service.loader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.utility.RnamlPairsLoader;

public class RnamlDataLoader extends XmlDataLoader implements RnaDataLoader {

	private RnamlPairsLoader pairsLoader = new RnamlPairsLoader();

	@Override
	public RnaFileData getData(String path) {
		RnaFileData data = new RnaFileData();
		Document doc = loadXmlDocument(path);
		if(doc == null) {
			return null;
		}
		NodeList moleculeList = doc.getElementsByTagName("molecule");
		for (int i = 0; i < moleculeList.getLength(); i++) {
            Element node = getElement(moleculeList.item(i));
            if (node != null) {
				RnaMolecule molecule = getMolecule(node, i);
				if(molecule == null)
					return null;
				data.addMolecule(molecule);
            }
		}
		return data;
	}
	
	public RnaMolecule getMolecule(Element moleculeData, int index) {
		RnaMolecule molecule = new RnaMolecule(index + 1);
		try {
			loadSequence(moleculeData, molecule);
			this.pairsLoader.loadPairs(moleculeData, molecule);
			return molecule;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * carica la sequenza
	 * @throws RnaParsingException
	 */
	private void loadSequence(Element moleculeData, RnaMolecule molecule) throws RnaParsingException {

		Element sequenceNode = getElement(moleculeData.getElementsByTagName("sequence")
				.item(0));
		NodeList list = sequenceNode.getElementsByTagName("seq-data");
				
		String sequence = list.getLength() > 0 ? 
							getElement(list.item(0)).getTextContent()
							.replace(" ", "").replace("\n", "")
							.replace("\t", "").replace("\r", "").toUpperCase() 
							: sequenceNode.getTextContent();
		for(char c : sequence.toCharArray()) {
			molecule.addRibonucleotide(c);
		}
	}
	
}
