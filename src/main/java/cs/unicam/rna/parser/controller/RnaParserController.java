package cs.unicam.rna.parser.controller;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.OperationResult;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.utility.RnaFileNameHandler;
import cs.unicam.rna.parser.utility.RnaHandlerBuilder;

/**
 * Controller Facade, centrale in questo tool
 * Mette insieme tutte le funzioni della libreria
 * Un ipotetico utilizzo esterno dovrà passare da qui
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnaParserController {
	
	/**
	 * dati caricati pronti per essere scritti
	 */
	private RnaFileData molecules;
	/**
	 * costruttore di gestori di dati rna
	 */
	protected RnaHandlerBuilder builder;
	/**
	 * true se dei dati sono già stati caricati, false altrimenti
	 */
	private boolean loaded;
	/**
	 * Nome del file caricato
	 */
	protected String loadedPath;
	/**
	 * classe per la gestione di nomi di file imprevisti
	 */
	protected RnaFileNameHandler nameHandler;
	
	/**
	 * Costruttore del controller
	 */
	public RnaParserController() {
		builder = new RnaHandlerBuilder();
		nameHandler = new RnaFileNameHandler();
		loaded = false;
	}
	
	/**
	 * Metodo per caricare i dati di un rna contenuti in un dati file
	 * @param path nome del file
	 * @return esito dell'operazione
	 */
	public synchronized OperationResult loadRna(String path) {
		path = nameHandler.checkExt(path, false);
		RnaDataLoader loader = builder.buildDataLoader(path);
		molecules = loader.getData(path);
		loaded = molecules != null;
		OperationResult result = new OperationResult();
		if(loaded) {
			loadedPath = path;
			result.result = true;
			result.addInfo("Load " + molecules.getMolecules().size() + " molecules.");
			for(int i = 0; i < molecules.getMolecules().size(); i++ ) {
				result.addInfo("Molecule n." + i + " with " + 
					molecules.getMolecules().get(i).getLength() + " ribonucleotides.");
			}
		}else {
			result.addInfo("Failure to load data.");
		}
		return result;
	}
	
	/**
	 * metodo per controllare se i dati sono attualmente stati caricati
	 * @return true se caricati, false altrimenti
	 */
	public boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Metodo per salvare i dati caricati in un dato file
	 * @param path nome del file
	 * @return esito dell'operazione
	 */
	public synchronized OperationResult SaveLoadedData(String path) {
		OperationResult result = new OperationResult();
		if(path == null || (!loaded)) {
			result.addInfo(path == null ? "Error. Path is null." : "Error. Data to save not loaded.");
			return result;
		}
		path = nameHandler.checkExt(path, true);
		RnaFileWriter writer = this.builder.buildFileWriter(path);
		result.result = writer.writeAndSave(molecules, path);
		result.addInfo(result.result ? "Saving to file " + path + " was successful."
			: "Failed to save data in " + path + " file.");
		return result;
	}
	
}
