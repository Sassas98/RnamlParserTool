package cs.unicam.rna.parser.service.writer;

import java.util.Map;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

public class BpseqFileWriter implements RnaFileWriter {

	private String data;
	
	@Override
	public String write(RnaFileData molecules) {
		data = "";
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return data;
	}

	private void writeMolecule(RnaMolecule m) {
		char[] array = m.getSequence().toCharArray();
		Map<Integer, Integer> pairs = m.getPairMap();
		for(int i = 1; i <= array.length; i++) {
			int pair = pairs.getOrDefault(i, -1);
			data += i + " " + array[i - 1] + " " 
					+ (pair == -1 ? "0" : pair) + "\n";
		}
		data += "\n\n";
	}

}
