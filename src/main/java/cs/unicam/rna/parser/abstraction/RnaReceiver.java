package cs.unicam.rna.parser.abstraction;

import cs.unicam.rna.parser.exception.RnaParsingException;

public interface RnaReceiver {
	
	public void addRibonucleotide(char c) throws RnaParsingException;
	
	public void addPair(int first, int second) throws RnaParsingException;
	
}
