package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;

public class DbSequenceGenerator {

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

	private char getDbBracket(int n){
		if(n <= 0)
			return '.';
		switch(n){
			case 1:
				return '(';
			case 2:
				return ')';
			case 3:
				return '[';
			case 4:
				return ']';
			case 5:
				return '{';
			case 6:
				return '}';
            default:
                return getDbLetter(n);
		}
	}

    private char getDbLetter(int n){
        return ((char) 
                ((n % 2 != 0 ? 'A' : 'a')
                + (n - 7) / 2));
    }
    
}
