package cs.unicam.rna.parser.utility;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Stack;

import cs.unicam.rna.parser.model.DbPair;
import cs.unicam.rna.parser.model.RnaMolecule;

/**
 * Classe per generare sequenze DB
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class DotBracketSequenceGenerator extends DotBracketTranslator {
    /**
     * Dati finali da scrivere
     */
    private int [] array;
    /**
     * Dati finali scritti
     */
    String data = "";

    /**
     * Metodo per ricavare una sequenza da una molecola
     * @param molecule molecola da analizzare
     * @return la sequenza della molecola
     */
    public String writeSequence(RnaMolecule molecule) {
        if(molecule != null){
            this.array = new int[molecule.getLength()];
            analyze(molecule);
        }
		return data + "\n\n";
	}

    /**
     * Metodo per analizzare una molecola
     * @param molecule molecola da analizzare
     */
    private void analyze(RnaMolecule molecule) {
		List<DbPair> pairs = molecule.getSimplifiedPairMap().entrySet().stream()
											.map(x -> x.getKey() < x.getValue() ? x : 
											new SimpleEntry<Integer, Integer>(x.getValue(), x.getKey()))
											.distinct().map(x -> new DbPair(x.getKey(), x.getValue()))
                                            .toList();
        updateData(pairs);
        for(DbPair pair : pairs){
            while(!checkSequence(pair)){
                pair.setOrder(pair.getOrder() + 1);
                updateData(pairs);
            }
        }
    }

    /**
     * Metodo per aggiornare i valori delle coppie salvati
     * @param pairs coppie da codificare
     */
    private void updateData(List<DbPair> pairs){
        array = new int[array.length];
        for (DbPair p : pairs) {
            array[p.getLeft() - 1] = 1 + (p.getOrder()*2);
			array[p.getRight() - 1] = 2 + (p.getOrder()*2);
        }
        data = "";
        for(int i : array) {
            data += getDbBracket(i);
        }
    }

    /**
     * Metodo per controllare che una data coppia esista
     * @param pair
     * @return true se la coppia emerge, false altrimenti
     */
    public boolean checkSequence(DbPair pair) {
        int[] symbols = data.chars().map(c -> getDbNumber((char)c)).toArray();
        int max = 0;
        for(int symbol : symbols) {
            if(symbol > max)
                max = symbol;
        }
        if(max % 2 == 1)
            return false;
        Stack<Integer> stack = new Stack<>();
        for(int i = 1; i < max; i += 2) {
            for(int j = 0; j < symbols.length; j++) {
                if(symbols[j] == i) {
                    stack.push(j);
                } else if (symbols[j] == i + 1) {
                    int p = stack.pop();
                    if(pair.getLeft() == p +1 && pair.getRight() == j +1)
                        return true;
                }
            }
            if(stack.size() != 0) {
                return false;
            }
        }
        return false;
    }

}
