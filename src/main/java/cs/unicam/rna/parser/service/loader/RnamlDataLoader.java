package cs.unicam.rna.parser.service.loader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.utility.RnamlPairsLoader;

/**
 * classe per il caricamento di dati da un rnaml
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class RnamlDataLoader extends XmlDataLoader implements RnaDataLoader {
	/**
	 * Caricatore di coppie per l'rnaml
	 */
	private RnamlPairsLoader pairsLoader = new RnamlPairsLoader();

	@Override
	public synchronized RnaFileData getData(String path) {
		RnaFileData data = new RnaFileData();
		Document doc = loadXmlDocument(path);
		if(doc == null) {
			return null;
		}
		NodeList moleculeList = doc.getElementsByTagName("molecule");
		for (int i = 0; i < moleculeList.getLength(); i++) {
            Element node = getElement(moleculeList.item(i));
			RnaMolecule molecule = getMolecule(node, i);
			if(molecule == null)
				return null;
			data.addMolecule(molecule);
		}
		checkInfoData(doc, data);
		return data;
	}

	/**
	 * metodo per ottenere una molecola da un elemento
	 * di un xml e un indice
	 * @param moleculeData elemento di xml
	 * @param index indice
	 * @return molecola se il parsing va correttamente, null altrimenti
	 */
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

	
	/**
	 * metodo che controlla se ci sono informazioni accessorie da inserire
	 * nei dati dal documento e, nel caso ci siano, le inserisce
	 * @param doc documento da controllare
	 * @param data dati in cui salvare le informazioni
	 */
	private void checkInfoData(Document doc, RnaFileData data) {
		NodeList urls = doc.getElementsByTagName("url");
		if(urls.getLength() > 0){
			data.setReferenceLink(urls.item(0).getTextContent());
		}
		NodeList moleculeList = doc.getElementsByTagName("molecule");
		if(moleculeList.getLength() > 0){
        	Element mol = getElement(moleculeList.item(0));
			String db = mol.getAttribute("database-ids");
			if(!"".equals(db)){
				data.setAccessionNumber(db);
			}
			NodeList identities = mol.getElementsByTagName("identity");
			if(identities.getLength() > 0) {
				String name = getElement(identities.item(0)).getElementsByTagName("name").item(0).getTextContent();
				data.setOrganism(name);
			}
		}
	}

	
}
