package cs.unicam.rna.parser.service.writer;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.utility.DbSequenceGenerator;

public class DbFileWriter extends TextFileWriter implements RnaFileWriter {

	private DbSequenceGenerator sequenceGenerator = new DbSequenceGenerator();
	
	@Override
	public boolean writeAndSave(RnaFileData molecules, String path) {
		data = "";
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}

	private void writeMolecule(RnaMolecule m) {
		data += m.getSequence() + "\n";
		data += sequenceGenerator.writeSequence(m);
	}

}