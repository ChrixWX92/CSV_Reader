package com.chrixwx92.csvreader.deserialization;

import com.chrixwx92.csvreader.parsing.ColumnHeaderParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static Csv createCsv() {
        return null;
    }

    public static Csv createCsvFromSample() throws IOException {
        String filePathString = "C:\\Users\\Chris\\Documents\\GitHub\\CSV Reader\\src\\main\\resources\\input.csv";
        Path filePath = Paths.get(filePathString);
        return new Csv(getFile(filePath.toString()), new ColumnHeaderParser(), true);
    }


    public static File getFile(String filepath) {
        return new File(filepath);
    }

}
