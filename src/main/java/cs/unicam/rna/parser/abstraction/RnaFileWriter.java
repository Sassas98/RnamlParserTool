package cs.unicam.rna.parser.abstraction;

import java.util.List;

import cs.unicam.rna.parser.model.RnaMolecule;

public interface RnaFileWriter {
	
	public String write(List<RnaMolecule> molecules);
	
}
