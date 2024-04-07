package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.List;

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

}
