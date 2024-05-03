package cs.unicam.rna.parser.service.loader;

/**
 * classe per caricare i dati contenuti in un file bpseq
 */
public final class BpseqDataLoader extends TableDataLoader {

    public BpseqDataLoader(){
        this.basePosition = 1;
        this.dimension = 3;
        this.pairOnePosition = 0;
        this.pairTwoPosition = 2;
    }
    
}
