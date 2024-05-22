package cs.unicam.rna.parser.service.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cs.unicam.rna.parser.abstraction.RnaFileWriter;
import cs.unicam.rna.parser.model.RnaFileData;

public class TertiaryStructureWriter implements RnaFileWriter {

    @Override
    public boolean writeAndSave(RnaFileData molecules, String path) {
        try (FileWriter fw = new FileWriter(path, false);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println("base-id-5p, base-id-3p, bond-type");
            for (String[] pair : molecules.getTertiaryStructure()) {
                out.println(pair[0] + ", " + pair[1] + ", " + pair[2]);
            }
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }



}
