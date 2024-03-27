package cs.unicam.rna.parser.abstraction;

import java.util.List;

import cs.unicam.rna.parser.model.RnaPair;

public interface RnaSupplier {
	
	public String getSequence();
	
	public List<RnaPair> getPairs();
	
}
