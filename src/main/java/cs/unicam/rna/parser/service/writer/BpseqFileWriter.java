package cs.unicam.rna.parser.service.writer;

import java.util.Map;

import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * Classe per scrivere dai dati in formato BPSEQ
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class BpseqFileWriter extends TextFileWriter {

	@Override
	public synchronized boolean writeAndSave(RnaFileData molecules, String path) {
		data = "";
		setFileInfo(molecules);
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}

	/**
	 * Scrive i dati contenuti nella molecola nel formato BPSEQ
	 * @param m molecola da scrivere
	 */
	private void writeMolecule(RnaMolecule m) {
		char[] array = m.getSequence().toCharArray();
		Map<Integer, Integer> pairs = m.getPairMap();
		for(int i = 1; i <= array.length; i++) {
			int pair = pairs.getOrDefault(i, -1);
			data += i + " " + array[i - 1] + " " 
					+ (pair == -1 ? "0" : pair) + "\n";
		}
	}

}
