package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs.unicam.rna.parser.abstraction.RnaReceiver;
import cs.unicam.rna.parser.abstraction.RnaSupplier;
import cs.unicam.rna.parser.exception.RnaParsingException;

public class RnaMolecule implements RnaReceiver, RnaSupplier {
	
	private int moleculeId;
	private Map<Integer, RnaRibonucleotide> chain;
	
	public RnaMolecule(int moleculeId) {
		this.moleculeId = moleculeId;
		this.chain = new HashMap<>();
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
		if( first < 1 || first > getLength() || ribo1 == null || ribo1.getPair() != null)
			throwException(first);
		if( first == second || second < 1 || second > getLength()|| ribo2 == null || ribo2.getPair() != null)
			throwException(second);
		ribo1.setPair(ribo2);
		ribo2.setPair(ribo1);
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
	
	public List<RnaPair> getPairs(){
		List<RnaRibonucleotide> riboList = new ArrayList<>(this.chain.values().stream()
				.filter( r -> r.getPair() != null).toList());
		for(int i = 0; i < riboList.size(); i++) {
			RnaRibonucleotide toDelete = riboList.get(i).getPair();
			riboList.remove(toDelete);
		}
		return new ArrayList<>(riboList.stream()
				.map( r -> new RnaPair(r, r.getPair())).toList());
	}
	
	private void throwException(int pos) throws RnaParsingException {
		throw new RnaParsingException(this.moleculeId, pos);
	}
	
}
