package cs.unicam.rna.parser.service.writer;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

public class AasFileWriter extends TextFileWriter implements RnaFileWriter {
	
	@Override
	public boolean writeAndSave(RnaFileData molecules, String path) {
		data = "";
		setFileInfo(molecules);
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}

	private void writeMolecule(RnaMolecule m) {
		data += m.getSequence()+"\n";
		List<Entry<Integer, Integer>> list = m.getPairMap().entrySet().stream()
											.map(x -> x.getKey() < x.getValue() ? x : new SimpleEntry<Integer, Integer>(x.getValue(), x.getKey()))
											.distinct().toList();
		for(Entry<Integer, Integer> pair : list) {
			data += "(" + pair.getKey() + "," + pair.getValue() +");";
		}
		data += "\n\n";
	}
	

}
