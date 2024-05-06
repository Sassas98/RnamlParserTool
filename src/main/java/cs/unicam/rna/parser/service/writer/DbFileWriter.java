package cs.unicam.rna.parser.service.writer;

import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.utility.DotBracketSequenceGenerator;

/**
 * Classe per scere i dati nel formato DB
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class DbFileWriter extends TextFileWriter {

	/**
	 * Generatore della sequenza secondaria del formato DB
	 */
	private final DotBracketSequenceGenerator sequenceGenerator = new DotBracketSequenceGenerator();
	
	@Override
	public synchronized boolean writeAndSave(RnaFileData molecules, String path) {
		data = "";
		setFileInfo(molecules);
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}

	/**
	 * Metodo per scrivere in formato DB i dati contenuti in una data molecola
	 * @param m molecola i cui dati vanno scritti
	 */
	private void writeMolecule(RnaMolecule m) {
		data += m.getSequence() + "\n";
		data += sequenceGenerator.writeSequence(m);
	}

}