package cs.unicam.rna.parser.controller;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.utility.RnaHandlerBuilder;

public class RnamlParserController {
	
	private RnaFileData molecules;
	private RnaHandlerBuilder builder;
	private boolean loaded;
	
	
	public RnamlParserController() {
		builder = new RnaHandlerBuilder();
		loaded = false;
	}
	
	
	public void loadRna(String path) {
		RnaDataLoader loader = builder.buildDataLoader(path);
		molecules = loader.getData(path);
		loaded = molecules != null;
	}
	
	
	public boolean isLoaded() {
		return loaded;
	}
	
	
	public boolean SaveLoadedData(String path) {
		if(path == null || (!loaded)) {
			return false;
		}
		RnaFileWriter writer = this.builder.buildFileWriter(path);
		return writer.writeAndSave(molecules, path);
	}
	
	
}
