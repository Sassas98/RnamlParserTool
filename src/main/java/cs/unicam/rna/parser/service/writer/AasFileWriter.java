package cs.unicam.rna.parser.service.writer;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;

import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * Classe per salvare dei dati nel formato AAS
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class AasFileWriter extends TextFileWriter {
	
	@Override
	public synchronized boolean writeAndSave(RnaFileData molecules, String path) {
		data = molecules.getMolecules().stream().map(x -> x.getSequence()).reduce("", (a,b) -> a + b) + "\n";
		setFileInfo(molecules);
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}

	/**
	 * Scrive i dati contenuti nella molecola in formato AAS
	 * @param m molecola da scrivere
	 */
	private void writeMolecule(RnaMolecule m) {
		List<Entry<Integer, Integer>> list = m.getPairMap().entrySet().stream()
											.map(x -> x.getKey() < x.getValue() ? x : new SimpleEntry<Integer, Integer>(x.getValue(), x.getKey()))
											.distinct().toList();
		for(Entry<Integer, Integer> pair : list) {
			data += "(" + pair.getKey() + "," + pair.getValue() +");";
		}
	}
	

}
