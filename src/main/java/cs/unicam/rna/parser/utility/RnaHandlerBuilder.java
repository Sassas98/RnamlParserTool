package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.abstraction.*;
import cs.unicam.rna.parser.service.loader.*;
import cs.unicam.rna.parser.service.writer.*;

/**
 * Classe che genera il gestore adatto al contesto
 * in base al metodo chiamato e al nome del file
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class RnaHandlerBuilder {
	
	/**
	 * Metodo per ottenere il giusto scrittore di
	 * dati per un certo path
	 * @param path path in cui salvare
	 * @return scrittore di dati adatto
	 */
	public RnaFileWriter buildFileWriter(String path) {
		String extension = getExtension(path);
		switch(extension) {
		case "rnaml":
			return new RnamlFileWriter();
		case "xml":
			return new RnamlFileWriter();
		case "bpseq":
			return new BpseqFileWriter();
		case "ct":
			return new CtFileWriter();
		case "aas":
			return new AasFileWriter();
		case "db":
			return new DbFileWriter();
		default:
			return new NullFileWriter();
		}
	}

	/**
	 * Metodo per ottenere il giusto caricatore di
	 * dati per un certo path
	 * @param path path da caricare
	 * @return caricatore di dati adatto
	 */
	public RnaDataLoader buildDataLoader(String path) {
		String extension = getExtension(path);
		switch(extension) {
		case "rnaml":
			return new RnamlDataLoader();
		case "xml":
			return new RnamlDataLoader();
		case "bpseq":
			return new BpseqDataLoader();
		case "ct":
			return new CtDataLoader();
		case "aas":
			return new AasDataLoader();
		case "db":
			return new DbDataLoader();
		default:
			return new NullDataLoader();
		}
	}

	/**
	 * Metodo interno per ottenere l'estensione del file
	 * @param path nome del file
	 * @return estensione del file
	 */
	private String getExtension(String path) {
		String[] parts = path.split("\\.");
		if(parts.length < 2)
			return "";
		String ext = parts[parts.length - 1];
		if(ext.equals("txt") && parts.length > 2)
			return parts[parts.length - 2];
		return ext;
	}
	
	
}
