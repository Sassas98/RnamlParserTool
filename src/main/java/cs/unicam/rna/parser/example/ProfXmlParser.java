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
        // total time of test executions in milliseconds
        int totalTime = 0;
        // total failures for each xml report
        int[] totalFailures = new int[argv.length];
        // total errors for each xml report
        int[] totalErrors = new int[argv.length];
        // Class Names
        String[] classNames = new String[argv.length];
        // Get csvFileName from commandline
        String csvFileName = argv[0];
        // System.out.println(csvFileName);
        // Get SURNAME-NAME from commandline
        String surnameName = argv[1];
        // System.out.println(surnameName);
        int namePos = surnameName.lastIndexOf("-");
        String surname = surnameName.substring(0, namePos);
        surname = surname.charAt(0) + surname.substring(1).toLowerCase();
        // System.out.println(surname);
        String name = surnameName.substring(namePos + 1);
        name = name.charAt(0) + name.substring(1).toLowerCase();
        // System.out.println(name);
        // Scan all the given xml files and determines times, failures and
        // errors
        for (int i = 2; i < argv.length; i++) {
            totalFailures[i] = 0;
            totalErrors[i] = 0;
            try {
                // System.out.println("Processing file " + argv[i]);
                File fXmlFile = new File(argv[i]);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();
                
                classNames[i] = argv[i].substring(31); 

                // System.out.println("Root element :"
                // + doc.getDocumentElement().getNodeName());
                NodeList nList = doc.getElementsByTagName("testcase");
                // System.out.println("----------------------------");
                // Process all the test cases
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
//                    System.out.println(
//                            "\nCurrent Element :" + nNode.getNodeName());
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
//                        System.out.println("Class : "
//                                + eElement.getAttribute("classname"));
//                        System.out.println(
//                                "Test : " + eElement.getAttribute("name"));
//                        System.out.println(
//                                "Time : " + eElement.getAttribute("time"));
//                        // scale to milliseconds
                        totalTime += 1000 * Double
                                .parseDouble(eElement.getAttribute("time"));
                        if (eElement.getElementsByTagName("error")
                                .getLength() > 0) {
//                            System.out.println("Error: "
//                                    + eElement.getElementsByTagName("error")
//                                            .item(0).getTextContent());
                            totalErrors[i]++;
                        }
                        if (eElement.getElementsByTagName("failure")
                                .getLength() > 0) {
//                            System.out.println("Failure: "
//                                    + eElement.getElementsByTagName("failure")
//                                            .item(0).getTextContent());
                            totalFailures[i]++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
         * Write a line surname, name, totalTime, failures+errors 1,
         * failures+error 2, ... on the given csv file
         */
        try (FileWriter fw = new FileWriter(csvFileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print(surname);
            out.print(", " + name);
            out.print(", " + totalTime);
            StringBuffer s = new StringBuffer();
            //s.append("\"");
            for (int j = 2; j < argv.length; j++) {
                int tot = totalFailures[j] + totalErrors[j];
                out.print(", " + tot);
                s.append(classNames[j] + ": " + totalFailures[j] + " failures + " + totalErrors[j] + " errors; ");
            }
            //s.append("\"");
            out.print(", " + s.toString());
            out.println();
            out.close();
        } catch (IOException e) {
            System.err.println("ERROR on writing output file");
            System.exit(1);
        }
//        System.out.println("Total Time: " + totalTime);
//        for (int j = 2; j < argv.length; j++) {
//            System.out.println(
//                    "Failures in class " + j + " = " + totalFailures[j]);
//            System.out.println("Errors in class " + j + " = " + totalErrors[j]);
//        }
    }
	
}
