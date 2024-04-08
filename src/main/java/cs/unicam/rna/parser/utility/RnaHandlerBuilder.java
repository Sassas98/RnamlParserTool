package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.service.loader.AasDataLoader;
import cs.unicam.rna.parser.service.loader.BpseqDataLoader;
import cs.unicam.rna.parser.service.loader.CtDataLoader;
import cs.unicam.rna.parser.service.loader.DbDataLoader;
import cs.unicam.rna.parser.service.loader.RnamlDataLoader;
import cs.unicam.rna.parser.service.writer.AasFileWriter;
import cs.unicam.rna.parser.service.writer.BpseqFileWriter;
import cs.unicam.rna.parser.service.writer.CtFileWriter;
import cs.unicam.rna.parser.service.writer.DbFileWriter;
import cs.unicam.rna.parser.service.writer.RnamlFileWriter;

public class RnaHandlerBuilder {
	
	public RnaFileWriter buildFileWriter(String path) {
		String extension = getExtension(path);
		switch(extension) {
		case "rnaml":
			return new RnamlFileWriter();
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
			return new BpseqDataLoader();
		case "ct":
			return new CtDataLoader();
		case "db":
			return new DbDataLoader();
		case "aas":
			return new AasDataLoader();
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