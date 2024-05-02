package cs.unicam.rna.parser.utility;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cs.unicam.rna.parser.model.DbPair;
import cs.unicam.rna.parser.model.RnaMolecule;

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
		List<DbPair> pairs = molecule.getPairMap().entrySet().stream()
											.map(x -> x.getKey() < x.getValue() ? x : 
											new SimpleEntry<Integer, Integer>(x.getValue(), x.getKey()))
											.distinct().map(x -> new DbPair(x.getKey(), x.getValue()))
                                            .toList();
        pairs = sortPairsByStartPoint(pairs);
        setPairsOrder(pairs);
		encodeBasePairs(pairs, molecule.getLength());
    }

    private static List<DbPair> sortPairsByStartPoint(List<DbPair> pairs) {
        List<DbPair> tmp = new ArrayList<>(pairs);
        tmp.sort(Comparator.comparingInt(r -> r.getLeft()));
        return tmp;
    }

	private void setPairsOrder(List<DbPair> pairs) {
        if (pairs.size() < 2) return;
        pairs.get(0).setOrder(0);
        for (int i = 1; i < pairs.size(); i++) {
            int globalOrder = 0;
            for (int j = 0; j <= i - 1; j++) {
                if (pairs.get(j).getOrder() == globalOrder && arePairsConflicting(pairs.get(i), pairs.get(j)))
                    globalOrder += 1;
            }
            pairs.get(i).setOrder(globalOrder);
        }
    }

    private boolean arePairsConflicting(DbPair p1, DbPair p2) {
        boolean firstCase = p1.getLeft() < p2.getLeft() && p1.getRight() > p2.getLeft() && p2.getRight() > p1.getRight();
        boolean secondCase = p2.getLeft() < p1.getLeft() && p2.getRight() > p1.getLeft() && p1.getRight() > p2.getRight();
        return firstCase || secondCase;
    }

    private void encodeBasePairs(List<DbPair> pairs, int size) {
        for (DbPair p : pairs) {
            array[p.getLeft() - 1] = 1 + (p.getOrder()*2);
			array[p.getRight() - 1] = 2 + (p.getOrder()*2);
        }
    }

}
