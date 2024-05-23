package cs.unicam.rna.parser.service.writer;

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
	public synchronized boolean writeAndSave(RnaMolecule chains, String path) {
		data = "";
		setFileInfo(chains);
		chains.getchains().stream().forEach( m -> data += m.getSequence());
		data += "\n" + sequenceGenerator.writeSequence(chains);
		return save(path);
	}

}