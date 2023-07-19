package com.chrixwx92.csvreader.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ColumnHeaderParser extends HashMapParser implements CsvParser {

    String[] headers;

    public HashMap<String, ArrayList<String>> parse(File csv) throws IOException {

        this.hashMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {

            //TODO br.readLine() returns null?
            generateHeaders(br.readLine());
            List<String[]> bodyLines = new ArrayList<>();

            String nextLine = br.readLine();
            while (nextLine != null) {
                String[] nextLinePolished = nextLine.split(lookaheadRegex);
                for (int i = 0; i < nextLinePolished.length; i++) {
                    if (nextLinePolished[i].matches("^\".*\"$")) {
                        nextLinePolished[i] = nextLinePolished[i].substring(1, nextLinePolished[i].length() - 1);
                    }
                }
                bodyLines.add(nextLinePolished);
                nextLine = br.readLine();
            }

            mapLines(bodyLines);

        }
        catch (IOException ioe) {

            ioe.printStackTrace();
            throw ioe;

        }

        return this.hashMap;

    }

    private void generateHeaders(String firstLine) {

        if (!firstLine.isEmpty()) {
            this.headers = firstLine.split(",");
        }

    }

    //NOTE: Time complexity optimisation cannot be assumed, as we don't know whether our CSV will contain more rows/columns
    // This could be optimised with additional code to check the length of the CSV being filtered, but would only be practical
    // if assuming potential large file processing
    private void  mapLines(List<String[]> lines) {
        int counter = 0;
        for (String header : this.headers) {
            ArrayList<String> columnBody = new ArrayList<>();
            for (String[] line : lines) columnBody.add(line[counter]);
            this.hashMap.put(header, columnBody);
            counter++;
        }
    }

}
