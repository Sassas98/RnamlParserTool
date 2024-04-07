package cs.unicam.rna.parser.service.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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


}
