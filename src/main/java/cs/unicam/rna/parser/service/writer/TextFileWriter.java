package cs.unicam.rna.parser.service.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import cs.unicam.rna.parser.model.RnaFileData;

public abstract class TextFileWriter {
    
    protected String data = "";
    public boolean overwrite = true;
	
	protected boolean save(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, !overwrite));
            writer.write(data);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void setFileInfo(RnaFileData rnaData) {
        if(rnaData.getOrganism() != null) {
            data += "Organism: " + rnaData.getOrganism() + "\n";
        }
        if(rnaData.getAccessionNumber() != null) {
            data += "Accession Number: " + rnaData.getAccessionNumber() + "\n";
        }
        if(rnaData.getReferenceLink() != null) {
            data += "Citation and related information available at " + rnaData.getReferenceLink() + "\n";
        }
    }


}
