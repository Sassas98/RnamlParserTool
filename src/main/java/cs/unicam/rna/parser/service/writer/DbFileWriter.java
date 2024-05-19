package cs.unicam.rna.parser.service.writer;

import cs.unicam.rna.parser.model.RnaFileData;
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
		molecules.getMolecules().stream().forEach( m -> data += m.getSequence());
		data += "\n" + sequenceGenerator.writeSequence(molecules);
		return save(path);
	}

}