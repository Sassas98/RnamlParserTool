package cs.unicam.rna.parser.abstraction;

import cs.unicam.rna.parser.model.RnaFileData;

/**
 * Interfaccia che definisce la responsabilità
 * di ottenere i dati da un file situato in un dato path
 */
public interface RnaDataLoader {

	/**
	 * metodo per ottenere i file
	 * @param path path del file
	 * @return il file contenente tutti i dati caricati
	 */
	public RnaFileData getData(String path);

}
