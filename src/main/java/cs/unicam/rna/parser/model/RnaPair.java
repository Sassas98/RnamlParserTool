package cs.unicam.rna.parser.model;

public class RnaPair {
	
	RnaRibonucleotide first, second;

	public RnaPair(RnaRibonucleotide first, RnaRibonucleotide second) {
		this.first = first;
		this.second = second;
	}

	public RnaRibonucleotide getFirst() {
		return first;
	}

	public RnaRibonucleotide getSecond() {
		return second;
	}
	
}
