package cs.unicam.rna.parser.service.loader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import cs.unicam.rna.parser.model.RnaFileData;

public abstract class TextDataLoader {

    protected List<List<String>> getLines(String path){
        try{
            return Files.readAllLines(Paths.get(path)).stream()
                .map( l ->  Arrays.asList((l.trim().split("\\s+"))))
                .toList();
        } catch (Exception e){
            return null;
        }
    }

    protected void setFileInfo(RnaFileData file, List<List<String>> lines) {
        boolean findOrganism = true, findNumber = true, findLink = true;
        for(List<String> line : lines) {
            if(line.size() > 1) {
                if(findOrganism && containAtIndex(line, 0,"organism")) {
                    file.setOrganism(getWordsFrom(line, 1));
                    findOrganism = false;
                } else if(findNumber && containAtIndex(line, 0,"accession") && containAtIndex(line, 1,"number")) {
                    file.setAccessionNumber(getWordsFrom(line, 2));
                    findNumber = false;
                } else if(findLink) {
                    String url = findFirstUrl(line);
                    if(url != null){
                        file.setReferenceLink(url);
                        findLink = false;
                    }
                }
            }
        }
    }

    private String getWordsFrom(List<String> line, int from) {
        String s = "";
        for(int i = from; i < line.size(); i++) {
            s += (i != 1 ? " " : "") + line.get(i);
        }
        return s;
    }

    private boolean containAtIndex(List<String> line, int index, String word) {
        return line.get(index).toLowerCase().contains(word.toLowerCase());
    }

    private String findFirstUrl(List<String> line) {
        for(String word : line) {
            if(word.contains("http://") || word.contains("https://")) {
                return word;
            }
        }
        return null;
    }

}
