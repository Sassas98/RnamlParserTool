package cs.unicam.rna.parser.abstraction;

import java.util.List;

import cs.unicam.rna.parser.model.RnaMolecule;

public interface RnaFileWriter {
	
	public void write(String path, List<RnaMolecule> molecules);
	
}
