package cs.unicam.rna.parser.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cs.unicam.rna.parser.model.OperationResult;
import cs.unicam.rna.parser.model.RnaBase;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

public class RnaComparator {

    private OperationResult result;
    private int molN;

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

    
    private boolean compareChain(List<RnaMolecule> molecules1, List<RnaMolecule> molecules2) {
        boolean check = compareMoleculeNumber(molecules1, molecules2);
        for(molN = 0; check && molN < molecules1.size(); molN++) {
            String sequence1 = molecules1.get(molN).getSequence(),
                   sequence2 = molecules2.get(molN).getSequence();
            Map<Integer, Integer> pairs1 = molecules1.get(molN).getPairMap(),
                                  pairs2 = molecules2.get(molN).getPairMap();
            check = compareSequence(sequence1, sequence2)
                 && comparePairs(pairs1, pairs2, "second") 
                 && comparePairs(pairs2, pairs1, "first");
        }
        return check;
    }

    private boolean compareMoleculeNumber(List<RnaMolecule> molecules1, List<RnaMolecule> molecules2) {
        if(molecules1.size() != molecules2.size()) {
            result.addInfo("Different number of molecules!");
            return false;
        } else {
            return true; 
        }
    }

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

    private void findDifferentBase(String sequence1, String sequence2) {
        for(int j = 0; j < sequence1.length(); j++){
            char a = sequence1.charAt(j), b = sequence2.charAt(j);
            if( a != b ) {
                result.addInfo("In position " + j + " in the first file there is <" 
                    + a + "> and in the second file there is <" + b + ">.");
            }
        }
    }

    private boolean comparePairs(Map<Integer, Integer> pairs1, Map<Integer, Integer> pairs2, String focus) {
        for(Entry<Integer, Integer> pair : pairs1.entrySet()) {
            if(!(pair.getValue().equals(pairs2.get(pair.getKey()))
            || pair.getKey().equals(pairs2.get(pair.getValue())))) {
                result.addInfo("The " + pair.getKey() + " - " + pair.getValue()
                    + " pair is not present in molecule n." + molN + " of the " + focus + " file.");
                result.addInfo(pairs2.get(pair.getKey()) + "!=" + pair.getValue());
                return false;
            }
        }
        return true;
    }

}
