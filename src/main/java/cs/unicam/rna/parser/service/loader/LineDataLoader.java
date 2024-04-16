package cs.unicam.rna.parser.service.loader;

import java.util.List;

import java.util.ArrayList;

public class LineDataLoader extends TextDataLoader {

    protected List<Integer> getSequencePositions(List<List<String>> lines) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < lines.size(); i++) {
            if(lines.get(i).size() == 1 && isSequence(lines.get(i).get(0)))
                list.add(i);
        }
        return list;
    }

    private boolean isSequence(String word) {
        if(word == null || word.length() == 0)
            return false;
        word = word.toUpperCase();
        for(char letter : word.toCharArray()) {
            if(isNotSequenceLetter(letter))
                return false;
        }
        return true;
    }

    private boolean isNotSequenceLetter(char letter) {
        switch(letter){
            case 'A':
                return false;
            case 'C':
                return false;
            case 'U':
                return false;
            case 'G':
                return false;
            case 'N':
                return false;
            default:
                return true;
        }
    }

    
}
