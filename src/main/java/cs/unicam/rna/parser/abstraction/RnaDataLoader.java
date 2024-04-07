package cs.unicam.rna.parser.abstraction;

import cs.unicam.rna.parser.model.RnaFileData;

public interface RnaDataLoader {

	public RnaFileData getData(String path);

}
