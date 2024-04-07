package cs.unicam.rna.parser.abstraction;

import cs.unicam.rna.parser.model.RnaFileData;

public interface RnaFileWriter {
	
	public boolean writeAndSave(RnaFileData molecules, String path);
	
}
