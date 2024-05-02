package cs.unicam.rna.parser.service.loader;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.model.RnaFileData;

public class NullDataLoader implements RnaDataLoader {

    @Override
    public RnaFileData getData(String path) {
        return null;
    }

}
