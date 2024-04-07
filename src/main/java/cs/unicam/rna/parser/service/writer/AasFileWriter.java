package cs.unicam.rna.parser.service.writer;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;

public class AasFileWriter implements RnaFileWriter {
	
	private String data;
	
	@Override
	public String write(RnaFileData molecules) {
		data = "";
		molecules.getMolecules().stream().forEach( m -> writeMolecule(m));
		return data;
	}

	private void writeMolecule(RnaMolecule m) {
		data += m.getSequence()+"\n";
		for(RnaPair pair : m.getPairs()) {
			data += "(" + pair.getFirst().getPosition() + "," + pair.getSecond().getPosition() +");";
		}
		data += "\n\n";
	}
	

}
