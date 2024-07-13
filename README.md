# Parser di file per il formato RNAML
Il seguente tool è stato creato in java e tramite maven.
Accetta come input file in formato rnaml, ct, aas, bpseq e db.
L'output si può richiedere negli stessi formati.

## Installazione
Per installare il tool bisogna seguire i seguenti passaggi:
1. Scaricare il contenuto della repository
2. Copiare i tre file contenuti nella cartella **result** in una cartella apposita
3. Inserire nella variabile di sistema Path il perscorso della cartella scelta
4. Creare una nuova variabile di sistema **RNATOOL_PATH** ed inserire lo stesso percorso

## Utilizzo
Per usarlo su riga di comando è necessario digitare rnatool, il path del file di input e, di seguito, tutti gli output.
Es. **rnatool input.rnaml output.db output.ct...**
Per usarlo per tradurre un gran numero di file dello stesso formato è necessario digitare rnatool all, 
il path della cartella (o un punto, se si esegue il comando dalla cartella) il formato dei file da prendere e il formato di out da produrre.
Es. **rnatool all . rnaml db**
Il software è stato pensato per una pipeline con RnaView. Per sfruttarla, bisogna prima installare RnaView sul proprio applicativo.
Una volta fatto, è necessario digitare rnaviewtool, il file di input di tipo pdb e il formato di output
Es. **rnaviewtool input.pdb bpseq**
