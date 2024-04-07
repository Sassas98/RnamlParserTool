package cs.unicam.rna.parser.service.writer;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;

public class AasFileWriter extends TextFileWriter implements RnaFileWriter {
	
	@Override
	public boolean writeAndSave(RnaFileData molecules, String path) {
		data = "";
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return save(path);
	}

	private void writeMolecule(RnaMolecule m) {
		data += m.getSequence()+"\n";
		for(RnaPair pair : m.getPairs()) {
			data += "(" + pair.getFirst().getPosition() + "," + pair.getSecond().getPosition() +");";
		}
		data += "\n\n";
	}
	

}
