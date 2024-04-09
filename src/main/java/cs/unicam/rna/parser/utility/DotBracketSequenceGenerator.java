package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;

public class DotBracketSequenceGenerator extends DotBracketTranslator {

    int [] array;

    public String writeSequence(RnaMolecule m) {
		this.array = new int[m.getLength()];
		analyze(m, 0, 0);
        String data = "";
		for(int i : array) {
			data += getDbBracket(i);
		}
		return data +"\n\n";
	}

    private void analyze(RnaMolecule m, int count, int position) {
		for(RnaPair pair : m.getPairs()) {
			if(pair.getFirst().getPosition() > position) {
				position = pair.getSecond().getPosition();
			} else if(position < pair.getSecond().getPosition()) {
				count++;
			}
			array[pair.getFirst().getPosition()-1] = 1 + (count*2);
			array[pair.getSecond().getPosition()-1] = 2 + (count*2);
		}
    }
    
}
