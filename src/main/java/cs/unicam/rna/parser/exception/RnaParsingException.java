package cs.unicam.rna.parser.exception;

public class RnaParsingException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public RnaParsingException(int molecule, int ribonucleotide) {
        super("Error in molecule n." + molecule 
        		+ " and ribonucleotide in pos." + ribonucleotide);
    }
	
	public RnaParsingException(int molecule, int ribonucleotide1, int ribonucleotide2) {
        super("Error in molecule n." + molecule 
		+ ", ribonucleotide in pos." + ribonucleotide1
		+ " and ribonucleotide in pos." + ribonucleotide2);
    }

}
