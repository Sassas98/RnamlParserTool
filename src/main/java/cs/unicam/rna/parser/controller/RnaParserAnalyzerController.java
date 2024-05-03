package cs.unicam.rna.parser.controller;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.model.OperationResult;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.service.comparator.RnaComparator;

/**
 * Controller che estende per parser con un check alla scrittura che segnala
 * una perdita di dati per conversione e una nuova funzione di confronto che
 * verifica la corrispondenza o le differenze della struttura primaria e
 * secondaria dati due file rna
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnaParserAnalyzerController extends RnaParserController {
    /**
	 * servizio per la comparazione di due dati di rna
	 */
	private RnaComparator comparator;

    public RnaParserAnalyzerController() {
		super();
		comparator = new RnaComparator();
    }

	/**
	 * Metodo per controllare se due file contengono la
	 * stessa struttura primaria e secondaria
	 * @param path1 percosto del primo file
	 * @param path2 percorso del secondo file
	 * @return esito dell'analisi
	 */
	public synchronized OperationResult equals(String path1, String path2) {
		path1 = nameHandler.checkExt(path1, false);
		path2= nameHandler.checkExt(path2, false);
		RnaDataLoader loader = builder.buildDataLoader(path1);
		RnaFileData data1 = loader.getData(path1);
		loader = builder.buildDataLoader(path2);
		RnaFileData data2 = loader.getData(path2);
		return this.comparator.areEquals(data1, data2);
	}

    @Override
    public synchronized OperationResult SaveLoadedData(String path) {
        OperationResult result = super.SaveLoadedData(path);
        if(result.result && (!equals(loadedPath, path).result)) {
				result.addInfo("Some data was lost during the format switch.");
        }
        return result;
    }
    
}
