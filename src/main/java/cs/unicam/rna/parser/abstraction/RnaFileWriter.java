package cs.unicam.rna.parser.abstraction;

import cs.unicam.rna.parser.model.RnaFileData;

public interface RnaFileWriter {
	
	public String write(RnaFileData molecules);
	
}
