package cs.unicam.rna.parser;



import cs.unicam.rna.parser.controller.RnaParserAnalyzerController;
import cs.unicam.rna.parser.model.OperationResult;

/**
 * Classe di avvio del programma. 
 * Pensata per un utilizzo da terminale.
 * Se si vuole usare questo tool come una libreria esterna, cancellare questa classe
 * e interagire direttamente con il controller che più si adatta alle esigenze dello
 * sviluppatore interessato.
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class App {

	/**
	 * Info da stampare su schermo quando gli argomenti non sono forniti
	 */
	private static String info = "To use this tool you must first type the name of the input file"
	+ " and then all the output files you want to produce. The formats supported by this tool are"
	+ " aas, ct, bpseq, db and rnaml.\n If instead you want to compare two files to verify that they"
	+ " contain the same primary and secondary structure, you need to type \"equals file1 file2\".";
	
	/**
	 * Metodo main per l'utilizzo via console
	 * @param args ci sono due casi di utilizzo previsti: o il primo argomento è
	 * equals e il secondo e terzo sono i due file da confrontare, oppure il primo
	 * argomento è il file da leggere e tutti i seguenti sono quelli da scrivere.
	 */
	public static void main(String[] args) {
		if(args.length < 2){
			System.out.println(info);
			System.exit(1);
		}
		RnaParserAnalyzerController controller = new RnaParserAnalyzerController();
		OperationResult result;
		if(args[0].equals("equals") && args.length == 3) {
			result = controller.equals(args[1], args[2]);
			result.getInfo().forEach(x -> System.out.println(x));
		} else {
			result = controller.loadRna(args[0]);
			result.getInfo().forEach(x -> System.out.println(x));
			if(result.result){
				for(int i = 1; i < args.length; i++){
					result = controller.SaveLoadedData(args[i]);
					result.getInfo().forEach(x -> System.out.println(x));
				}
			}
		}
		
	}
	
}
