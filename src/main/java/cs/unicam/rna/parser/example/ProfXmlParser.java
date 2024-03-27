package cs.unicam.rna.parser.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProfXmlParser {
	
	public static void exe(String argv[]) {
        if (argv.length < 3) {
            System.out.println(
                    "Usage: java -jar surefire-reader.jar <csv-output-file>"
                            + " <surname-name string> <surefire-xml-report-file1>"
                            + " <surefire-xml-report-file2> ...");
            System.exit(1);
        }
        int totalTime = 0;
        int[] totalFailures = new int[argv.length];
        int[] totalErrors = new int[argv.length];
        String[] classNames = new String[argv.length];
        String csvFileName = argv[0];
        String surnameName = argv[1];
        int namePos = surnameName.lastIndexOf("-");
        String surname = surnameName.substring(0, namePos);
        surname = surname.charAt(0) + surname.substring(1).toLowerCase();
        String name = surnameName.substring(namePos + 1);
        name = name.charAt(0) + name.substring(1).toLowerCase();
        for (int i = 2; i < argv.length; i++) {
            totalFailures[i] = 0;
            totalErrors[i] = 0;
            try {
                File fXmlFile = new File(argv[i]);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();
                
                classNames[i] = argv[i].substring(31); 
                NodeList nList = doc.getElementsByTagName("testcase");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        totalTime += 1000 * Double
                                .parseDouble(eElement.getAttribute("time"));
                        if (eElement.getElementsByTagName("error")
                                .getLength() > 0) {
                            totalErrors[i]++;
                        }
                        if (eElement.getElementsByTagName("failure")
                                .getLength() > 0) {
                            totalFailures[i]++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try (FileWriter fw = new FileWriter(csvFileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print(surname);
            out.print(", " + name);
            out.print(", " + totalTime);
            StringBuffer s = new StringBuffer();
            for (int j = 2; j < argv.length; j++) {
                int tot = totalFailures[j] + totalErrors[j];
                out.print(", " + tot);
                s.append(classNames[j] + ": " + totalFailures[j] + " failures + " + totalErrors[j] + " errors; ");
            }
            out.print(", " + s.toString());
            out.println();
            out.close();
        } catch (IOException e) {
            System.err.println("ERROR on writing output file");
            System.exit(1);
        }
    }
	
}
