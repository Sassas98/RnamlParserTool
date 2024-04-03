package cs.unicam.rna.parser.service;

import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;

public class AasFileWriter implements RnaFileWriter {
	
	private String data;
	
	@Override
	public String write(List<RnaMolecule> molecules) {
		data = "";
		molecules.stream().forEach( m -> writeMolecule(m));
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
