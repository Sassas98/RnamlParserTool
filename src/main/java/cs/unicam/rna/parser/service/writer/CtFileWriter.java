package cs.unicam.rna.parser.service.writer;

import java.util.Map;

import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * Classe per scrivere dati nel formato CT
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class CtFileWriter extends TextFileWriter {
	
	@Override
	public synchronized boolean writeAndSave(RnaFileData molecules, String path) {
		data = "";
		setFileInfo(molecules);
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
		data += m.getLength() + "\n";
		for(int i = 1; i <= array.length; i++) {
			int pair = pairs.getOrDefault(i, -1);
			data += i + " " + array[i - 1] + " " 
					+ (i - 1) + " " + (i == array.length ? 0 : i + 1)
					+ " " + (pair == -1 ? "0" : pair) + " " + i + "\n";
		}
		data += "\n\n";
	}

}
