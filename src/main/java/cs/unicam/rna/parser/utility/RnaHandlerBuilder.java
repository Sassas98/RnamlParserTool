package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.service.*;

public class RnaHandlerBuilder {
	
	public RnaFileWriter buildFileWriter(String path) {
		String extension = getExtension(path);
		switch(extension) {
		case "rnaml":
			return null;
		case "bpseq":
			return new BpseqFileWriter();
		case "ct":
			return new CtFileWriter();
		case "db":
			return new DbFileWriter();
		case "aas":
			return new AasFileWriter();
		default:
			return null;
		}
	}

	public RnaDataLoader buildDataLoader(String path) {
		String extension = getExtension(path);
		switch(extension) {
		case "rnaml":
			return new RnamlDataLoader();
		case "bpseq":
			return null;
		case "ct":
			return null;
		case "db":
			return null;
		case "aas":
			return null;
		default:
			return null;
		}
	}

	private String getExtension(String path) {
		String[] parts = path.split("\\.");
		if(parts.length < 2)
			return "";
		return parts[parts.length - 1];
	}
	
	
}
