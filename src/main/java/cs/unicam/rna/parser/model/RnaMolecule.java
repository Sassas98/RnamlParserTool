package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cs.unicam.rna.parser.exception.RnaParsingException;

public class RnaMolecule {
	
	private int moleculeId;
	private Map<Integer, RnaRibonucleotide> chain;
	private Map<Integer, List<Integer>> pairs;
	
	public RnaMolecule(int moleculeId) {
		this.moleculeId = moleculeId;
		this.chain = new HashMap<>();
		this.pairs = new HashMap<>();
	}

	public int getMoleculeId() {
		return this.moleculeId;
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
		RnaRibonucleotide ribo1 = this.chain.get(first), ribo2 = this.chain.get(second);
		if( first < 1 || first > getLength() || ribo1 == null)
			throwException(first);
		if( first == second || second < 1 || second > getLength()|| ribo2 == null)
			throwException(second);
		addPairToMap(first, second);
		addPairToMap(second, first);
	}

	public void addPairToMap(int first, int second) {
		List<Integer> list = pairs.get(first);
		if(list == null) {
			list = new ArrayList<>();
			pairs.put(first, list);
		}
		list.add(second);
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
		for(Entry<Integer, List<Integer>> pair : pairs.entrySet()) {
			for(Integer i : pair.getValue()) {
				Integer x = map.get(pair.getKey());
				if(x == null) {
					map.put(pair.getKey(), i);
				} else {
					if(map.get(i) == null)
						map.put(i, pair.getKey());
				}
			}
		}
		return map;
	}
	
	private void throwException(int pos) throws RnaParsingException {
		throw new RnaParsingException(this.moleculeId, pos);
	}
	
}
