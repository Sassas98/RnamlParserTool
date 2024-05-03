package cs.unicam.rna.parser;



import cs.unicam.rna.parser.controller.RnaParserAnalyzerController;
import cs.unicam.rna.parser.model.OperationResult;

public class App {

	private static String info = "To use this tool you must first type the name of the input file"
	+ " and then all the output files you want to produce. The formats supported by this tool are"
	+ " aas, ct, bpseq, db and rnaml.\n If instead you want to compare two files to verify that they"
	+ " contain the same primary and secondary structure, you need to type \"equals file1 file2\".";
	
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
