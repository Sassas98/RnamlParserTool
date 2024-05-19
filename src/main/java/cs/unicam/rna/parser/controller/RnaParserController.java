package cs.unicam.rna.parser.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.OperationResult;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.service.writer.TertiaryStructureWriter;
import cs.unicam.rna.parser.utility.RnaFileNameHandler;
import cs.unicam.rna.parser.utility.RnaHandlerBuilder;
import cs.unicam.rna.parser.utility.DefaultRnaHandlerBuilder;

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
	private RnaHandlerBuilder builder;
	/**
	 * Nome del file caricato
	 */
	private String loadedPath;
	/**
	 * classe per la gestione di nomi di file imprevisti
	 */
	private RnaFileNameHandler nameHandler;

	private TertiaryStructureWriter tertiaryWriter;
	
	/**
	 * Costruttore del controller
	 */
	public RnaParserController() {
		builder = new DefaultRnaHandlerBuilder();
		nameHandler = new RnaFileNameHandler();
		tertiaryWriter = new TertiaryStructureWriter();
	}
	
	/**
	 * Metodo per caricare i dati di un rna contenuti in un dati file
	 * @param path nome del file
	 * @return esito dell'operazione
	 */
	public synchronized OperationResult loadRna(String path) {
		path = checkExt(path, false);
		RnaDataLoader loader = builder.buildDataLoader(path);
		molecules = loader.getData(path);
		OperationResult result = new OperationResult();
		if(isLoaded()) {
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
		return molecules != null;
	}
	
	/**
	 * Metodo per cancellare i dati caricati
	 */
	public void clean() {
		this.molecules = null;
	}
	
	/**
	 * Metodo per salvare i dati caricati in un dato file
	 * @param path nome del file
	 * @return esito dell'operazione
	 */
	public synchronized OperationResult SaveLoadedData(String path) {
		OperationResult result = new OperationResult();
		if(path == null || (!isLoaded())) {
			result.addInfo(path == null ? "Error. Path is null." : "Error. Data to save not loaded.");
			return result;
		}
		path = checkExt(path, true);
		RnaFileWriter writer = this.builder.buildFileWriter(path);
		result.result = writer.writeAndSave(molecules, path);
		result.addInfo(result.result ? "Saving to file " + path + " was successful."
			: "Failed to save data in " + path + " file.");
		if(result.result && molecules.haveTertiaryData() && (!Files.exists(Paths.get(loadedPath + ".csv")))){
			result.result = this.tertiaryWriter.writeAndSave(molecules, loadedPath + ".csv");
			result.addInfo(result.result ? "Saving to tertiary structure was successful."
				: "Saving to tertiary structure was failed.");
		}
		return result;
	}

	/**
	 * Metodo che fa uso nel nameHandler
	 * @param path path da controllare
	 * @param newFile true se è un nuovo file
	 * @return path sicuro
	 */
	public String checkExt(String path, boolean newFile){
		return this.nameHandler.checkExt(this.builder.getSupportedExtensions(), this.builder.getDefaultExtension(), path, newFile);
	}

	public RnaHandlerBuilder getBuilder() {
		return builder;
	}

	public String getLoadedPath() {
		return loadedPath;
	}

	/**
	 * Metodo per estendere il controller con
	 * builder che accettano nuovi formati
	 * @param builder nuovo builder non default
	 */
	public void setCustomHandlerBuilder(RnaHandlerBuilder builder) {
		this.builder = builder;
	}
	
}
