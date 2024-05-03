package cs.unicam.rna.parser.service.comparator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cs.unicam.rna.parser.model.OperationResult;
import cs.unicam.rna.parser.model.RnaBase;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * Servizio di comparazione e analisi di due file di rna
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnaComparator {
    /**
     * risultato delle analisi che viene chiamato alla fine
     */
    private OperationResult result;
    /**
     * count di servizio per il funzionamento dei metodi
     */
    private int molN;

    /**
     * classe da chiamare per far partire il servizio
     * @param data1 dati del primo file
     * @param data2 dati del secondo file
     * @return esito delle analisi
     */
    public OperationResult areEquals(RnaFileData data1, RnaFileData data2) {
        this.result = new OperationResult();
        if(data1 == null || data2 == null) {
			result.addInfo("Failure to load data.");
			return result;
		}
        result.result = compareChain(data1.getMolecules(), data2.getMolecules());
        if(result.result)
            result.addInfo("RNAs are the same.");
        return result;
    }

    /**
     * comparazione della struttura primaria e secondaria
     * @param molecules1 prima lista di molecole
     * @param molecules2 seconda lista di molecole
     * @return true se contengono le stesse strutture, false altrimenti
     */
    private boolean compareChain(List<RnaMolecule> molecules1, List<RnaMolecule> molecules2) {
        boolean check = compareMoleculeNumber(molecules1, molecules2);
        for(molN = 0; check && molN < molecules1.size(); molN++) {
            String sequence1 = molecules1.get(molN).getSequence(),
                   sequence2 = molecules2.get(molN).getSequence();
            Map<Integer, Integer> pairs1 = molecules1.get(molN).getPairMap(),
                                  pairs2 = molecules2.get(molN).getPairMap();
            check = compareSequence(sequence1, sequence2)
                 && comparePairs(pairs1, pairs2, "second") 
                 & comparePairs(pairs2, pairs1, "first");
        }
        return check;
    }

    /**
     * Metodo per verificare semplicemente se hanno un diverso numero di molecole e segnalarlo
     * @param molecules1 prima lista di molecole
     * @param molecules2 seconda lista di molecole
     * @return esito del controllo
     */
    private boolean compareMoleculeNumber(List<RnaMolecule> molecules1, List<RnaMolecule> molecules2) {
        if(molecules1.size() != molecules2.size()) {
            result.addInfo("Different number of molecules!");
            return false;
        } else {
            return true; 
        }
    }

    /**
     * Confronta le strutture primarie di due molecole,
     * considerando la terminologia imprecisa secondo cui
     * un ribonucleide potrebbe esserne un'altro
     * @param sequence1 prima sequenza
     * @param sequence2 seconda sequenza
     * @return true se potrebbero essere corrispondenti, false altrimenti
     */
    private boolean compareSequence(String sequence1, String sequence2) {
        if(!RnaBase.maybeEquals(sequence1, sequence2)) {
            result.addInfo("Sequences not corresponding to molecule n." + molN );
            if(sequence1.length() != sequence2.length()) {
                result.addInfo("The first sequence is long: " + sequence1.length());
                result.addInfo("The second sequence is long: " + sequence2.length());
            }
            else findDifferentBase(sequence1, sequence2);
            return false;
        }
        return true;
    }

    /**
     * Metodo di servizio per inserire dati ad un esito negativo
     * @param sequence1 prima sequenza
     * @param sequence2 seconda sequenza
     */
    private void findDifferentBase(String sequence1, String sequence2) {
        for(int j = 0; j < sequence1.length(); j++){
            char a = sequence1.charAt(j), b = sequence2.charAt(j);
            if( a != b ) {
                result.addInfo("In position " + j + " in the first file there is <" 
                    + a + "> and in the second file there is <" + b + ">.");
            }
        }
    }

    /**
     * Metodo per controllare se ogni coppia della struttura secondaria
     * è presente nella prima. il metodo viene chiamato due volte per
     * fare anche il check opposto
     * @param pairs1  prima lista
     * @param pairs2 seconda lista
     * @param focus focus dell'attuale check
     * @return esito della comparazione
     */
    private boolean comparePairs(Map<Integer, Integer> pairs1, Map<Integer, Integer> pairs2, String focus) {
        boolean check = true;
        for(Entry<Integer, Integer> pair : pairs1.entrySet()) {
            if(!(pair.getValue().equals(pairs2.get(pair.getKey()))
            || pair.getKey().equals(pairs2.get(pair.getValue())))) {
                result.addInfo("The " + pair.getKey() + " - " + pair.getValue()
                    + " pair is not present in molecule n." + molN + " of the " + focus + " file.");
                result.addInfo(pairs2.get(pair.getKey()) + "!=" + pair.getValue());
                check = false;
            }
        }
        return check;
    }

}
