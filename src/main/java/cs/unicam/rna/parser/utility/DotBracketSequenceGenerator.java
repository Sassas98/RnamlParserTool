package cs.unicam.rna.parser.utility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cs.unicam.rna.parser.model.RnaMolecule;
import cs.unicam.rna.parser.model.RnaPair;

public class DotBracketSequenceGenerator extends DotBracketTranslator {

    int [] array;
	Map<Integer, Integer> scopes;

    public String writeSequence(RnaMolecule molecule) {
		this.array = new int[molecule.getLength()];
		this.scopes = new HashMap<>();
		analyze(molecule);
        String data = "";
		for(int i : array) {
			data += getDbBracket(i);
		}
		return data +"\n\n";
	}

    private void analyze(RnaMolecule molecule) {
		for(RnaPair pair : molecule.getPairs()) {
			int symbol = getSymbol(pair, 0);
			array[pair.getFirst().getPosition()-1] = 1 + (symbol*2);
			array[pair.getSecond().getPosition()-1] = 2 + (symbol*2);
		}
    }

	private int getSymbol (RnaPair pair, int symbol) {
		Integer scope = scopes.get(symbol);
		if(scope == null || scope < pair.getFirst().getPosition()
						 || scope > pair.getSecond().getPosition()) {
			scopes.put(symbol, pair.getSecond().getPosition());
			return symbol;
		}
		return getSymbol(pair, symbol + 1);
	}


    
}
