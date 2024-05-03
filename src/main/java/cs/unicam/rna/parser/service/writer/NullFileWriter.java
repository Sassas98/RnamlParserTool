package cs.unicam.rna.parser.service.writer;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;

public final class NullFileWriter implements RnaFileWriter {

    @Override
    public boolean writeAndSave(RnaFileData molecules, String path) {
        return false;
    }

}
