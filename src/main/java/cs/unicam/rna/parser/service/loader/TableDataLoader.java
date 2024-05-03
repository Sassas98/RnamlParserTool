package cs.unicam.rna.parser.service.loader;

import java.util.ArrayList;
import java.util.List;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * classe astratta per il caricamento di dati da un file con i valori
 * espressi in matrici. si implementa fornando durante la costruzione
 * valori adeguati per i parametri interi
 */
public abstract class TableDataLoader extends TextDataLoader implements RnaDataLoader {

    /**
     * Dati da caricare
     */
    private RnaFileData data;
    /**
     * Indici da riempire con la concretizzazione della classe
     */
    protected int dimension, pairOnePosition,
                  pairTwoPosition, basePosition;

    @Override
    public synchronized RnaFileData getData(String path) {
        this.data = new RnaFileData();
		List<List<String>> lines = getLines(path);
        if(lines == null || lines.isEmpty())
            return null;
        setFileInfo(data, lines);
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

    /**
     * Metodo che prende le linee in cui la sequenza inizia
     * @param lines linee da controllare
     * @return lista di indici
     */
    private List<Integer> getStartLines(List<List<String>> lines){
        List<Integer> starts = new ArrayList<>();
        for(int i = 0; i < lines.size(); i++) {
            if(lines.get(i).get(0).equals("1")) {
                starts.add(i);
            }
        }
        return starts;
    }

    /**
     * metodo per scrivere su una molecola dato un insieme di linee
     * @param lines linee in cui scrivere
     * @param molecule molecola in cui scrivere
     * @return la molecola inserita, con tutti i nuovi dati, o null in caso di errore
     */
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
