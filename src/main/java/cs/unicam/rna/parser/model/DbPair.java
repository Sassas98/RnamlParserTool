package cs.unicam.rna.parser.model;

/**
 * classe che contiene una coppia di posizioni
 * unita ad un livello di ordine, indispensabile
 * per l'applicazione dell'algoritmo di scrittura
 * di file db
 */
public class DbPair implements Comparable<DbPair> {

    private final int left;
    private final int right;
    private int order;

    public DbPair(int left, int right){
        this.left = left;
        this.right = right;
        this.order = 0;
    }

    public int getLeft() {
        return left;
    }
    
    public int getRight() {
        return right;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    
    /**
     * metodo per verificare se un'altra coppia
     * si sovrappone a questa
     * @param p coppia da controllare
     * @return true se si sovrappongono, false altrimenti
     */
    public boolean crossesWith(DbPair p) {
        if (p == null)
            throw new NullPointerException("Passed pair was null");
        if (this.equals(p))
            throw new IllegalArgumentException("Passed pair was equal to this one");
        return p.left < this.left && p.right < this.right || this.left < p.left && this.right < p.right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + left;
        result = prime * result + right;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DbPair)) {
            return false;
        }
        DbPair other = (DbPair) obj;
        return this.left == other.left && this.right == other.right;
    }

    @Override
    public int compareTo(DbPair o) {
        Integer right, oright;
        right = this.right;
        oright = o.right;
        return right.compareTo(oright);
    }

}