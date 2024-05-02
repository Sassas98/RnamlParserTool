package cs.unicam.rna.parser.service.loader;

import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.utility.DotBracketSequenceAnalyzator;

public class DbDataLoader extends LineDataLoader implements RnaDataLoader {

    private RnaFileData data;
    private final DotBracketSequenceAnalyzator analyzator = new DotBracketSequenceAnalyzator();

    @Override
    public RnaFileData getData(String path) {
        this.data = new RnaFileData();
		List<List<String>> lines = getLines(path);
        List<Integer> starts = getSequencePositions(lines);
        if(starts.isEmpty())
            return null;
        for(int i = 0; i < starts.size(); i++) {
            String sequence = lines.get(i).get(0);
            String pairs = lines.get(i + 1).get(0) ;
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
            List<Integer[]> pairData = analyzator.getPairs(pairs);
            for(Integer[] pair : pairData) {
                molecule.addPair(pair[0], pair[1]);
            }
            return molecule;
        } catch (RnaParsingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}