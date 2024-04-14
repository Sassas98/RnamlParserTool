package cs.unicam.rna.parser.service.loader;

import java.util.ArrayList;
import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

public abstract class TableDataLoader extends TextDataLoader implements RnaDataLoader {


    private RnaFileData data;
    protected int dimension, pairOnePosition,
                  pairTwoPosition, basePosition;

    @Override
    public RnaFileData getData(String path) {
        this.data = new RnaFileData();
		List<List<String>> lines = getLines(path);
        List<Integer> starts = getStartLines(lines);
        if(starts.isEmpty())
            return null;
        for(int i = 0; i < starts.size(); i++) {
            int until = i + 1 == starts.size() ? lines.size() : starts.get(i + 1);
            List<List<String>> moleculeList = lines.subList(starts.get(i), until).stream()
                                                   .filter(l -> l.size() == dimension).toList();
            RnaMolecule molecule = getMolecule(moleculeList, new RnaMolecule(i + 1));
            if(molecule == null)
                return null; 
            data.addMolecule(molecule);
        }
        return data;
    }


    private List<Integer> getStartLines(List<List<String>> lines){
        List<Integer> starts = new ArrayList<>();
        for(int i = 0; i < lines.size(); i++) {
            if(lines.get(i).get(0).equals("1")) {
                starts.add(i);
            }
        }
        return starts;
    }


    private RnaMolecule getMolecule(List<List<String>> lines, RnaMolecule molecule) {
        try {
            for(List<String> line : lines) {
                molecule.addRibonucleotide(line.get(basePosition).charAt(0));
            }
            List<Integer> toSkip = new ArrayList<>();
            for(List<String> line : lines) {
                int pair1 = Integer.parseInt(line.get(pairOnePosition)),
                    pair2 = Integer.parseInt(line.get(pairTwoPosition));
                if(!(pair2 == 0 || toSkip.contains(pair1))) {
                    molecule.addPair(pair1, pair2);
                    toSkip.add(pair2);
                }
            }
            return molecule;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
