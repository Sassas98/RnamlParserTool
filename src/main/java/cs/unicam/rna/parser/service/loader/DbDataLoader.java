package cs.unicam.rna.parser.service.loader;

import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.utility.DotBracketSequenceAnalyzator;

/**
 * classe per caricare i dati contenuti in un file db
 */
public final class DbDataLoader extends LineDataLoader implements RnaDataLoader {

    private RnaFileData data;
    /**
     * Analizzatore della struttura secondaria
     */
    private final DotBracketSequenceAnalyzator analyzator = new DotBracketSequenceAnalyzator();

    @Override
    public synchronized RnaFileData getData(String path) {
        this.data = new RnaFileData();
		List<List<String>> lines = getLines(path);
        if(lines == null || lines.isEmpty())
            return null;
        setFileInfo(data, lines);
        List<Integer> starts = getSequencePositions(lines);
        if(starts.isEmpty())
            return null;
        for(int i = 0; i < starts.size(); i++) {
            String sequence = lines.get(starts.get(i)).get(0);
            String pairs = lines.get(starts.get(i) + 1).get(0) ;
            RnaMolecule molecule = getMolecule(i, sequence, pairs);
            if(molecule == null) {
                return null;
            }
            data.addMolecule(molecule);
        }
        return data;
    }

    /**
     * Metodo per ottenere una molecola
     * @param index indice della molecola
     * @param sequence sequenza primaria della molecola
     * @param pairs sequenza secondaria della molecola
     * @return molecola se il parsing è andato a buon fine, null altrimenti
     */
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