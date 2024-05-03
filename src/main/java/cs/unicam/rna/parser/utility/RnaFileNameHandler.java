package cs.unicam.rna.parser.utility;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class RnaFileNameHandler {

    private final String[] exts = {"rnaml", "xml", "bpseq", "ct", "aas", "db" };

    public String checkExt(String path, boolean newFile) {
        path = authomaticExtension(path);
        if(newFile)
            path = checkFileExist(path);
        return path;
    }

    private String authomaticExtension(String path) {
        String[] parts = path.split("\\.");
        String extension = parts[parts.length - 1];
        for(String ext : exts) {
            if(ext.equals(extension))
                return path;
        }
        return path + ".xml";
    }

    private String checkFileExist(String path){
        if(!Files.exists(Paths.get(path))){
            return path;
        }
        String newPath;
        int count = 1;
        do{
            String[] parts = path.split("\\.");
            parts[parts.length - 2] += "(" + count++ + ")";
            newPath = Arrays.asList(parts).stream().reduce("", (a, b) -> a + ( a.equals("") ? "" : ".") + b);
        }while(Files.exists(Paths.get(newPath)));
        return newPath;
    }
}
