package cs.unicam.rna.parser.utility;

import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;

public class DbFileWriter implements RnaFileWriter {

	private String data;
	
	@Override
	public String write(List<RnaMolecule> molecules) {
		data = "";
		molecules.stream().forEach( m -> writeMolecule(m));
		return data;
	}

	private void writeMolecule(RnaMolecule m) {
		data += m.getSequence() + "\n";
		int[] array = new int[m.getLength()];
		for(RnaPair pair : m.getPairs()) {
			array[pair.getFirst().getPosition()-1] = 1;
			array[pair.getSecond().getPosition()-1] = 2;
		}
		for(int i : array) {
			data += i == 0 ? "." : i == 1 ? "(" : ")";
		}
		data += "\n\n";
	}

}
