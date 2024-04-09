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
		if(loaded) {
			System.out.println("Caricate " + molecules.getMolecules().size() + " molecole.");
			for(int i = 0; i < molecules.getMolecules().size(); i++ ) {
				System.out.println("Molecola n." + i + " con " + 
					molecules.getMolecules().get(i).getLength() + " ribonucleotidi.");
			}
		}
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
	
	public boolean equals(String path1, String path2) {
		RnaDataLoader loader = builder.buildDataLoader(path1);
		RnaFileData data1 = loader.getData(path1);
		loader = builder.buildDataLoader(path2);
		RnaFileData data2 = loader.getData(path2);
		if(data1 == null || data2 == null) {
			System.out.println("Data loading failed.");
			return false;
		}
		return data1.equals(data2);
	}
	
}
