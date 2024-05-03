package cs.unicam.rna.parser.service.loader;

/**
 * classe per caricare i dati contenuti in un file ct
 */
public final class CtDataLoader extends TableDataLoader  {

    public CtDataLoader(){
        this.basePosition = 1;
        this.dimension = 6;
        this.pairOnePosition = 0;
        this.pairTwoPosition = 4;
    }
}
