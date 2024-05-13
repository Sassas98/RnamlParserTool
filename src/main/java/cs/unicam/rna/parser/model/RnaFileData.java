package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.List;

import cs.unicam.rna.parser.exception.RnaParsingException;

/**
 * classe che contiene tutti i dati relativi ad uno specifico file rna
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
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

    public void checkSecondaryStructure() throws RnaParsingException {
        for(RnaMolecule molecule : this.molecules) {
            if(molecule.getMaxReference() > getLength()) {
                throw new RnaParsingException(molecule.getMoleculeId(), molecule.getMaxReference());
            }
        }
    }

    public int getLength() {
        return this.molecules.stream().map(x -> x.getLength()).reduce(0, (a, b) -> a + b);
    }

}
