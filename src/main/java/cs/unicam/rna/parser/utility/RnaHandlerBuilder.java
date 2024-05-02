package cs.unicam.rna.parser.utility;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.service.loader.AasDataLoader;
import cs.unicam.rna.parser.service.loader.BpseqDataLoader;
import cs.unicam.rna.parser.service.loader.CtDataLoader;
import cs.unicam.rna.parser.service.loader.NullDataLoader;
import cs.unicam.rna.parser.service.loader.RnamlDataLoader;
import cs.unicam.rna.parser.service.writer.AasFileWriter;
import cs.unicam.rna.parser.service.writer.BpseqFileWriter;
import cs.unicam.rna.parser.service.writer.CtFileWriter;
import cs.unicam.rna.parser.service.writer.RnamlFileWriter;

public class RnaHandlerBuilder {
	
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
