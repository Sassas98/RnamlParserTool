package cs.unicam.rna.parser.service.writer;

import java.util.Map;

import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * Classe per scrivere dati nel formato CT
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class CtFileWriter extends TextFileWriter {

	private int count = 1;
	
	@Override
	public synchronized boolean writeAndSave(RnaFileData molecules, String path) {
		setFileInfo(molecules);
		data += molecules.getMolecules().stream().map(x -> x.getLength()).reduce(0, (a,b) -> a + b) + "\n";
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}
	
	/**
	 * Scrive i dati di una molecola nel formato CT
	 * @param m molecola da scrivere
	 */
	private void writeMolecule(RnaMolecule m) {
		char[] array = m.getSequence().toCharArray();
		Map<Integer, Integer> pairs = m.getSimplifiedPairMap();
		for(int i = 1; i <= array.length; i++) {
			int pair = pairs.getOrDefault(i, -1);
			data += count + " " + array[i - 1] + " " 
					+ (i - 1) + " " + (i == array.length ? 0 : i + 1)
					+ " " + (pair == -1 ? "0" : pair) + " " + count++ + "\n";
		}
	}

}
