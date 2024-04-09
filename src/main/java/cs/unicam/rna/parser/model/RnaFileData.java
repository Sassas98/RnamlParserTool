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
            if(!molecules1.get(i).getSequence().equals(molecules2.get(i).getSequence())) {
                return false;
            }
            for(Entry<Integer, Integer> pair : molecules1.get(i).getPairMap().entrySet()) {
                if(molecules2.get(i).getPairMap().get(pair.getKey()) != pair.getValue())
                    return false;
            }
            for(Entry<Integer, Integer> pair : molecules2.get(i).getPairMap().entrySet()) {
                if(molecules1.get(i).getPairMap().get(pair.getKey()) != pair.getValue())
                    return false;
            }
        }
        return true;
    }

    

}
