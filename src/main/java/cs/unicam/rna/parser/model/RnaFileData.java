package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * classe che contiene tutti i dati relativi ad uno specifico file rna
 */
public class RnaFileData {
    
    private List<RnaMolecule> molecules;
    private String organism, accessionNumber, referenceLink;

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

    public String getOrganism() {
        return organism;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public String getReferenceLink() {
        return referenceLink;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public void setReferenceLink(String referenceLink) {
        this.referenceLink = referenceLink;
    }

}
