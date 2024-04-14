package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RnaFileData {
    
    private List<RnaMolecule> molecules;
    private int molN;

    public RnaFileData(){
        this.molecules = new ArrayList<>();
    }

    public void addMolecule(RnaMolecule molecule){
        this.molecules.add(molecule);
    }

    public List<RnaMolecule> getMolecules(){
        List<RnaMolecule> list = new ArrayList<>();
        list.addAll(this.molecules);
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(! (obj instanceof RnaFileData))
            return false;
        RnaFileData other = (RnaFileData) obj;
        boolean sameChain = compareChain(this.getMolecules(), other.getMolecules());
        return sameChain;
    }

    private boolean compareChain(List<RnaMolecule> molecules1, List<RnaMolecule> molecules2) {
        boolean check = compareMoleculeNumber(molecules1, molecules2);
        for(molN = 0; check && molN < molecules1.size(); molN++) {
            String sequence1 = molecules1.get(molN).getSequence(),
                   sequence2 = molecules2.get(molN).getSequence();
            Map<Integer, Integer> pairs1 = molecules1.get(molN).getPairMap(),
                                  pairs2 = molecules2.get(molN).getPairMap();
            check = compareSequence(sequence1, sequence2)
                 && comparePairs(pairs1, pairs2, "secondo") 
                 && comparePairs(pairs2, pairs1, "primo");
        }
        return check;
    }

    private boolean compareMoleculeNumber(List<RnaMolecule> molecules1, List<RnaMolecule> molecules2) {
        if(molecules1.size() != molecules2.size()) {
            System.out.println("Differente numero di molecole!");
            return false;
        } else {
            return true; 
        }
    }

    private boolean compareSequence(String sequence1, String sequence2) {
        if(!RnaBase.maybeEquals(sequence1, sequence2)) {
            System.out.println("Sequenze non corrispondenti a molecola n." + molN );
            if(sequence1.length() != sequence2.length()) {
                System.out.println("La prima sequenza è lunga: " + sequence1.length());
                System.out.println("La seconda sequenza è lunga: " + sequence2.length());
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
                System.out.println("In posizione " + j + " nel primo file c'è <" 
                    + a + "> e nel secondo file c'è <" + b + ">.");
            }
        }
    }

    private boolean comparePairs(Map<Integer, Integer> pairs1, Map<Integer, Integer> pairs2, String focus) {
        for(Entry<Integer, Integer> pair : pairs1.entrySet()) {
            if(!(pair.getValue().equals(pairs2.get(pair.getKey()))
            || pair.getKey().equals(pairs2.get(pair.getValue())))) {
                System.out.println("La coppia " + pair.getKey() + " - " + pair.getValue()
                    + " non è presente nella molecola n." + molN + " del " + focus + " file.");
                System.out.println(pairs2.get(pair.getKey()) + "!=" + pair.getValue());
                return false;
            }
        }
        return true;
    }

    

}
