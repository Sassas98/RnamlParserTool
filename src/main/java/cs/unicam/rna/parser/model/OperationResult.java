package cs.unicam.rna.parser.model;

import java.util.ArrayList;
import java.util.List;

public class OperationResult {

    private List<String> info;

    public boolean result;

    public OperationResult() {
        this.info = new ArrayList<>();
        result = false;
    }

    public List<String> getInfo() {
        return new ArrayList<>(info);
    }

    public void addInfo(String info) {
        this.info.add(info);
    }
    

}
