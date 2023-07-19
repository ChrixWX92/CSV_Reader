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

    public Csv(File file, CsvParser parser, boolean deserialize) throws IOException {
        this.file = file;
        this.parser = parser;
        if (deserialize) this.filter();
    }

    public void filter() throws IOException {
        this.data = this.parser.parse(this.file);
    }

    public boolean isDeserialized() { return !this.data.isEmpty();}

    public HashMap<String, ArrayList<String>> getData() { return data; }

}
