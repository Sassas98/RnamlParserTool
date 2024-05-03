package cs.unicam.rna.parser.service.writer;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;

/**
 * classe simbolica per indicare che non è stata
 * trovata una classe adeguata per scrivere i dati
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class NullFileWriter implements RnaFileWriter {

    @Override
    public boolean writeAndSave(RnaFileData molecules, String path) {
        return false;
    }

}
