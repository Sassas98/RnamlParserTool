package cs.unicam.rna.parser.service.writer;

import java.util.Map;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

public class CtFileWriter extends TextFileWriter implements RnaFileWriter {
	
	@Override
	public boolean writeAndSave(RnaFileData molecules, String path) {
		data = "";
		setFileInfo(molecules);
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}

	private void writeMolecule(RnaMolecule m) {
		char[] array = m.getSequence().toCharArray();
		Map<Integer, Integer> pairs = m.getPairMap();
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
