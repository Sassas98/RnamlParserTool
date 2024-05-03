package cs.unicam.rna.parser.exception;

/**
 * eccezione per segnalare la posizione dell'errore di parsing
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnaParsingException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * costruttore nel caso l'errore coinvolga un ribonucleide
	 * @param molecule
	 * @param ribonucleotide
	 */
	public RnaParsingException(int molecule, int ribonucleotide) {
        super("Error in molecule n." + molecule 
        		+ " and ribonucleotide in pos." + ribonucleotide);
    }
	
	/**
	 * ccostruttore nel caso l'eccezione coinvolga due ribonucleidi
	 * @param molecule
	 * @param ribonucleotide1
	 * @param ribonucleotide2
	 */
	public RnaParsingException(int molecule, int ribonucleotide1, int ribonucleotide2) {
        super("Error in molecule n." + molecule 
		+ ", ribonucleotide in pos." + ribonucleotide1
		+ " and ribonucleotide in pos." + ribonucleotide2);
    }

}
