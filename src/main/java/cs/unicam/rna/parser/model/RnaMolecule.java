package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cs.unicam.rna.parser.exception.RnaParsingException;

/**
 * classe che contiene tutti i dati relativi ad un rna
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnaMolecule {
	
	private int moleculeId;
	private Map<Integer, RnaRibonucleotide> chain;
	private Map<Integer, Integer> pairs;
	private List<String[]> tertiaryPairs;
	private int maxReference = 0;
	
	public RnaMolecule(int moleculeId) {
		this.moleculeId = moleculeId;
		this.chain = new HashMap<>();
		this.pairs = new HashMap<>();
		this.tertiaryPairs = new ArrayList<>();
	}

	public int getMoleculeId() {
		return this.moleculeId;
	}

	public int getMaxReference(){
		return maxReference;
	}
	
	public void addRibonucleotide(char c) throws RnaParsingException {
		int pos = getLength() + 1;
		try {
			RnaBase base = RnaBase.getBase(c);
			RnaRibonucleotide obj = new RnaRibonucleotide(this.moleculeId, pos, base);
			this.chain.put(pos, obj);
		}catch(Exception e) {
			throwException(pos);
		}
	}
	
	public void addPair(int first, int second) throws RnaParsingException {
		if( first < 1 )
			throwException(first);
		if( first == second || second < 1 )
			throwException(second);
		if(this.pairs.containsKey(first) || this.pairs.containsKey(second)){
			if(this.pairs.containsKey(first)) {
				addTertiaryPair(first, pairs.get(first));
				pairs.remove(first);
			}
			if(this.pairs.containsKey(second)) {
				addTertiaryPair(second, pairs.get(second));
				pairs.remove(second);
			}
			addTertiaryPair(first, second);
		} else {
			pairs.put(first, second);
			pairs.put(second, first);
		}
	}
	public void addTertiaryPair(int first, int second) throws RnaParsingException {
		if( first < 1 )
			throwException(first);
		if( first == second || second < 1 )
			throwException(second);
			tertiaryPairs.add(new String[]{getBaseOf(first) + first, getBaseOf(second) + second});
	}

	public String getBaseOf(int index){
		RnaRibonucleotide ribo = this.chain.get(index);
		return ribo==null ? "N" : "" + RnaBase.getBaseLetter(ribo.getBase());
	}
	
	public int getLength() {
		return this.chain.size();
	}
	
	public String getSequence() {
		String out = "";
		final int len = getLength();
		for(int i = 1; i <= len; i++) {
			out += this.chain.get(i);
		}
		return out;
	}
	
	public Map<Integer, Integer> getPairMap(){
		Map<Integer, Integer> map = new HashMap<>();
		for(Entry<Integer, Integer> pair : pairs.entrySet()) {
			map.put(pair.getKey(), pair.getValue());
		}
		return map;
	}

	public boolean haveTertiaryData(){
		return !this.tertiaryPairs.isEmpty();
	}

	public List<String[]> getTertiaryStructure(){
		List<String[]> list = new ArrayList<>();
		if(!haveTertiaryData())
			return list;
		list.addAll(tertiaryPairs);
		return list;
	}
	
	private void throwException(int pos) throws RnaParsingException {
		throw new RnaParsingException(this.moleculeId, pos);
	}
	
}
