package cs.unicam.rna.parser;



import cs.unicam.rna.parser.controller.RnamlParserController;

public class App {
	
	public static void main(String[] args) {
		if(args.length < 2){
			System.out.println("Please use args lanch.\nEs. java -jar app.jar in.rnaml out.aas");
			System.exit(1);
		}
		RnamlParserController controller = new RnamlParserController();
		if(args[0].equals("equals") && args.length == 3) {
			boolean result = controller.equals(args[1], args[2]);
			System.out.println("Gli RNA " + (result ? "" : "non ") + "sono uguali.");
		} else {
			controller.loadRna(args[0]);
			if(!controller.isLoaded()){
				System.out.println("Data loading failed.");
				System.exit(1);
			}
			for(int i = 1; i < args.length; i++){
				controller.SaveLoadedData(args[i]);
			}
		}
		
	}
	
}
