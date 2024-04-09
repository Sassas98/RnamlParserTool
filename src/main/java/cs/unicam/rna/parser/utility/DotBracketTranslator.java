package cs.unicam.rna.parser.utility;

public class DotBracketTranslator {

    private final char [] symbols = {'.', '(', ')', '[', ']', '{', '}', '<', '>'};
    
    public char getDbBracket(int n){
        n = n < 0 ? 0 : n;
        return n < 9 ? symbols[n] : ((char) 
                                    ((n % 2 != 0 ? 'A' : 'a')
                                    + (n - 9) / 2));
    }

    public int getDbNumber(char c) {
        for(int i = 0; i < symbols.length; i++) {
            if(symbols[i] == c)
                return i;
        }
        boolean maiusc = c >= 'A' && c <= 'Z';
        int base = (c - (maiusc ? 'A' : 'a')) - (maiusc ? 0 : 1);
        return ((base * 2) + 9) + (maiusc ? 0 : 1);
    }

}
