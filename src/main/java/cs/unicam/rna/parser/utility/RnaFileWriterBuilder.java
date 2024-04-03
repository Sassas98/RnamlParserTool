package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.service.*;

public class RnaFileWriterBuilder {
	
	public RnaFileWriter build(String path) {
		String[] parts = path.split("\\.");
		String extension = parts[parts.length - 1];
		switch(extension) {
		case "bpseq":
			return new BpFileWriter();
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
	
	
}
