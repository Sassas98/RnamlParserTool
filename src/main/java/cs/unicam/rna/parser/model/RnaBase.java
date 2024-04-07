package cs.unicam.rna.parser.model;

public enum RnaBase {
	ADENINE,
	URACIL,
	CYTOSINE,
	GUANINE,
	UNIDENTIFIED;
	
	public static RnaBase getBase(String s) {
		return getBase(s.toUpperCase().charAt(0));
	}
	
	public static RnaBase getBase(char c) {
		if(c >= 'a' && c <= 'z') {
			return getBase(""+c);
		}
		switch(c) {
			case 'A':
				return ADENINE;
			case 'U':
				return URACIL;
			case 'C':
				return CYTOSINE;
			case 'G':
				return GUANINE;
			case 'N':
				return UNIDENTIFIED;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public static char getBaseLetter (RnaBase base) {
		switch(base) {
			case ADENINE:
				return 'A';
			case URACIL:
				return 'U';
			case CYTOSINE:
				return 'C';
			case GUANINE:
				return 'G';
			case UNIDENTIFIED:
				return 'N';
		}
		return 0;
	}
	
}
