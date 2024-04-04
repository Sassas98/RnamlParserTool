package cs.unicam.rna.parser.abstraction;

public interface RnaDataLoader {
    
    public void setReceiver(RnaReceiver receiver);

	public void setMoleculeData(Object moleculeData);
	
	public boolean loadData();

}
