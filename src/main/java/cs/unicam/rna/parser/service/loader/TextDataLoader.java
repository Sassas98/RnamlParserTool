package cs.unicam.rna.parser.service.loader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public abstract class TextDataLoader {

    protected List<List<String>> getLines(String path){
        try{
            return Files.readAllLines(Paths.get(path)).stream()
                .map( l ->  Arrays.asList((l.trim().split("\\s+"))))
                .filter( l -> !l.get(0).equals(""))
                .toList();
        } catch (Exception e){
            return null;
        }
    }
    
}
