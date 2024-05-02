package cs.unicam.rna.parser.controller;

import cs.unicam.rna.parser.abstraction.RnaDataLoader;
import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.OperationResult;
import cs.unicam.rna.parser.model.RnaFileData;
import cs.unicam.rna.parser.service.RnaComparator;
import cs.unicam.rna.parser.utility.RnaHandlerBuilder;

public class RnamlParserController {
	
	private RnaFileData molecules;
	private RnaHandlerBuilder builder;
	private boolean loaded;
	private RnaComparator comparator;
	
	
	public RnamlParserController() {
		builder = new RnaHandlerBuilder();
		loaded = false;
		comparator = new RnaComparator();
	}
	
	
	public OperationResult loadRna(String path) {
		RnaDataLoader loader = builder.buildDataLoader(path);
		molecules = loader.getData(path);
		loaded = molecules != null;
		OperationResult result = new OperationResult();
		if(loaded) {
			result.result = true;
			result.addInfo("Load " + molecules.getMolecules().size() + " molecules.");
			for(int i = 0; i < molecules.getMolecules().size(); i++ ) {
				result.addInfo("Molecule n." + i + " with " + 
					molecules.getMolecules().get(i).getLength() + " ribonucleotides.");
			}
		}else {
			result.addInfo("Failure to load data.");
		}
		return result;
	}
	
	
	public boolean isLoaded() {
		return loaded;
	}
	
	
	public OperationResult SaveLoadedData(String path) {
		OperationResult result = new OperationResult();
		if(path == null || (!loaded)) {
			result.addInfo(path == null ? "Error. Path is null." : "Error. Data to save not loaded.");
			return result;
		}
		RnaFileWriter writer = this.builder.buildFileWriter(path);
		result.result = writer.writeAndSave(molecules, path);
		if(result.result) {
			result.addInfo("Saving to file " + path + " was successful.");
			RnaDataLoader loader = builder.buildDataLoader(path);
			RnaFileData newData = loader.getData(path);
			if(!this.comparator.areEquals(molecules, newData).result)
				result.addInfo("Some data was lost during the format switch.");
		} else {
			result.addInfo("Failed to save data in " + path + " file.");
		}
		return result;
	}
	
	public OperationResult equals(String path1, String path2) {
		RnaDataLoader loader = builder.buildDataLoader(path1);
		RnaFileData data1 = loader.getData(path1);
		loader = builder.buildDataLoader(path2);
		RnaFileData data2 = loader.getData(path2);
		return this.comparator.areEquals(data1, data2);
	}
	
}
