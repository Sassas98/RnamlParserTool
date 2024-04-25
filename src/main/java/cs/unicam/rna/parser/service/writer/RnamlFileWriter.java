package cs.unicam.rna.parser.service.writer;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

public class RnamlFileWriter extends XmlFileWriter implements RnaFileWriter {

    @Override
    public boolean writeAndSave(RnaFileData molecules, String path) {
        createNewDocument();
        for(RnaMolecule molecule : molecules.getMolecules()) {
            addMolecule(molecule, molecules.getAccessionNumber(), molecules.getOrganism());
            setgetReferenceLink(molecules.getReferenceLink(), molecule.getMoleculeId());
        }
        return save(path);
    }

    private void addMolecule(RnaMolecule molecule, String accessionNumer, String organism) {
        Element mol = xmlDoc.createElement("molecule");
        mol.setAttribute("id", "" + molecule.getMoleculeId());
        if(accessionNumer != null){
            mol.setAttribute("database-ids", accessionNumer);
        }
        root.appendChild(mol);
        setIdentity(mol, organism);
        Element seq = xmlDoc.createElement("sequence");
        mol.appendChild(seq);
        Element seq_data = xmlDoc.createElement("seq-data");
        seq_data.appendChild(xmlDoc.createTextNode(sequenceStyle(molecule.getSequence())));
        seq.appendChild(seq_data);
        Element struct = xmlDoc.createElement("structure");
        mol.appendChild(struct);
        Element model = xmlDoc.createElement("model");
        struct.appendChild(model);
        Element str_ann = xmlDoc.createElement("str-annotation");
        model.appendChild(str_ann);
        List<Entry<Integer, Integer>> pairs = molecule.getPairMap().entrySet().stream()
											.map(x -> x.getKey() < x.getValue() ? x : new SimpleEntry<Integer, Integer>(x.getValue(), x.getKey()))
											.distinct().toList();
        for(Entry<Integer, Integer> pair : pairs) {
            Element base_pair = xmlDoc.createElement("base-pair");
            str_ann.appendChild(base_pair);
            addBase(base_pair, "base-id-5p", pair.getKey());
            addBase(base_pair, "base-id-3p", pair.getValue());
        }
    }

    

    private void setgetReferenceLink(String referenceLink, int moleculeId){
        if(referenceLink != null) {
            Element reference = xmlDoc.createElement("reference");
            reference.setAttribute("id", "" + moleculeId);
            root.appendChild(reference);
            Element pa = xmlDoc.createElement("path");
            reference.appendChild(pa);
            Element url = xmlDoc.createElement("url");
            url.appendChild(xmlDoc.createTextNode(referenceLink));
            pa.appendChild(url);
        }
    }

    private void setIdentity(Element mol, String organism){
        if(organism != null){
            Element identity = xmlDoc.createElement("identity");
            mol.appendChild(identity);
            Element name = xmlDoc.createElement("name");
            name.appendChild(xmlDoc.createTextNode(organism));
            identity.appendChild(name);
        }
    }

    private String sequenceStyle(String sequence) {
        String result = "\n";
        int count = 0;
        for(char c : sequence.toCharArray()) {
            if(count != 0) {
                if(count % 60 == 0)
                    result += '\n';
                else if(count % 10 == 0)
                    result += ' ';
            }
            result += c;
            count++;
        }
        return result + "\n";
    }

    private void addBase(Element base_pair, String id, int pos) {
        Element base_p = xmlDoc.createElement(id);
        base_pair.appendChild(base_p);
        Element base_id = xmlDoc.createElement("base-id");
        base_p.appendChild(base_id);
        Element position = xmlDoc.createElement("position");
        position.appendChild(xmlDoc.createTextNode(pos+""));
        base_id.appendChild(position);
    }
    
    
}
