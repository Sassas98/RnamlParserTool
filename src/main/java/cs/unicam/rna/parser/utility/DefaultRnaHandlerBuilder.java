package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.abstraction.*;
import cs.unicam.rna.parser.service.loader.*;
import cs.unicam.rna.parser.service.writer.*;

/**
 * Implementazione di default per i casi non previsti.
 * Per aggiungere nuovi formati è necessario estendere diversamente
 * la classe astratta.
 * @author Marvin Sincini - Università di Informatica di Camerino - matricola 118311
 */
public class DefaultRnaHandlerBuilder extends RnaHandlerBuilder {

	@Override
	protected RnaFileWriter buildUnexpectedFileWriter(String path) {
		return new NullFileWriter();
	}

	@Override
	protected RnaDataLoader buildUnexpectedDataLoader(String path) {
		return new NullDataLoader();
	}

	@Override
	public String[] getSupportedExtensions() {
		return new String[]{"rnaml", "xml", "bpseq", "ct", "aas", "db" };
	}

	@Override
	public String getDefaultExtension() {
		return "xml";
	}
	
	
}
