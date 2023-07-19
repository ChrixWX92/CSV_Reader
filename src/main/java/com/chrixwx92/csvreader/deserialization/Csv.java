package com.chrixwx92.csvreader.deserialization;

import com.chrixwx92.csvreader.parsing.CsvParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Csv {

    private final File file;
    private CsvParser parser;
    private HashMap<String, ArrayList<String>> data;

    public Csv(File file) {
        this.file = file;
        this.parser = null;
    }

    public Csv(File file, CsvParser parser, boolean deserialize) {
        this.file = file;
        this.parser = parser;
        if (deserialize) this.parse();
    }

    public void parse() {
        if (this.parser != null) {
            try {
                this.data = this.parser.parse(this.file);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.out.println("Error parsing CSV data.");
            }
        }
        else {
            System.out.println("No parser specified for CSV file.");
        }
    }

    public boolean isDeserialized() { return !this.data.isEmpty();}

    public void setParser(CsvParser parser) { this.parser = parser; }
    public HashMap<String, ArrayList<String>> getData() { return data; }

}
