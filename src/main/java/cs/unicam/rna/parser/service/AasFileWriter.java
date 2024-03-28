package cs.unicam.rna.parser.service;

import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;
import cs.unicam.rna.parser.model.RnaRibonucleotide;

public class AasFileWriter implements RnaFileWriter {
	
	private String data;
	
	@Override
	public String write(List<RnaMolecule> molecules) {
		data = "";
		molecules.stream().forEach( m -> writeMolecule(m));
		return data;
	}

	private void writeMolecule(RnaMolecule m) {
		char[] sequence = m.getSequence().toCharArray();
		for(int i = 1; i <= sequence.length; i++) {
			data += sequence[i - 1] + "" + i + " ";
		}
		for(RnaPair pair : m.getPairs()) {
			data += "\n" + getIdentifier(pair.getFirst()) + "-" + getIdentifier(pair.getSecond());
		}
		data += "\n\n";
	}

	private String getIdentifier(RnaRibonucleotide ribo) {
		return ribo.toString() + ribo.getPosition();
	}
	

}
