package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class RnaFileData {
    
    private List<RnaMolecule> molecules;

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
        if(molecules1.size() != molecules2.size())
            return false;
        for(int i = 0; i < molecules1.size(); i++) {
            String sequence1 = molecules1.get(i).getSequence();
            String sequence2 = molecules2.get(i).getSequence();
            if(!sequence1.equals(sequence2)) {
                System.out.println("Sequenze non corrispondenti a molecola n." + i );
                if(sequence1.length() != sequence2.length()) {
                    System.out.println("La prima sequenza è lunga: " + sequence1.length());
                    System.out.println("La seconda sequenza è lunga: " + sequence2.length());
                }
                else for(int j = 0; j < sequence1.length(); j++){
                    char a = sequence1.charAt(j), b = sequence2.charAt(j);
                    if( a != b ) {
                        System.out.println("In posizione " + j + " nel primo file c'è <" 
                            + a + "> e nel secondo file c'è <" + b + ">.");
                        break;
                    }
                }
                return false;
            }
            String check = "secondo";
            for(Entry<Integer, Integer> pair : molecules1.get(i).getPairMap().entrySet()) {
                if(molecules2.get(i).getPairMap().get(pair.getKey()).intValue() != pair.getValue().intValue()) {
                    System.out.println("La coppia " + pair.getKey() + " - " + pair.getValue()
                        + " non è presente nella molecola n." + i + " del " + check + " file.");
                    System.out.println(molecules2.get(i).getPairMap().get(pair.getKey()) + "!=" + pair.getValue());
                    return false;
                }
            }
            check = "primo";
            for(Entry<Integer, Integer> pair : molecules2.get(i).getPairMap().entrySet()) {
                if(molecules1.get(i).getPairMap().get(pair.getKey()).intValue() != pair.getValue().intValue()) {
                    System.out.println("La coppia " + pair.getKey() + " - " + pair.getValue()
                        + " non è presente nella molecola n." + i + " del " + check + " file.");
                    System.out.println(molecules1.get(i).getPairMap().get(pair.getKey()) + "!=" + pair.getValue());
                    return false;
                }
            }
        }
        return true;
    }

    

}
