package cs.unicam.rna.parser.service.loader;

import java.util.Arrays;
import java.util.List;

import cs.unicam.rna.parser.exception.RnaParsingException;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * classe per caricare i dati contenuti in un file aas
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public final class AasDataLoader extends LineDataLoader {
    
    /**
     * Dati salvati da caricare
     */
    private RnaFileData data;

    /**
     * metodo per ottenere i dati dal nome del file
     */
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
            String pairs = lines.size() > starts.get(i) + 1 && lines.get(starts.get(i) + 1).size() == 1 ? 
                           lines.get(starts.get(i) + 1).get(0) : "";
            RnaMolecule molecule = getMolecule(i + 1, sequence, pairs);
            if(molecule == null) {
                return null;
            }
            data.addMolecule(molecule);
        }
        return data;
    }

    /**
     * metodo per ottenere una molecola data una sequenza,
     * un indice e le coppie
     * @param index numero della molecola
     * @param sequence sequenza della molecola
     * @param pairs coppie della molecola
     * @return molecola
     */
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
