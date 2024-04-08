package cs.unicam.rna.parser.service.loader;

import java.util.Arrays;
import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

public class AasDataLoader extends LineDataLoader implements RnaDataLoader {
    
    private RnaFileData data;

    @Override
    public RnaFileData getData(String path) {
        this.data = new RnaFileData();
		List<List<String>> lines = getLines(path);
        List<Integer> starts = getSequencePositions(lines);
        if(starts.isEmpty())
            return null;
        for(int i = 0; i < starts.size(); i++) {
            String sequence = lines.get(i).get(0);
            String pairs = lines.size() > i + 1 && lines.get(i + 1).size() == 1 ? 
                           lines.get(i + 1).get(0) : "";
            RnaMolecule molecule = getMolecule(i, sequence, pairs);
            if(molecule == null) {
                return null;
            }
            data.addMolecule(molecule);
        }
        return data;
    }

    private RnaMolecule getMolecule(int index, String sequence, String pairs) {
        try {
            RnaMolecule molecule = new RnaMolecule(index);
            for(char letter : sequence.toCharArray()) {
                molecule.addRibonucleotide(letter);
            }
            if(!(pairs == null || pairs.equals(""))) {
                for(String pair : pairs.split(";")) {
                    if(pair.length() > 4) {
                        List<Integer> positions = Arrays.asList(pair.replace("(", "")
                                                    .replace(")", "").split(","))
                                                    .stream().map(s -> Integer.parseInt(s)).toList();
                        molecule.addPair(positions.get(0), positions.get(1));
                    }
                }
            }
            return molecule;
        } catch (RnaParsingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}