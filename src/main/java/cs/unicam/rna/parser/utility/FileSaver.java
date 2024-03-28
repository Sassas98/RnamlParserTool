package cs.unicam.rna.parser.utility;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
	
	public boolean overwrite = true;
	
	public boolean save(String path, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, !overwrite));
            writer.write(text);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
