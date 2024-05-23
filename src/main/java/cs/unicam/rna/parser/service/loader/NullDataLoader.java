package cs.unicam.rna.parser.service.loader;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * Classe simbolica che indica che non è stato trovato
 * un caricatore di dati adeguato per il formato indicato
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class NullDataLoader implements RnaDataLoader {

    @Override
    public RnaMolecule getData(String path) {
        return null;
    }

}
