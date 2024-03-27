package cs.unicam.rna.parser.exception;

public class RnaParsingException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public RnaParsingException(int molecule, int ribonucleotide) {
        super("Error in molecule n." + molecule 
        		+ " and ribonucleotide in pos." + ribonucleotide);
    }

}
