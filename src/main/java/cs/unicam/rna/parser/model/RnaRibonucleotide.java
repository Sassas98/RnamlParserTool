package cs.unicam.rna.parser.model;

public class RnaRibonucleotide {
	
	private int moleculeId;
	private int position;
	private RnaBase base;
	private RnaRibonucleotide pair;

	public RnaRibonucleotide(int moleculeId, int position, RnaBase base) {
		this.moleculeId = moleculeId;
		this.position = position;
		this.base = base;
	}

	public int getMoleculeId() {
		return moleculeId;
	}
	
	public int getPosition() {
		return position;
	}

	public RnaBase getBase() {
		return base;
	}

	public RnaRibonucleotide getPair() {
		return pair;
	}

	public void setPair(RnaRibonucleotide pair) {
		this.pair = pair;
	}

	@Override
	public String toString() {
		return RnaBase.getBaseLetter(base) + "";
	}
	
	
	
}
