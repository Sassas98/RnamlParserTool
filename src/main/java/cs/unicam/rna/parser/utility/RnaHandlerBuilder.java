package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.abstraction.*;
import cs.unicam.rna.parser.service.loader.*;
import cs.unicam.rna.parser.service.writer.*;

public class RnaHandlerBuilder {
	
	public RnaFileWriter buildFileWriter(String path) {
		String extension = getExtension(path);
		switch(extension) {
		case "bpseq":
			return new BpseqFileWriter();
		case "ct":
			return new CtFileWriter();
		case "aas":
			return new AasFileWriter();
		case "db":
			return new DbFileWriter();
		default:
			return new RnamlFileWriter();
		}
	}

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
